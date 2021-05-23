package UM.UserManagment;

import static io.restassured.RestAssured.given;


import UM.sharedSteps.sharedSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class createPortal {
	public static String portal_id;
	public static String portal_URL;

	@Given("^I have unique portal ID & URL$")
	public void get_portal_data() throws Throwable {
		// get auth for 12 digit API
		RestAssured.baseURI = sharedSteps.auth_URL;
		Response auth_response = 
			given().urlEncodingEnabled(true)
				.param("client_id", "nagwa.de")
				.param("client_secret", "sQ6qqzeKtnKSUdBdtG5xqr3uTpnDeBZSv").param("scope", "nagwa.12digits")
				.param("grant_type", "client_credentials").header("Accept", ContentType.JSON.getAcceptHeader())
			.when()
			.post()
			.then()
			.extract().response();

		String token = auth_response.path("access_token").toString();
		System.out.println("auth token for 12 didgit API  = " + token);

		// call 12-digit API to get 12 digit for creating new portal
		RestAssured.baseURI = "https://12digit.nagwa.com/get.test.code/";
		String twelve_digit_response = 
			given().auth().oauth2(token)
			.header("api-version", 2)
			.when()
			.get()
			.then()
			.extract().response().asString();

		sharedSteps.write_into_FileAsString("./data/portal_ID",twelve_digit_response.substring(1, twelve_digit_response.length()- 1));
		System.out.println("12 digit = " + twelve_digit_response);
		portal_id = twelve_digit_response.substring(1, twelve_digit_response.length()-1);
		portal_URL = "automation_portal_" + portal_id + ".com";
		sharedSteps.write_into_FileAsString("./data/portal_URL",portal_URL);
		System.out.println("portal_id " + portal_id);
		System.out.println("portal_URL " + portal_URL);

	}

	@When("^I call Create New Portal api$")
	public void Create_New_Portal() throws Throwable {
		sharedSteps.get_url();
		RestAssured.baseURI = sharedSteps.url;
		sharedSteps.response = 
				given()
				.auth().oauth2(sharedSteps.token)
				.header("api-version", sharedSteps.ApiVersion)
				.param("portalID", portal_id)
				.param("portalURL", portal_URL)
			.when()
			    .post("/Portal/CreateNewPortal")
			.then()
			   .extract().response();
		
		sharedSteps.write_into_FileAsString("./responses/createportal", sharedSteps.response.prettyPrint());
	}

}
