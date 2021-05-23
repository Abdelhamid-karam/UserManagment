package UM.UserManagment;






import UM.sharedSteps.sharedSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;





public class GetBulkUserInfo{
	String user1_ID;
	String user2_ID;
	String user3_ID;
	String wrongID_formate;
	String notfound_ID;
	
	String user2_email;
	String user1_username;
	

	@Given("I have list of usersID")
	public void i_have_list_of_usersID() throws Exception {
		user1_ID = sharedSteps.readFileAsString("./data/User1_code");
		user2_ID = sharedSteps.readFileAsString("./data/User2_code");
		user3_ID = sharedSteps.readFileAsString("./data/User3_code");
		wrongID_formate = "123";
		notfound_ID = "497179696822"; 
	}

	@When("I post Get users info By codes API")
	public void i_post_Get_users_info_By_codes_API() throws Exception {
		 sharedSteps.get_url();
		  RestAssured.baseURI=sharedSteps.url;
		  sharedSteps.response= RestAssured
				 .given()
				   .auth().oauth2(sharedSteps.token)
				   .header("api-version", sharedSteps.ApiVersion)
				   .contentType("application/json")
				   .body("[" +user1_ID+","+user2_ID+","+user3_ID+","+wrongID_formate+","+notfound_ID+"]")
				 .when()
				   .post("List/User/Info/Code")
			     .then()
				    .extract().response();
		  
		   sharedSteps.write_into_FileAsString("./responses/GetUserInfoByCodes", sharedSteps.response.prettyPrint());
	}
	
	@Given("I have list of Emails and username")
	public void i_have_list_of_Emails_and_username() throws Exception {
		user2_email = sharedSteps.readFileAsString("./data/User2_email");
		user1_username = sharedSteps.readFileAsString("./data/User1_username");
		System.out.println("list of username and email : "+user2_email +" , "  + user1_username ); 
	}

	@When("I post Get users info By usersName or Email")
	public void i_post_Get_users_info_By_usersName_or_Email() throws Exception {
		sharedSteps.get_url();
		  RestAssured.baseURI=sharedSteps.url;
		  sharedSteps.response= RestAssured
				 .given()
				   .auth().oauth2(sharedSteps.token)
				   .header("api-version", sharedSteps.ApiVersion)
				   .contentType("application/json")
				   .body( 
				   		"     [ \""+user2_email +"\", \""+user1_username+"\"]   \r\n"  
				   		)
				 .when()
				   .post("/List/User/Info/UsersNameOrEmail")
			     .then()
			     .log().all()
				    .extract().response();
		  
		   sharedSteps.write_into_FileAsString("./responses/Get users info By usersName or Email", sharedSteps.response.prettyPrint());
	   
	}
}