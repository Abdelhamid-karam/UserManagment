package UM.UserManagment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import UM.sharedSteps.sharedSteps;
import cucumber.api.java.en.When;
import org.apache.http.client.methods.HttpPost;



public class confirmUser{

	String User;
	StringBuffer UserResult = new StringBuffer();
	

	
	  @When("^I call confirm user api$")
	  public void confirm_user() throws Throwable {
		  sharedSteps.get_url();
		  HttpClient client = HttpClientBuilder.create().build();
		  URIBuilder builder = new URIBuilder(sharedSteps.url+"User/Confirm");	
		  System.out.println("Token : " + sharedSteps.Verify_token.toString());
		  String Verify_Token= sharedSteps.Verify_token.toString().replaceAll("^\"|\"$", "");
		  System.out.println("Verify_Token : " + Verify_Token);
		  builder.setParameter("token", Verify_Token);
		  HttpPost request = new HttpPost (builder.build());
			// add request header
		  request.addHeader("Authorization", sharedSteps.token);
		  request.addHeader("Content-Type", "application/x-www-form-urlencoded"); 
		 
		  sharedSteps.response = client.execute(request);
		  System.out.println("Response Code : " + sharedSteps.response.getStatusLine().getStatusCode());
	      System.out.println("Confirm Response:"+ sharedSteps.response.getEntity().getContent());
		  BufferedReader rd = new BufferedReader(new InputStreamReader(sharedSteps.response.getEntity().getContent()));
		  String line = "";
		  StringBuffer result = new StringBuffer();
		  while ((line = rd.readLine()) != null) 
		  {
			  result.append(line);
	      }
		  sharedSteps.responseContent= result.toString();
		  System.out.println("Response Code : " + sharedSteps.responseContent);

		  BufferedWriter writer = new BufferedWriter(new FileWriter("./responses/confirmUser"));
		  writer.write(sharedSteps.responseContent);
		  writer.close();
	  }
	
	
}