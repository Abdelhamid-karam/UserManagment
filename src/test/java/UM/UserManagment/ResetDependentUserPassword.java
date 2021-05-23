package UM.UserManagment;






import static io.restassured.RestAssured.given;


import java.io.IOException;

import UM.sharedSteps.sharedSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;





public class ResetDependentUserPassword{
	
	String created_by_id ; 
	String user_ID ;
	String portal_id;
	String encrypted_created_by_id ; 
	String encrypted_user_ID;

	@Given("I have body data")
	public void i_have_body_data() throws Exception {
		 portal_id=sharedSteps.readFileAsString("./data/portal_ID");
		 user_ID = sharedSteps.readFileAsString("./data/User1_code");
		 created_by_id = sharedSteps.readFileAsString("./data/User3_code");
	}
	
	
	@Given("I have encrypted IDs for user and requester")
	public void i_have_encrypted_IDs_for_user_and_requester() throws IOException {
		encrypted_user_ID = sharedSteps.get_encryption(user_ID);
		System.out.println("encrypted_user_ID : "+encrypted_user_ID);
		encrypted_created_by_id= sharedSteps.get_encryption(created_by_id);
		System.out.println("encrypted_created_by_id : "+encrypted_created_by_id);
	    
	    
	}

	@When("I post Reset dependent user password API")
	public void i_post_Reset_dependent_user_password_API() throws Exception {
		sharedSteps.get_url();
		RestAssured.baseURI = sharedSteps.url;
		sharedSteps.response =
		    		given()
		    		 .auth()
		    		 .oauth2(sharedSteps.token)
		    		 .header("api-version", sharedSteps.ApiVersion)
		    		 .header("UserIDEncrypted", encrypted_user_ID)
		    		 .header("RequesterUserIDEncrypted", encrypted_created_by_id)
		    		 .contentType("application/json")
		    		 .body("{\r\n" + 
		    		 		"    \"userID\": \""+user_ID+"\",\r\n" + 
		    		 		"    \"requesterUserID\": \""+created_by_id+"\",\r\n" + 
		    		 		"    \"portalID\": \""+portal_id+"\"\r\n" + 
		    		 		"}    ")
		    		.when()
		    		 .post("/User/ResetDependentPassword")
		    	    .then()
		    		 .extract().response();
		sharedSteps.write_into_FileAsString("./responses/ResetDependentUserPassword", sharedSteps.response.prettyPrint());
	   
	}
}