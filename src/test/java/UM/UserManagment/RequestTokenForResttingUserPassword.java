package UM.UserManagment;



import UM.sharedSteps.sharedSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

import io.restassured.RestAssured;

public class RequestTokenForResttingUserPassword {
	
	String user_ID ;
	String username;

	@Given("I have user ID")
	public void i_have_user_ID() throws Exception {
		user_ID = sharedSteps.readFileAsString("./data/User1_code");
	}
	
	@Given("I have username")
	public void i_have_username() throws Exception {
		username = sharedSteps.readFileAsString("./data/User2_username");
	    
	}


	@When("I post request token for restting user poassword by user \"([^\"]+)\"")
	public void i_post_request_token_for_restting_user_poassword_by_user(String sent_V_user) throws Exception {
		if(sent_V_user.equalsIgnoreCase("ID")) {
			sharedSteps.get_url();
			  RestAssured.baseURI=sharedSteps.url;
			  sharedSteps.response= RestAssured
					 .given()
					   .auth().oauth2(sharedSteps.token)
					   .header("api-version", sharedSteps.ApiVersion)
					   .formParam("UserID", user_ID)
					 .when()
					  .post("/User/RequestResetPassword/UserID")
				     .then()
					    .extract().response();
			   
			   sharedSteps.write_into_FileAsString("./responses/request token for restting user poassword", sharedSteps.response.prettyPrint());
			   sharedSteps.write_into_FileAsString("./data/U1_Reset_password_token", sharedSteps.response.path("result").toString());
			
		}else if(sent_V_user.equalsIgnoreCase("username")) {
			sharedSteps.get_url();
			  RestAssured.baseURI=sharedSteps.url;
			  sharedSteps.response= RestAssured
					 .given()
					   .auth().oauth2(sharedSteps.token)
					   .header("api-version", sharedSteps.ApiVersion)
					   .formParam("userName", username)
					 .when()
					  .post("/User/RequestResetPassword/UserName")
				     .then()
					    .extract().response();
			   
			   sharedSteps.write_into_FileAsString("./responses/request token for restting user poassword", sharedSteps.response.prettyPrint());
			   sharedSteps.write_into_FileAsString("./data/U2_Reset_password_token", sharedSteps.response.path("result").toString());
			
		} else {
			System.out.println("something went wrong o-O"); 
		}
		
		
	    
	}

	

}