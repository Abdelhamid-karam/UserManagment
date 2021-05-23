package UM.UserManagment;



import static org.junit.Assert.assertEquals;

import UM.sharedSteps.sharedSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import io.restassured.RestAssured;

public class ConfirmResttingUserPassword {
	String reset_token ;
	int new_password;
	String rseult_user_code;
	
	@Given("I have reset token and new password")
	public void i_have_reset_token_and_new_password() throws Exception {
		reset_token = sharedSteps.readFileAsString("./data/U1_Reset_password_token");
		new_password = 123456789; 
	    
	}

	@When("I post confirm the restting of the user password API")
	public void i_post_confirm_the_restting_of_the_user_password_API() throws Exception {
		sharedSteps.get_url();
		  RestAssured.baseURI=sharedSteps.url;
		  sharedSteps.response= RestAssured
				 .given()
				   .auth().oauth2(sharedSteps.token)
				   .header("api-version", sharedSteps.ApiVersion)
				   .formParam("token", reset_token)
				   .formParam("newPassword", new_password)
				 .when()
				  .post("/User/ConfirmResetPassword")
			     .then()
				    .extract().response();
		   
		   sharedSteps.write_into_FileAsString("./responses/confirm the restting of the user password", sharedSteps.response.prettyPrint());
		   rseult_user_code=sharedSteps.response.path("result").toString();
	}
	
	@Then("result ID should to be equal first_user_code")
	public void result_ID_should_to_be_equal_user__code() throws Exception {
		assertEquals(rseult_user_code,sharedSteps.readFileAsString("./data/User1_code"));
	   
	}

	

}