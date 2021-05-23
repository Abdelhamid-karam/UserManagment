package UM.UserManagment;






import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import UM.sharedSteps.sharedSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;





public class UpdateUserName{

	String User_ID;
	String portal_ID;
	String old_Username ; // i get old user name and make from it a new one by adding prefix by automation 
	
	@Given("I have user portalID")
	public void i_have_user_portalID() throws Exception {
		portal_ID = sharedSteps.readFileAsString("./data/portal_ID");
	}

	@Given("I have userID for both user and requester")
	public void i_have_userID_for_both_user_and_requester() throws Exception {
		User_ID = sharedSteps.readFileAsString("./data/User1_code");
	}

	@When("I post UpdateUsername API")
	public void i_post_UpdateUsername_API() throws Exception {
		old_Username  =sharedSteps.readFileAsString("./data/User1_userName");
		 sharedSteps.get_url();
		  RestAssured.baseURI=sharedSteps.url;
		  sharedSteps.response =
		    		given()
		    		 .auth()
		    		 .oauth2(sharedSteps.token)
		    		 .header("api-version", sharedSteps.ApiVersion)
		    		 .contentType("application/json")
		    		 .body("{\r\n" + 
		    		 		"    \"userID\": \""+User_ID+"\",\r\n" + 
		    		 		"    \"requesterUserID\": \""+User_ID+"\",\r\n" + 
		    		 		"    \"portalID\": \""+portal_ID+"\",\r\n" + 
		    		 		"    \"Username\": \" "+old_Username+"_ByAuto\" \r\n" + 
		    		 		"}    ")
		    		.when()
		    		 .post("/User/UpdateUserInfo")
		    	    .then()
		    		 .extract().response();
		   
		   sharedSteps.write_into_FileAsString("./responses/UpdateUsername", sharedSteps.response.prettyPrint());
	   
	}
	
	// calling get user info by code and get username to check its changed successfully 
	@Then("make sure that change take action by calling get user api")
	public void make_sure_that_change_take_action_by_calling_get_user_api() throws Exception {
		sharedSteps.response = sharedSteps.call_Get_user_info_api_by_code(User_ID);
		assertEquals(old_Username+"_ByAuto",sharedSteps.response.path("result.UserName").toString());
		 System.out.println(old_Username+"_ByAuto = " +sharedSteps.response.path("result.UserName").toString()); 
	  
	}

}