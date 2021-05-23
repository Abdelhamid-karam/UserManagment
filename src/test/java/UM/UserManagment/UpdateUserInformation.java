package UM.UserManagment;






import UM.sharedSteps.sharedSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;





public class UpdateUserInformation{

	String User_ID;
	
	@Given("I have userID to update its info")
	public void i_have_userID_to_update_its_info() throws Exception {
		User_ID = sharedSteps.readFileAsString("./data/User3_code");
	}

	@When("I post Update user information API")
	public void i_post_Update_user_information_API() throws Exception {
		 sharedSteps.get_url();
		  RestAssured.baseURI=sharedSteps.url;
		  sharedSteps.response= RestAssured
				 .given()
				   .auth().oauth2(sharedSteps.token)
				   .header("api-version", sharedSteps.ApiVersion)
				   .param("userId", User_ID)
				   .param("FirstName", "update")
				   .param("LastName", "update")
				   .param("CountryIso", "eg")
				   .param("LanguageIso", "en")
				 .when()
				   .post("/User/UpdateUserInfo/UserID")
			     .then()
				    .extract().response();
		   
		   sharedSteps.write_into_FileAsString("./responses/Update user information", sharedSteps.response.prettyPrint());
	}
	
}