package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDTests {

	// we are grabbing here data from excel present in testData folder. We are using
	// XLUtility to extract data and with DataProviders passing data to method.

	// first we are creating multiple users here. For that we are grabbing
	// information from excel required for payload using getAllData method of
	// XLUtility. Than in 2nd test we are hitting delete request to delete all users
	// for which we only require usernames instead of all data from payload. So for
	// getting only usernames we are using method UserNames from XLUtility
	// class.

	@Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
	public void testPostUSer(String userID, String userName, String fName, String lName, String userEmail, String pwd,
			String phone) {
		User userPayload = new User();
		userPayload.setId(Integer.parseInt(userID));
		userPayload.setUsername(userName);
		userPayload.setFirstName(fName);
		userPayload.setLastName(lName);
		userPayload.setEmail(userEmail);
		userPayload.setPassword(pwd);
		userPayload.setPhone(phone);

		Response response = UserEndPoints.createUser(userPayload);
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority = 2,dataProvider = "UserNames",dataProviderClass = DataProviders.class)
	public void testDeleteUSerByName(String username) {
		Response response = UserEndPoints.deleteUser(username);
		Assert.assertEquals(response.getStatusCode(), 200);
	}

}
