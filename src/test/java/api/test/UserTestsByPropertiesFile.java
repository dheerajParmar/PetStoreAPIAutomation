package api.test;


import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPointsByPropertiesFile;
import api.payload.User;
import io.restassured.response.Response;

public class UserTestsByPropertiesFile {

	Faker faker;
	User userPayload;
	
	public Logger logger;   //for logs

	@BeforeClass
	void setup() {
		faker = new Faker();
		userPayload = new User();
		

		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logs 
		logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass());
	}

	@Test(priority = 1)
	public void testPostUser() {
		logger.info("************  Creating user ***************");  //added just to track logs code working fine
		Response response = UserEndPointsByPropertiesFile.createUser(userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("************  User created ***************");
	}
	
	@Test(priority = 2)
	public void testGetUserByName() {
		logger.info("************  Reading User Info ***************");
		Response response = UserEndPointsByPropertiesFile.readUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("************  user info is displayed ***************");
	}
	
	@Test(priority = 3)
	public void testUpdateUserByName() {
		logger.info("************  Updating user info ***************");
		//update some fields
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndPointsByPropertiesFile.updateUser(this.userPayload.getUsername(),userPayload);
		response.then().log().body();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("************  user is updated ***************");
		
		//checking if data updated:
		Response responseAfterUpdate = UserEndPointsByPropertiesFile.readUser(this.userPayload.getUsername());
		responseAfterUpdate.then().log().all();
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
	}	
	
	@Test(priority = 4)
	public void testDeleteUSerByName() {
		logger.info("************  deleting user ***************");
		Response response = UserEndPointsByPropertiesFile.deleteUser(this.userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("************  user deleted ***************");
	}	
}
