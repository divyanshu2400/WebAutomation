package com.truecodes;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class APIDocTest extends BaseSetup{
    Map<String, String> api1=new HashMap<>();
    Map<String, String> api2=new HashMap<>();
    Map<String, String> api4=new HashMap<>();
    Map<String, String> api9=new HashMap<>();
    Map<String, String> api13=new HashMap<>();



    public void testProductSchemaValidation(String apiUrl) {
        // Replace with your actual URL
        String url = apiUrl;

        // Send GET request to fetch the products
        Response response = RestAssured
                .given()
                .when()
                .get(url)
                .then()
                .extract()
                .response();

        // Validate the JSON response schema
        response.then().assertThat()
                .body(matchesJsonSchemaInClasspath("schema.json"));  // Ensure the schema.json is in your classpath

        System.out.println("Response matches the expected schema.");
    }

    @Test(priority = 2)
    public void validateApi1(){
        // API1 GET Response
        // Send the request using RestAssured
        Response responseApi1 = RestAssured.given()
                .given()
                .when()
                .get(api1.get("url"))
//                .get("https://automationexercise.com/api/productsList")
                .then()
                .extract()
                .response();

        // Validate the response code
        String actualResponseCode = String.valueOf(responseApi1.getStatusCode());
        Assert.assertEquals(actualResponseCode, api1.get("statusCode"), "Response code mismatch");
        // Extract response body as a string for debugging purposes
        String responseBody = responseApi1.getBody().asString();
        System.out.println("Response: " + responseBody);

        // Extract the list of products using JsonPath
        JsonPath jsonPath = responseApi1.jsonPath();

        // Get the list of products from the response (adjust path based on your actual response structure)
        int productCount = jsonPath.getList("products").size();

        // 1. Verify the number of products
        int expectedProductCount = 34; // Adjust this to the expected number of products
        MatcherAssert.assertThat("Product count does not match expected!", productCount, equalTo(expectedProductCount));

        System.out.println("Total number of products: " + productCount);
        //Validate schema
        testProductSchemaValidation(api1.get("url"));
    }

    @Test(priority = 3)
    public void validateApi2(){
        RestAssured.baseURI = api2.get("url");

        // Send the POST request
        Response response = RestAssured
                .given()
                .contentType("application/json")
                .post();

        // Get the response body as a string
        String responseBody = response.asString();

        // Print the response body (which will be in HTML format)
        System.out.println("Response Body: " + responseBody);

        // Check if the status code is 405
        Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(api2.get("statusCode")), "Expected status code is 200");
        // Verify response message
        Assert.assertEquals(response.jsonPath().getString("message"), api2.get("response").trim(), "Response message does not match the expected message");
    }

    @Test(priority = 4)
    public void validateApi4(){
        RestAssured.baseURI = api4.get("url");

        // Send the POST request
        Response response = RestAssured
                .given()
                .contentType("application/json")
                .put();

        // Get the response body as a string
        String responseBody = response.asString();

        // Print the response body (which will be in HTML format)
        System.out.println("Response Body: " + responseBody);

        // Check if the status code is 405
        Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(api4.get("statusCode")), "Expected status code is 405");
        // Verify response message
        Assert.assertEquals(response.jsonPath().getString("message"), api4.get("response").trim(), "Response message does not match the expected message");
    }
    @Test(priority = 5)
    public void validateApi9(){
        RestAssured.baseURI = api9.get("url");

        // Send the POST request
        Response response = RestAssured
                .given()
                .contentType("application/json")
                .delete();

        // Get the response body as a string
        String responseBody = response.asString();

        // Print the response body (which will be in HTML format)
        System.out.println("Response Body: " + responseBody);

        // Check if the status code is 405
        Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(api9.get("statusCode")), "Expected status code is 405");
        // Verify response message
        Assert.assertEquals(response.jsonPath().getString("message"), api9.get("response").trim(), "Response message does not match the expected message");
    }

    @Test(priority =6)
    public void validateApi14(){
        RestAssured.baseURI = api13.get("url");
        RestAssured.baseURI = "https://automationexercise.com/api/getUserDetailByEmail";

        String email = "example@example.com";

        Response response = RestAssured.given()
                .queryParam("email", email)
                .get();

        String responseBody = response.asString();

        System.out.println("Response Body: " + responseBody);

        // Assert the response code is 200 OK
        Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(api13.get("statusCode")), "Expected status code is 200");
        // Assert response contatins user detail

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
    }

    @Test(priority = 1)
    public void testApiLists() throws InterruptedException {
        // Navigate to APIs list for practice
        driver.findElement(By.xpath("//div[@class='item active']//button[@type='button'][normalize-space()='APIs list for practice']")).click();
        // expanding 5 apis
        expandApi1();
        expandApi2();
        expandApi4();
        expandApi9();
        expandApi14();

    }
    // expanding API1
    public void expandApi1() {
        // expanding API1 GET method
        WebElement API1Element = driver.findElement(By.xpath("//u[normalize-space()='API 1: Get All Products List']"));

        jse.executeScript("arguments[0].scrollIntoView()", API1Element);
        wait.until(ExpectedConditions.elementToBeClickable(API1Element)).click();
        // Locate the <ul> element with the class "list-group"
        WebElement listGroup = driver.findElement(By.className("list-group"));

        // Extract the API URL
        WebElement apiUrlElement = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//li[b/text()='API URL:']/a"))));
        String apiUrl = apiUrlElement.getAttribute("href");
        // Extract the Request Method
        WebElement requestMethodElement = listGroup.findElement(By.xpath("//li[b/text()='Request Method:']"));
        String requestMethod = requestMethodElement.getText().replace("Request Method: ", "");

        // Extract the Response Code
        WebElement responseCodeElement = listGroup.findElement(By.xpath("//li[b/text()='Response Code:']"));
        String responseCode = responseCodeElement.getText().replace("Response Code: ", "");

        // Extract the Response JSON
        WebElement responseJsonElement = listGroup.findElement(By.xpath("//li[b/text()='Response JSON:']"));
        String responseJson = responseJsonElement.getText().replace("Response JSON: ", "");
        api1.put("url",apiUrl);
        api1.put("method",requestMethod);
        api1.put("statusCode",(responseCode));
        api1.put("response",responseJson);
        // Output the captured values
        System.out.println("API URL: " + apiUrl);
        System.out.println("Request Method: " + requestMethod);
        System.out.println("Response Code: " + responseCode);
        System.out.println("Response JSON: " + responseJson);


        // Assert if the Request Method is GET
        Assert.assertEquals("GET", requestMethod, "Request Method should be GET!");

        // Assert if the Response Code is 200
        Assert.assertEquals("200", responseCode, "Response Code should be 200!");
        System.out.println("Assertions passed: Request Method is GET and Response Code is 200.");
    }

    public void expandApi2() {
        // expanding API1 GET method
        WebElement API2Element = driver.findElement(By.xpath("//u[normalize-space()='API 2: POST To All Products List']"));

        jse.executeScript("arguments[0].scrollIntoView()", API2Element);
        wait.until(ExpectedConditions.elementToBeClickable(API2Element)).click();
        // Locate the <ul> element with the class "list-group"
        WebElement listGroup = driver.findElement(By.className("list-group"));

        // Extract the API URL
        WebElement apiUrlElement = listGroup.findElement(By.xpath("//li[b/text()='API URL:']/a"));
        String apiUrl = apiUrlElement.getAttribute("href");
        // Extract the Request Method
        WebElement requestMethodElement = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("/html[1]/body[1]/section[1]/div[1]/div[3]/div[1]/div[2]/ul[1]/li[2]"))));
        String requestMethod = requestMethodElement.getText().replace("Request Method: ", "");

        // Extract the Response Code
        WebElement responseCodeElement = driver.findElement(By.xpath("/html[1]/body[1]/section[1]/div[1]/div[3]/div[1]/div[2]/ul[1]/li[3]"));
        String responseCode = responseCodeElement.getText().replace("Response Code: ", "");

        // Extract the Response JSON
        WebElement responseJsonElement = driver.findElement(By.xpath("/html[1]/body[1]/section[1]/div[1]/div[3]/div[1]/div[2]/ul[1]/li[4]"));
        String responseJson = responseJsonElement.getText().replace("Response Message: ", "");
        api2.put("url",apiUrl);
        api2.put("method",requestMethod);
        api2.put("statusCode",responseCode);
        api2.put("response",responseJson);
        // Output the captured values
        System.out.println("API URL: " + apiUrl);
        System.out.println("Request Method: " + requestMethod);
        System.out.println("Response Code: " + responseCode);
        System.out.println("Response JSON: " + responseJson);
        // Assert if the Request Method is post
        Assert.assertEquals("POST", requestMethod, "Request Method should be POST!");
        // Assert if the Response Code is 405
        Assert.assertEquals("405", responseCode, "Response Code should be 405!");


        System.out.println("Assertions passed: Request Method is POST and Response Code is 405.");
    }


    // expanding API4
    public void expandApi4(){
        // expanding API1 GET method
        WebElement API4Element =  driver.findElement(By.xpath("//u[normalize-space()='API 4: PUT To All Brands List']"));
        jse.executeScript("arguments[0].scrollIntoView()", API4Element);
        wait.until(ExpectedConditions.elementToBeClickable(API4Element)).click();

        // Extract the API URL
        WebElement apiUrlElement = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='collapse4']//a[@target='_blank']"))));
        String apiUrl = apiUrlElement.getAttribute("href");
        // Extract the Request Method
        WebElement requestMethodElement = driver.findElement(By.xpath("/html[1]/body[1]/section[1]/div[1]/div[5]/div[1]/div[2]/ul[1]/li[2]"));
        String requestMethod = requestMethodElement.getText().replace("Request Method: ", "");

        // Extract the Response Code
        WebElement responseCodeElement = driver.findElement(By.xpath("/html[1]/body[1]/section[1]/div[1]/div[5]/div[1]/div[2]/ul[1]/li[3]"));
        String responseCode = responseCodeElement.getText().replace("Response Code: ", "");

        // Extract the Response JSON
        WebElement responseJsonElement = driver.findElement(By.xpath("/html[1]/body[1]/section[1]/div[1]/div[5]/div[1]/div[2]/ul[1]/li[4]"));
        String responseJson = responseJsonElement.getText().replace("Response Message: ", "");

        // Output the captured values
        System.out.println("API URL: " + apiUrl);
        System.out.println("Request Method: " + requestMethod);
        System.out.println("Response Code: " + responseCode);
        System.out.println("Response Message: " + responseJson);
        api4.put("url",apiUrl);
        api4.put("method",requestMethod);
        api4.put("statusCode",responseCode);
        api4.put("response",responseJson);
        // Assert if the Request Method is GET
        Assert.assertEquals("PUT", requestMethod, "Request Method should be GET!");

        // Assert if the Response Code is 200
        Assert.assertEquals("405", responseCode, "Response Code should be 200!");

        System.out.println("Assertions passed: Request Method is GET and Response Code is 200.");

    }

    // expanding API9
    public void expandApi9(){
        // expanding API1 GET method
        WebElement API9Element =  driver.findElement(By.xpath("//u[normalize-space()='API 9: DELETE To Verify Login']"));
        jse.executeScript("arguments[0].scrollIntoView()", API9Element);
        wait.until(ExpectedConditions.elementToBeClickable(API9Element)).click();

        // Extract the API URL
        WebElement apiUrlElement = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='collapse9']//a[@target='_blank']"))));
        String apiUrl = apiUrlElement.getAttribute("href");
        // Extract the Request Method
        WebElement requestMethodElement = driver.findElement(By.xpath("//div[@id='collapse9']//li[2]"));
        String requestMethod = requestMethodElement.getText().replace("Request Method: ", "");

        // Extract the Response Code
        WebElement responseCodeElement = driver.findElement(By.xpath("//div[@id='collapse9']//li[3]"));
        String responseCode = responseCodeElement.getText().replace("Response Code: ", "");

        // Extract the Response JSON
        WebElement responseJsonElement = driver.findElement(By.xpath("//div[@id='collapse9']//li[4]"));
        String responseJson = responseJsonElement.getText().replace("Response Message: ", "");
        api9.put("url",apiUrl);
        api9.put("method",requestMethod);
        api9.put("statusCode",responseCode);
        api9.put("response",responseJson);
        // Output the captured values
        System.out.println("API URL: " + apiUrl);
        System.out.println("Request Method: " + requestMethod);
        System.out.println("Response Code: " + responseCode);
        System.out.println("Response Message: " + responseJson);

        // Assert if the Request Method is Delete
        Assert.assertEquals("DELETE", requestMethod, "Request Method should be DELETE!");

        // Assert if the Response Code is 200
        Assert.assertEquals("405", responseCode, "Response Code should be 405!");

        System.out.println("Assertions passed: Request Method is DELETE and Response Code is 405.");

    }

    // expanding API13
    public void expandApi14(){
        // expanding API1 GET method
        WebElement API14Element = driver.findElement(By.xpath("//u[normalize-space()='API 14: GET user account detail by email']"));
        jse.executeScript("arguments[0].scrollIntoView()", API14Element);
        wait.until(ExpectedConditions.elementToBeClickable(API14Element)).click();

        // Extract the API URL
        WebElement apiUrlElement = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='collapse14']//a[@target='_blank']"))));
        String apiUrl = apiUrlElement.getAttribute("href");
        // Extract the Request Method
        WebElement requestMethodElement = driver.findElement(By.xpath("//div[@id='collapse14']//li[2]"));
        String requestMethod = requestMethodElement.getText().replace("Request Method: ", "");

        // Extract the Request Parameters
        WebElement requestParamElement = driver.findElement(By.xpath("//div[@id='collapse14']//li[3]"));
        String requestParam = requestParamElement.getText().replace("Request Parameters: ", "");

        // Extract the Response Code
        WebElement responseCodeElement = driver.findElement(By.xpath("//div[@id='collapse14']//li[4]"));
        String responseCode = responseCodeElement.getText().replace("Response Code: ", "");

        // Extract the Response JSON
        WebElement responseJsonElement = driver.findElement(By.xpath("//div[@id='collapse14']//li[5]"));
        String responseJson = responseJsonElement.getText().replace("Response JSON: ", "");
        api13.put("url",apiUrl);
        api13.put("method",requestMethod);
        api13.put("statusCode",responseCode);
        api13.put("params",requestParam);
        api13.put("response",responseJson);
        // Output the captured values
        System.out.println("API URL: " + apiUrl);
        System.out.println("Request Method: " + requestMethod);
        System.out.println("Request Parameters: " + requestParam);
        System.out.println("Response Code: " + responseCode);
        System.out.println("Response JSON: " + responseJson);

        // Assert if the Request Method is PUT
        Assert.assertEquals("GET", requestMethod, "Request Method should be GET!");

        // Assert if the Response Code is 200
        Assert.assertEquals("200", responseCode, "Response Code should be 200!");

        System.out.println("Assertions passed: Request Method is PUT and Response Code is 200.");

    }

}
