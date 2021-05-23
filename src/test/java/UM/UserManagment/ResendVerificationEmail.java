package UM.UserManagment;

import UM.sharedSteps.sharedSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;





public class ResendVerificationEmail{

	String User_email;
	
	@Given("I have user email for user that is not verified")
	public void i_have_user_email_for_user_that_is_not_verified() throws Exception {
		User_email = sharedSteps.readFileAsString("./data/User2_email");
		System.out.println("User_email : " + User_email);
	}

	
	

	@When("I call Request resending verification email api")
	public void i_call_Request_resending_verification_email_api() throws Exception {
		 sharedSteps.get_url();
		  RestAssured.baseURI=sharedSteps.url;
		  sharedSteps.response= RestAssured
				 .given()
				   .auth().oauth2(sharedSteps.token)
				   .header("api-version", sharedSteps.ApiVersion)
				   .param("email", User_email)
				 .when()
				   .post("/User/ResendVerificationEmail")
			     .then()
				    .extract().response();
		  sharedSteps.write_into_FileAsString("./data/User2_verificationToken", sharedSteps.response.path("result.VerificationToken").toString());
		   sharedSteps.write_into_FileAsString("./responses/Request resending verification email", sharedSteps.response.prettyPrint());
	    
	}
	
	
}