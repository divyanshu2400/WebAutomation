package com.truecodes;
import com.truecodes.shared.PageWaits;
import com.truecodes.utils.ConfigReader;
import com.truecodes.utils.DriverCreator;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.WebDriver;

public class BaseSetup {
    public static final String PAGE_URL = "https://automationexercise.com/";
    PageWaits wait = null;
    Actions actions;
    JavascriptExecutor jse;
    WebDriver driver = DriverCreator.instantiateDriver(ConfigReader.getBrowser());

    @BeforeClass
    public void setUp(){
        driver.manage().window().maximize();//maximizing the chrome window
        wait = PageWaits.getPageWaitsObject(driver);
        actions = new Actions(driver);
        jse = (JavascriptExecutor) driver;
        driver.get(PAGE_URL);// Open the target webpage
    }
    @AfterSuite
    public void tearDown(){
        driver.quit();
    }
}
