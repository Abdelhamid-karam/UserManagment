package UM.UserManagment;

import static io.restassured.RestAssured.given;

import UM.sharedSteps.sharedSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;






public class linkUserToPortal{
	String portal_id ;
	String portal_URL  ;
	String user_code ; 
	String link_on ;

	
	@Given("^I have user code to be linked on \"([^\"]+)\"$")
	public void i_have_user_code(String S) throws Exception {
		link_on=S;
	    if (S.equalsIgnoreCase("nagwa")) { 
	    	user_code = sharedSteps.readFileAsString("./data/User1_code");
	    	System.out.println(user_code +" will be linked on nagwa portal");
	    }else if (S.equalsIgnoreCase("portal")) {
	    	user_code = sharedSteps.readFileAsString("./data/User2_code");
	    	System.out.println(user_code +"will be linked on new portal");
	    }else {
	    	System.out.println("fi haga 3'alat o-O"); 
	    }
	    
	}
	
	@Given("I have portal ID and URL")
	public void i_have_nagwa_portal_ID_and_URL() throws Exception {
		 if (link_on.equalsIgnoreCase("nagwa")) {
			 portal_id = "464161291713";//Nagwa portal ID same on all environments 
			 portal_URL = "nagwa.com";//Nagwa portal URL  same on all environments
		    	
		    }else if (link_on.equalsIgnoreCase("portal")) {
		    	portal_id = sharedSteps.readFileAsString("./data/portal_ID");
		    	portal_URL= sharedSteps.readFileAsString("./data/portal_URL");
		    }else {
		    	System.out.println("fi haga 3'alat o-O"); 
		    }
	}
	
	@When("I post link user account to portal")
	public void i_post_link_user_account_to_portal() throws Exception {
		sharedSteps.get_url();
		RestAssured.baseURI = sharedSteps.url;
		sharedSteps.response = 
			given()
			    .auth().oauth2(sharedSteps.token)
			    .header("api-version", sharedSteps.ApiVersion)
				.param("userId", user_code)
				.param("portalID", portal_id)
				.param("portalURL", portal_URL)
			.when()
				.post("/Portal/link")
			.then()
			.extract().response();
		sharedSteps.write_into_FileAsString("./responses/LinkUserOnPortal", sharedSteps.response.prettyPrint());
	}
}