package UM.UserManagment;

import static io.restassured.RestAssured.given;

import UM.sharedSteps.sharedSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;




public class AuthenticatePortalUserToLogin{
	String loginName;
	String  password ;
	String portal_ID ;
	String API_body;
	
	
	@Given("I have user email and password")
	public void i_have_user_email_and_password() throws Exception {
		loginName =sharedSteps.readFileAsString("./data/User2_email");
		password ="123456";// static password used with user 1 
	}

	@Given("I have \"([^\"]+)\" portal ID")
	public void i_have_portal_ID(String S) throws Exception {
	    if (S.equalsIgnoreCase("nagwa")) {
	    	portal_ID = "464161291713";//nagwa portal ID same on all environments (live - demo)
	    	System.out.println("Authenticate "+loginName +" to login on nagwa portal"); 
	    	API_body = "{\r\n" + 
	    			"       \"loginName\": \"" +loginName+ "\",\r\n" + 
	    			"       \"password\": \"" +password+ "\",\r\n" + 
	    			"       \"portalID\":  " +portal_ID+ "\r\n" + 
	    			"}";
	    	
	    }else if (S.equalsIgnoreCase("new portal")) {
	    	portal_ID = sharedSteps.readFileAsString("./data/portal_ID");
	    	System.out.println("Authenticate "+loginName +" to login on new portal"); 
	    	API_body = "{\r\n" + 
	    			"       \"loginName\": \"" +loginName+ "\",\r\n" + 
	    			"       \"password\": \"" +password+ "\",\r\n" + 
	    			"       \"portalID\":  " +portal_ID+ "\r\n" + 
	    			"}";
	    }else {
	    	System.out.println("fi haga 3'alat o-O"); 
	    }  
	}

	@When("I post Authenticate portal user to login")
	public void i_post_Authenticate_portal_user_to_login() throws Exception {
		sharedSteps.get_url();
		RestAssured.baseURI = sharedSteps.url;
		sharedSteps.response = 
			given()
			    .auth().oauth2(sharedSteps.token)
			    .header("api-version", sharedSteps.ApiVersion)
			    .contentType("application/json")
				.body(API_body)
			.when()
				.post("/User/PortalsLogin")
			.then()
			.extract().response();
		
		sharedSteps.write_into_FileAsString("./responses/Authenticate portal user to login", sharedSteps.response.prettyPrint());
	}
}