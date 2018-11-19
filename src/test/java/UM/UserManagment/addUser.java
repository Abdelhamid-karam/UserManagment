package UM.UserManagment;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import UM.sharedSteps.sharedSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import gherkin.deps.com.google.gson.JsonObject;
import gherkin.deps.com.google.gson.JsonParser;
import org.apache.http.client.methods.HttpPost;



public class addUser{

	String User;
	StringBuffer UserResult = new StringBuffer();

	@Given("^I have user data$")
	public void get_user_data() throws Throwable {
		File UserData = new File("./data/AddUser.txt"); 
		String line=null;
		try {
				BufferedReader br = new BufferedReader(new FileReader(UserData));
				while ((line= br.readLine()) != null)
				{	
					UserResult.append(line);
				}
				User = UserResult.toString();
				br.close();
			}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		System.out.println("User Data :"+ UserResult);
	}
	
	  @When("^I call add user api$")
	  public void add_user() throws Throwable {
		  sharedSteps.get_url();
		  HttpClient client = HttpClientBuilder.create().build();
		  URIBuilder builder = new URIBuilder(sharedSteps.url+"User/Add");		  
		  HttpPost request = new HttpPost(builder.build());
			// add request header
		  request.addHeader("Authorization", sharedSteps.token);
		  request.addHeader("Content-Type", "application/json");
		  JsonParser parser = new JsonParser();
	      Object resultObject = parser.parse(User);
	      JsonObject userJson =(JsonObject)resultObject;
	      userJson.get("users").getAsJsonArray().get(0).getAsJsonObject().addProperty("email", sharedSteps.Unique_Email);
	      userJson.get("users").getAsJsonArray().get(0).getAsJsonObject().addProperty("userName", sharedSteps.Unique_username);
	      System.out.println("user after parsing ="+userJson.toString());
          request.setEntity(new StringEntity(userJson.toString(),ContentType.create("application/json")));    
		  sharedSteps.response = client.execute(request);	 
		  System.out.println("Response Code : " + sharedSteps.response.getStatusLine().getStatusCode());

		  BufferedReader rd = new BufferedReader(new InputStreamReader(sharedSteps.response.getEntity().getContent()));
		  String line = "";
		  StringBuffer result = new StringBuffer();
		  while ((line = rd.readLine()) != null) 
		      {
			  result.append(line);
			  }
		  sharedSteps.responseContent= result.toString();
	      System.out.println("Response: "+sharedSteps.responseContent);
	      

			JsonParser parser1 = new JsonParser();
	        Object resultObject1 = parser1.parse(sharedSteps.responseContent);
		     System.out.println("Parsed: "+resultObject1);
	        JsonObject obj1 =(JsonObject)resultObject1;
	        sharedSteps.Verify_token=  obj1.get("result").getAsJsonArray().get(0).getAsJsonObject().get("verificationToken");
	        System.out.println("verify token  = "+ sharedSteps.Verify_token);
		  BufferedWriter writer = new BufferedWriter(new FileWriter("./responses/addedUser"));
		  writer.write(sharedSteps.responseContent);
		  writer.close();
		  parser1 = new JsonParser();
	      resultObject1 = parser1.parse(sharedSteps.responseContent);
	      sharedSteps.jsonResponse =(JsonObject)resultObject1;
	  }
	
	
}