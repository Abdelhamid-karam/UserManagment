package UM.UserManagment;


import UM.sharedSteps.sharedSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;


public class confirmUser{

	String User;
	public static String verificationToken;
	
	
	@Given("^I have user verification Token$")
	public void get_verificationToken() throws Exception {
		verificationToken = sharedSteps.readFileAsString("./data/User2_verificationToken");
		System.out.println("verificationToken : " + verificationToken);
	    
	}
	
	  @When("^I call confirm user api$")
	  public void confirm_user() throws Throwable {
		  sharedSteps.get_url();
		  RestAssured.baseURI=sharedSteps.url;
		  sharedSteps.response= RestAssured
				 .given()
				   .auth().oauth2(sharedSteps.token)
				   .header("api-version", sharedSteps.ApiVersion)
				   .param("token", verificationToken)
				 .when()
				   .post("/User/Confirm")
			     .then()
				    .extract().response();
		   
		   sharedSteps.write_into_FileAsString("./responses/confirmUser", sharedSteps.response.prettyPrint());
		  
		  
	  }
	
	
}