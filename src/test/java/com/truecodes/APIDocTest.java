package com.truecodes;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class APIDocTest extends BaseSetup{
    WebDriverWait wait;
    Actions actions;
    JavascriptExecutor jse;
    Map<String, String> api1=new HashMap<>();
    Map<String, String> api2=new HashMap<>();
    Map<String, String> api4=new HashMap<>();
    Map<String, String> api9=new HashMap<>();
    Map<String, String> api13=new HashMap<>();

    public static final String PAGE_URL = "https://automationexercise.com/";


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

    @Test
    public void testApiLists() throws InterruptedException {
        driver.get(PAGE_URL);// Open the target webpage

        // Navigate to APIs list for practice
        driver.findElement(By.xpath("//div[@class='item active']//button[@type='button'][normalize-space()='APIs list for practice']")).click();

        // Create WebDriverWait instance (wait up to 10 seconds)
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
        jse = (JavascriptExecutor)driver;
        // expanding 5 apis
        expandApi1();
        expandApi2();
        expandApi4();
        expandApi9();
        expandApi14();

        // Make API Request: save response for all 5 apis
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
        String responseJson = responseJsonElement.getText().replace("Response Message ", "");
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
