package api.endpoints;

import static io.restassured.RestAssured.given;

import java.util.ResourceBundle;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

//userEndpoints.java
//created to perform Create, Read, Update and deleterequest in the user API. Here we are using properties file instead of Routes.java 

public class UserEndPointsByPropertiesFile {
	
	//method created for getting URLs from properties file.
	static ResourceBundle getURL(){
		ResourceBundle routes = ResourceBundle.getBundle("Routes");  //by this we are loading properties file to gat in use first."Routes is name of properties file."
		return routes;
	}

	public static Response createUser(User payload) {
		String post_url = getURL().getString("post_url");  //get post URL from properties file
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload).when().post(post_url);
		return response;
	}

	public static Response readUser(String username) {
		String get_url = getURL().getString("get_url");
		Response response = given().pathParam("username", username).when().get(get_url);
		return response;
	}

	public static Response updateUser(String username, User payload) {
		String update_url = getURL().getString("update_url");
		Response response = given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.pathParam("username", username).
				body(payload).when().put(update_url);
		return response;
	}
	
	public static Response deleteUser(String username) {
		String delete_url = getURL().getString("delete_url");
		Response response = given().pathParam("username", username).when().delete(delete_url);
		return response;
	}
}
