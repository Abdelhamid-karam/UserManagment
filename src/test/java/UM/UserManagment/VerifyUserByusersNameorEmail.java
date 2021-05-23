package UM.UserManagment;






import static io.restassured.RestAssured.given;

import java.io.IOException;

import UM.sharedSteps.sharedSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;





public class VerifyUserByusersNameorEmail{
	String username ;
	String Email ;
	String encrypted_username;
	String encrypted_Email;
	String user_data ; // variable sent from feature file according to it we will send specific user body 
	
	
	@Given("I have user \"([^\"]+)\" to be verified$")
	public void i_have_user_data (String S) throws Exception {
		user_data = S ;
		 if (S.equalsIgnoreCase("UsersName")){
		username  = sharedSteps.readFileAsString("./data/User1_userName");
		System.out.println("username : "+username);
		 }else if (S.equalsIgnoreCase("Email")) {
			 Email = sharedSteps.readFileAsString("./data/User2_email") ;
		 }else {
				System.out.println("somthing went wrong o-O"); 
			}
	}

	@Given("^I have encrypted usersName or Email$")
	public void i_have_encrypted_data() throws IOException {
		if (user_data.equalsIgnoreCase("UsersName")) {
		encrypted_username =  sharedSteps.get_encryption(username);
		System.out.println("encrypted_username : "+encrypted_username);
		}else if (user_data.equalsIgnoreCase("Email")) {
			encrypted_Email = sharedSteps.get_encryption(Email);
			System.out.println("encrypted_Email : "+encrypted_Email);
		 }else {
				System.out.println("somthing went wrong o-O"); 
			}
	}

	@When("^I post Verify user API$")
	public void i_post_Verify_user() throws Exception {
		sharedSteps.get_url();
		RestAssured.baseURI = sharedSteps.url;
		if (user_data.equalsIgnoreCase("UsersName")) {
		sharedSteps.response =
		    		given()
		    		 .auth()
		    		 .oauth2(sharedSteps.token)
		    		 .header("api-version", sharedSteps.ApiVersion)
		    		 .header("LoginNameEncrypted",encrypted_username)
		    		 .contentType("application/json")
		    		 .body("{\r\n" + 
		    		 		"    \"loginName\": \""+username+"\"\r\n" + 
		    		 		"}    ")
		    		.when()
		    		 .post("/User/VerifyUsernameOrEmail")
		    	    .then()
		    		 .extract().response();
		sharedSteps.write_into_FileAsString("./responses/VerifyUserByusersName", sharedSteps.response.prettyPrint());
		sharedSteps.write_into_FileAsString("./data/U1_Reset_password_token2", sharedSteps.response.path("result.resetToken").toString());
		}else if (user_data.equalsIgnoreCase("Email")) {
			sharedSteps.response =
		    		given()
		    		 .auth()
		    		 .oauth2(sharedSteps.token)
		    		 .header("api-version", sharedSteps.ApiVersion)
		    		 .header("LoginNameEncrypted",encrypted_Email)
		    		 .contentType("application/json")
		    		 .body("{\r\n" + 
		    		 		"    \"loginName\": \""+Email+"\"\r\n" + 
		    		 		"}    ")
		    		.when()
		    		 .post("/User/VerifyUsernameOrEmail")
		    	    .then()
		    		 .extract().response();
		sharedSteps.write_into_FileAsString("./responses/VerifyUserByEmail", sharedSteps.response.prettyPrint());
		
		 }else {
				System.out.println("somthing went wrong o-O"); 
			}
	   
	}
	
}