package com.truecodes;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class BaseSetup {
    protected WebDriver driver;
    public static final String PAGE_URL = "https://automationexercise.com/";
    WebDriverWait wait;
    Actions actions;
    JavascriptExecutor jse;
    @BeforeClass
    public void setUp(){
        driver = new ChromeDriver();//setting up the new chrome driver
        driver.manage().window().maximize();//maximizing the chrome window

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
        jse = (JavascriptExecutor) driver;
        driver.get(PAGE_URL);// Open the target webpage
    }
    @AfterClass
    public void tearDown(){
//        driver.quit();
    }
}
