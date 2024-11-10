package com.truecodes;

import com.truecodes.pages.APIListPage;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class APIListTest extends BaseSetup{
    Map<String, String> api1=new HashMap<>();
    Map<String, String> api2=new HashMap<>();
    Map<String, String> api4=new HashMap<>();
    Map<String, String> api9=new HashMap<>();
    Map<String, String> api13=new HashMap<>();
    APIListPage apiListPage = APIListPage.getInstance(driver);
    // Validate the JSON response schema
    public void testProductSchemaValidation(String schema,Response response) {
        response.then().assertThat()
                .body(matchesJsonSchemaInClasspath(schema));

        System.out.println("Response matches the expected schema.");
    }

    @Test(priority = 1)
    public void testApiLists() {
        apiListPage.openApiListPage();
        // Asserting API1
        api1 = apiListPage.getApi1Elements();
        Assert.assertEquals("GET", api1.get("method"), "Request Method should be GET!");// Assert if the Request Method is GET
        Assert.assertEquals("200", api1.get("statusCode"), "Response Code should be 200!");// Assert if the Response Code is 200
        // Asserting Api2
        api2 = apiListPage.getApi2Elements();
        Assert.assertEquals("POST", api2.get("method"), "Request Method should be POST!");// Assert if the Request Method is post
        Assert.assertEquals("405", api2.get("statusCode"), "Response Code should be 405!");// Assert if the Response Code is 405
        //Asserting API4
        api4 = apiListPage.getApi4Elements();
        Assert.assertEquals("PUT", api4.get("method"), "Request Method should be PUT!");// Assert if the Request Method is PUT
        Assert.assertEquals("405", api4.get("statusCode"), "Response Code should be 405!");// Assert if the Response Code is 405
        // Asserting API9
        api9 = apiListPage.getApi9Elements();
        Assert.assertEquals("DELETE", api9.get("method"), "Request Method should be DELETE!");// Assert if the Request Method is Delete
        Assert.assertEquals("405", api9.get("statusCode"), "Response Code should be 405!");// Assert if the Response Code is 405
        // Asserting API14
        api13 = apiListPage.getApi14Elements();
        Assert.assertEquals("GET", api13.get("method"), "Request Method should be GET!");
        Assert.assertEquals("200", api13.get("statusCode"), "Response Code should be 200!");
    }

    @Test(priority = 2)
    public void validateApi1(){
        // Send the request using RestAssured
        Response responseApi1 = RestAssured.given()
                .given()
                .when()
                .get(api1.get("url"))
                .then()
                .extract()
                .response();
        Assert.assertEquals(String.valueOf(responseApi1.getStatusCode()), api1.get("statusCode"), "Response code mismatch");
        String responseBody = responseApi1.getBody().asString();// Extract response body as a string for debugging purposes
        System.out.println("Response: " + responseBody);
        int productCount = responseApi1.jsonPath().getList("products").size();// Get the list of products from the response
        int expectedProductCount = 34;// verify no. of products
        MatcherAssert.assertThat("Product count does not match expected!", productCount, equalTo(expectedProductCount));
        testProductSchemaValidation("schema.json", responseApi1);//Validate schema
    }

    @Test(priority = 3)
    public void validateApi2(){
        RestAssured.baseURI = api2.get("url");
        // Send the POST request
        Response response = RestAssured
                .given()
                .contentType("application/json")
                .post();
        System.out.println("Response actual: " + response.asString());// Print the response body
        Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(api2.get("statusCode")), "Expected status code is 405");// Check if the status code is 405
        // Verify response message
        Assert.assertEquals(response.jsonPath().getString("message"), api2.get("response").trim(), "Response message does not match the expected message");
        testProductSchemaValidation("schema405.json", response);

    }
    @Test(priority = 4)
    public void validateApi4(){
        RestAssured.baseURI = api4.get("url");
        // Send the POST request
        Response response = RestAssured
                .given()
                .contentType("application/json")
                .put();
        System.out.println("Response actual: " + response.asString());// Print the response body
        Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(api4.get("statusCode")), "Expected status code is 405");// Check if the status code is 405
        // Verify response message
        Assert.assertEquals(response.jsonPath().getString("message"), api4.get("response").trim(), "Response message does not match the expected message");
        testProductSchemaValidation("schema405.json", response);

    }

    @Test(priority = 5)
    public void validateApi9(){
        RestAssured.baseURI = api9.get("url");
        // Send the POST request
        Response response = RestAssured
                .given()
                .contentType("application/json")
                .delete();
        System.out.println("Response Body: " + response.asString());
        Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(api9.get("statusCode")), "Expected status code is 405");
        // Verify response message
        Assert.assertEquals(response.jsonPath().getString("message"), api9.get("response").trim(), "Response message does not match the expected message");
        testProductSchemaValidation("schema405.json",response);

    }

    @Test(priority =6)
    public void validateApi14(){
        RestAssured.baseURI = api13.get("url");

        String email = "example@example.com";

        Response response = RestAssured.given()
                .queryParam("email", email)
                .get();
        System.out.println("Response Body: " + response.asString());
        Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(api13.get("statusCode")), "Expected status code is 200");
        JsonPath jsonResponse = response.jsonPath();
        // Extract user details from the response
        int userId = jsonResponse.getInt("user.id");
        String userName = jsonResponse.getString("user.name");
        String userEmail = jsonResponse.getString("user.email");
        String userTitle = jsonResponse.getString("user.title");
        String userBirthDay = jsonResponse.getString("user.birth_day");
        String userBirthMonth = jsonResponse.getString("user.birth_month");
        String userBirthYear = jsonResponse.getString("user.birth_year");
        String userFirstName = jsonResponse.getString("user.first_name");
        String userLastName = jsonResponse.getString("user.last_name");
        String userAddress1 = jsonResponse.getString("user.address1");
        String userCountry = jsonResponse.getString("user.country");
        String userState = jsonResponse.getString("user.state");
        String userCity = jsonResponse.getString("user.city");
        String userZipcode = jsonResponse.getString("user.zipcode");

        // Perform assertions on the extracted user details
        Assert.assertEquals(userId, 12771, "User ID is incorrect");
        Assert.assertEquals(userName, "Test", "User name is incorrect");
        Assert.assertEquals(userEmail, email, "User email does not match the expected email");
        Assert.assertEquals(userTitle, "Mrs", "User title is incorrect");
        Assert.assertEquals(userBirthDay, "12", "User birth day is incorrect");
        Assert.assertEquals(userBirthMonth, "4", "User birth month is incorrect");
        Assert.assertEquals(userBirthYear, "2002", "User birth year is incorrect");
        Assert.assertEquals(userFirstName, "Test", "User first name is incorrect");
        Assert.assertEquals(userLastName, "Test", "User last name is incorrect");
        Assert.assertEquals(userAddress1, "First line of address", "User address is incorrect");
        Assert.assertEquals(userCountry, "United States", "User country is incorrect");
        Assert.assertEquals(userState, "Alabama", "User state is incorrect");
        Assert.assertEquals(userCity, "Alabama", "User city is incorrect");
        Assert.assertEquals(userZipcode, "ABA-JASW", "User zipcode is incorrect");

        testProductSchemaValidation("schema14.json",response);// Validating schema for the same

    }
}
