package UM.sharedSteps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.junit.Assert.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;






public class sharedSteps {
	public static String auth_URL = "https://auth.nagwa.com/connect/token";
	public static String url;
	public static Response response;
	public static String token;
	public static int  ApiVersion = 3;
	
	
	//**Base URL
	public static void get_url()  {
		File urlFile = new File("./src/test/java/url"); 
		try {
			BufferedReader br = new BufferedReader(new FileReader(urlFile));
			url= br.readLine();
			System.out.println(url);
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//Getting auth. token for UM
	@Given("^I have a valid user managment authentication token$")
	public void user_managment_authenticate() throws Throwable {
        RestAssured.baseURI =auth_URL;
         Response auth_response  = 
		 given()
			.param("client_id", "usermanagement.client")
			.param("client_secret", "Xn2AfMw3ZhjDkXXMPD4FRa6amNQNHFWRyrZ5Sb9e")
			.param("scope", "UserManagementApi")
			.param("grant_type", "client_credentials")
		.when()
			.post()
		.then()
		    .extract()
		    .response();
		    
		 token =  auth_response.path("access_token").toString();
		 System.out.println("auth token = " + token); 
	}
	
	//assert on response code 
	@Then("^response code should be (\\d+)$")
	public void response_code(int expectedCode) throws Throwable {
		assertEquals(expectedCode,response.getStatusCode());
	}
	
	//assert on API response jason schema   
	@Then("response schema matches the json schema provided in \"([^\"]+)\" file")
	public void checkResponseSchema(String fileName) throws Throwable {
		
		File questionsSchemaFile = new File("./data/"+fileName);
		response.then().assertThat().body(matchesJsonSchema(questionsSchemaFile));
	}
	
	//read file data 
	public static String readFileAsString(String fileName)throws Exception 
	  { 
	    String data = ""; 
	    data = new String(Files.readAllBytes(Paths.get(fileName))); 
	    return data; 
	  } 
	
	//write data in to a file 
	public static void  write_into_FileAsString(String fileName, String data )throws Exception 
	  { 
		 FileWriter file = new FileWriter(fileName);
         file.write(data);
         file.flush();
         file.close();
	  }
	
	// get encryption user IDs or email or username
	public static String  get_encryption(String input) throws IOException {
		
		String line;
		String EncryptedData= null;
		OutputStream stdin = null;
	    InputStream stdout = null;
		
		Process process = Runtime.getRuntime().exec("./Data/crypt-win64/publish/Crypt.exe");
		stdin = process.getOutputStream ();
	    stdout = process.getInputStream ();
		
	    stdin.write("e\n".getBytes());
	    input+="\n";
	    stdin.write(input.getBytes());
	    stdin.flush();
	    stdin.close();
	    
	    BufferedReader outPut = new BufferedReader (new InputStreamReader (stdout));
	    line = outPut.readLine ();
//	    System.out.println("line is :"+line);
	    line = line.substring(line.indexOf(':') + 1, line.indexOf('=')+2);
	    EncryptedData = line.substring(line.indexOf(':') + 2, line.indexOf('=')+2);
	    EncryptedData = EncryptedData.replaceAll("\n", "");
	    
	    outPut.close();
		return EncryptedData;
	}
	
	// get user info by code 
	public static Response call_Get_user_info_api_by_code(String user_code) throws Exception {
		get_url();
		  RestAssured.baseURI=url;
		  response= RestAssured
				 .given()
				   .auth().oauth2(sharedSteps.token)
				   .header("api-version", ApiVersion)
				 .when()
				  .get("/User/Info/"+user_code)
			     .then()
				    .extract().response();
		 
		   return response;
	}
	

}
