package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import static org.junit.Assert.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;
import pojo.*;

public class stepDefinition extends Utils{
	RequestSpecification reqspec;
	ResponseSpecification resspec;
	Response response;
	TestDataBuild data =new TestDataBuild();
	static String place_id;
	
	@Given("Add Place Payload with {string}  {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
	   
		System.out.println("add_place_payload_with_name");
		 reqspec=given().spec(requestSpecification())
					.body(data.addPlacePayLoad(name,language,address));
	
	}
	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
		
		APIResources resourceAPI=APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		resspec =getResponseSpec();
		if (method.equalsIgnoreCase("POST")) {
	        response = reqspec.when()
	                          .post(resourceAPI.getResource())
	                          .then()
	                          .spec(resspec)
	                          .extract()
	                          .response();
	    } else if (method.equalsIgnoreCase("GET")) {
	        response = reqspec.when()
	                          .get(resourceAPI.getResource())
	                          .then()
	                          .spec(resspec)
	                          .extract()
	                          .response();
	    }
	}
	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) {
	   
		System.out.println("the_api_call_got_success_with_status_code");
		assertEquals(response.getStatusCode(),200);
		
	    
	}
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String Expectedvalue) {
		System.out.println("Response Body: " + response.getBody().asString());

		assertEquals(getJsonPath(response,keyValue),Expectedvalue);
	    
	}
	@Then("verify place_Id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
	   		 
	     place_id=getJsonPath(response,"place_id");
		 reqspec=given().spec(requestSpecification()).queryParam("place_id",place_id);
		 user_calls_with_http_request(resource,"GET");
		 AddPlace getPlaceResponse = response.as(AddPlace.class);

		// Now you can directly access fields
		
		System.out.println("Name: " + getPlaceResponse.getName());
		System.out.println("Language: " + getPlaceResponse.getLanguage());
		System.out.println("Address: " + getPlaceResponse.getAddress());

		 
		// String actualName=getJsonPath(response,"name");
		 
		  assertEquals(getPlaceResponse.getName(),expectedName);
	}

	


}