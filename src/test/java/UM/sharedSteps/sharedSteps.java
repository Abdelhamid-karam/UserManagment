package UM.sharedSteps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import gherkin.deps.com.google.gson.JsonElement;
import gherkin.deps.com.google.gson.JsonObject;
import gherkin.deps.com.google.gson.JsonParser;

import static org.junit.Assert.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class sharedSteps {
	
	public static String url;
	public static HttpResponse response;
	public static String responseContent;
	public static String token;
	public static JsonObject jsonResponse;
	public static String Unique_Email;
	public static String Unique_username;
	public static JsonElement Verify_token;

	//**Base URL
	public static void get_url()  {
		File urlFile = new File("./src/test/java/url"); 
//		System.out.println(urlFile.getAbsolutePath());
		try {
			BufferedReader br = new BufferedReader(new FileReader(urlFile));
			url= br.readLine();
			System.out.println(url);
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	      String uniqueID = UUID.randomUUID().toString();
	       Unique_Email="ahmed.abdelwahab123"+uniqueID+"@nagwa.com";
	       Unique_username="ahmed.abdelwahab123"+uniqueID;
	}
	
	//Auth. token for UM
	@Given("^I have a valid user managment authentication token$")
	public void user_managment_authenticate() throws Throwable {
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost("https://auth.nagwa.com/connect/token");
	    List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
		urlParameters.add(new BasicNameValuePair("scope", "UserManagementApi"));
		urlParameters.add(new BasicNameValuePair("client_id", "usermanagement.client"));
		urlParameters.add(new BasicNameValuePair("client_secret", "Xn2AfMw3ZhjDkXXMPD4FRa6amNQNHFWRyrZ5Sb9e"));
		request.setEntity(new UrlEncodedFormEntity(urlParameters));
		response = client.execute(request);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));	
		String line = "";
		StringBuffer result = new StringBuffer();
		while ( (line = rd.readLine()) != null) 
		{
			result.append(line);
			}
		responseContent= result.toString();
		System.out.println(responseContent);
		JsonParser parser = new JsonParser();
        Object resultObject = parser.parse(responseContent);
        JsonObject obj =(JsonObject)resultObject;
        token= obj.get("token_type").getAsString()+" "+obj.get("access_token").getAsString();
        System.out.println("token  = "+ token);
	}
	
	@Then("^response code should be (\\d+)$")
	public void response_code(int expectedCode) throws Throwable {
		assertEquals(expectedCode, response.getStatusLine().getStatusCode());
	}
	

}
