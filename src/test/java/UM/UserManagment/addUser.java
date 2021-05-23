package UM.UserManagment;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import UM.sharedSteps.sharedSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

import io.restassured.RestAssured;

public class addUser {

	String API_body;
	String Nagwa_portal_id = "464161291713"; // nagwa portal ID that its configured in config file of development project same in all environments  
	String created_by_id ="206179704078"; // id for walaa.akram@nagwa.com on beta, demo and live
	String uniqueID ; // used to give unique username and email 
	String user_form ; // variable sent from feature file according to it we will send specific user body 
	String user_code ;
	String user_name ;
	String user_email;
	
//form add user API body according to its specifications sent from feature file 
	@Given("^I have user data \"([^\"]+)\"$")
	public void get_user_data(String userform) throws Throwable {
		uniqueID  = UUID.randomUUID().toString();// used to give unique username and email 
		user_form = userform;
		String portal_id=sharedSteps.readFileAsString("./data/portal_ID");
		
		if (user_form.equalsIgnoreCase("without_email_createdBy_onPortal")) {// if user is student > user name will be auto generated and it will be verified by default no verificationToken will be sent in response body
			System.out.println("user : " + userform);
		    API_body ="{\r\n" + 
					"   \"users\": [\r\n" + 
					"    {\r\n" + 
					"       \"firstName\": \"Automation\",\r\n" + 
					"       \"lastName\": \"API\",\r\n" + 
					"       \"birthDate\": \"2019-05-28T11:47:57.734Z\"\r\n" + 
					"    }\r\n" + 
					"   ],\r\n" + 
					"   \"createdByCode\": " + created_by_id + ",\r\n" + 
					"   \"countryIso\": \"eg\",\r\n" + 
					"   \"languageIso\": \"en\",\r\n" + 
					"   \"portalID\": \" "+ portal_id + "\"\r\n" + 
					"}";  
		}else if(user_form.equalsIgnoreCase("with_email_notCreatedBy_onNagwa")) { // with email can be admin, teacher , co-teacher , educator and account will need to be verified so verificationToken will be sent in response body
			System.out.println("user : " + userform); 
		    API_body ="{\r\n" + 
		    		"   \"users\": [\r\n" + 
		    		"    {\r\n" + 
		    		"       \"firstName\": \"Automation\",\r\n" + 
		    		"       \"lastName\": \"API\",\r\n" + 
		    		"       \"email\": \"automation." + uniqueID + "@nagwa.com\",\r\n" + 
		    		"       \"userName\": \"automation." + uniqueID + "\",\r\n" + 
		    		"       \"birthDate\": \"1988-06-17\",\r\n" + 
		    		"       \"plainPassword\": \"123456\"\r\n" + 
		    		"    }\r\n" + 
		    		"   ],\r\n" + 
		    		"   \"createdByCode\": null,\r\n" + 
		    		"   \"countryIso\": \"eg\",\r\n" + 
		    		"   \"languageIso\": \"en\",\r\n" + 
		    		"   \"portalID\": "+ Nagwa_portal_id +"\r\n" + 
		    		"}";
		}else if (user_form.equalsIgnoreCase("with_email_createdBy_onPortal")) {// with email and created by not null  can be teacher , co-teacher , educator and account will be verified by default no verificationToken will be sent in response body
			 System.out.println("user : " + userform.toString());
			    API_body ="{\r\n" + 
						"   \"users\": [\r\n" + 
						"    {\r\n" + 
						"       \"firstName\": \"Automation\",\r\n" + 
						"       \"lastName\": \"API\",\r\n" + 
						"       \"email\": \"automation." + uniqueID + "@portal.com\",\r\n" + 
			    		"       \"userName\": \"automation." + uniqueID + "\",\r\n" + 
						"       \"birthDate\": \"2019-05-28T11:47:57.734Z\",\r\n" +  
						"    }\r\n" + 
						"   ],\r\n" + 
						"   \"createdByCode\": " + created_by_id + ",\r\n" + 
						"   \"countryIso\": \"eg\",\r\n" + 
						"   \"languageIso\": \"en\",\r\n" + 
						"   \"portalID\": \" "+ portal_id + "\"\r\n" + 
						"}";  
		}else {
			System.out.println("something went wrong o-O"); 
		}
	}
	
	// calling add user API function to use it with different body data  
	public void  add_User_function() throws Exception {
		sharedSteps.get_url();
		RestAssured.baseURI = sharedSteps.url;
		sharedSteps.response =
		    		given()
		    		 .auth()
		    		 .oauth2(sharedSteps.token)
		    		 .header("api-version", sharedSteps.ApiVersion)
		    		 .contentType("application/json")
		    		 .body(API_body)
		    		.when()
		    		 .post("/User/Add")
		    	    .then()
		    		 .extract().response();
		user_code = sharedSteps.response.path("result.code").toString();
		user_name = sharedSteps.response.path("result.userName").toString();
	    user_email = sharedSteps.response.path("result.email").toString();
					
					
	}

	@When("^I call add user api$")
	public void add_user() throws Throwable {
		 
		 
		
		if (user_form.equalsIgnoreCase("without_email_createdBy_onPortal")) {// no verificationToken with response code 207 cuz dependant account 
		    add_User_function();
			sharedSteps.write_into_FileAsString("./data/User1_code", user_code.substring(1, user_code.length()-1));
			sharedSteps.write_into_FileAsString("./data/User1_userName", user_name.substring(1, user_name.length()-1));
    		sharedSteps.write_into_FileAsString("./responses/addedUser1", sharedSteps.response.prettyPrint());	
		}
		else if(user_form.equalsIgnoreCase("with_email_notCreatedBy_onNagwa")) { // verificationToken will be sent with response code 200 cuz not dependant account   
			 add_User_function();
   		     System.out.println("verificationToken " + sharedSteps.response.path("result.verificationToken").toString()); 
   		     assertNotNull(sharedSteps.response.path("result.verificationToken").toString());
   		     sharedSteps.write_into_FileAsString("./data/User2_verificationToken", sharedSteps.response.path("result.VerificationToken").toString());
   		     sharedSteps.write_into_FileAsString("./data/User2_code", user_code.substring(1, user_code.length()-1));
   		     sharedSteps.write_into_FileAsString("./data/User2_email", user_email.substring(1, user_email.length()-1));
   		     sharedSteps.write_into_FileAsString("./data/User2_username", user_name.substring(1, user_name.length()-1));
   	      	 sharedSteps.write_into_FileAsString("./responses/addedUser2", sharedSteps.response.prettyPrint());
		}
		else if (user_form.equalsIgnoreCase("with_email_createdBy_onPortal")) {// no verificationToken with response code 200 cuz dependant account 
			 add_User_function();
			 sharedSteps.write_into_FileAsString("./data/User3_code", user_code.substring(1, user_code.length()-1));
    		 sharedSteps.write_into_FileAsString("./responses/addedUser3", sharedSteps.response.prettyPrint());
		}else {
			System.out.println("something went wrong o-O"); 
		}
	}

}