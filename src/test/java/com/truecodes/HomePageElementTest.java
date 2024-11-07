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

public class HomePageElementTest extends BaseSetup{
    WebDriverWait wait;
    Actions actions;
    JavascriptExecutor jse;

    public static final String PAGE_URL = "https://automationexercise.com/";

    @Test
    public void testHomePageElements() {
        driver.get(PAGE_URL);// Open the target webpage
        
        // Create WebDriverWait instance (wait up to 10 seconds)
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
        jse = (JavascriptExecutor) driver;


    }
}
