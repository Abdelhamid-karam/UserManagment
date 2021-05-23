package UM.UserManagment;






import UM.sharedSteps.sharedSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;





public class getUserInfo{

	String User;
	public static String sent_V_user;// VARAIBLE sent from feature file that will be code , email or username  
	
	
	
	@Given("^I have user \"([^\\\"]+)\"$")
	public void i_have_user(String S) throws Exception {
		if (S.equalsIgnoreCase("code")){
			sent_V_user = sharedSteps.readFileAsString("./data/User2_code");
		}else if (S.equalsIgnoreCase("Email")) {
			sent_V_user = sharedSteps.readFileAsString("./data/User2_email");
		}else if (S.equalsIgnoreCase("Username")) {
			sent_V_user = sharedSteps.readFileAsString("./data/User2_username");
		}else {
			System.out.println("somthing went wrong o-O"); 
		}
	}
	
	@When("^I call  Get user info api$")
	public void i_call_Get_user_info_api() throws Exception {
		sharedSteps.get_url();
		  RestAssured.baseURI=sharedSteps.url;
		  sharedSteps.response= RestAssured
				 .given()
				   .auth().oauth2(sharedSteps.token)
				   .header("api-version", sharedSteps.ApiVersion)
				 .when()
				  .get("/User/Info/"+sent_V_user)
			     .then()
				    .extract().response();
		   
		   sharedSteps.write_into_FileAsString("./responses/Get user info", sharedSteps.response.prettyPrint());
	}
	
}