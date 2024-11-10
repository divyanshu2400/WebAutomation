package com.truecodes.shared;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class Activities {
    private static Activities activities = null;
    private WebDriver driver = null;

    private Activities(WebDriver driver) {
        this.driver = driver;
    }

    public static Activities getActionsObject(WebDriver driver) {
        if (Activities.activities == null) {
            Activities.activities = new Activities(driver);
        }
        return Activities.activities;
    }

    public void clickElement(WebElement element) {
        element.click();
    }
    public void hover(WebElement webElement){
        Actions actions = new Actions(driver);
        actions.moveToElement(webElement).perform();
    }
    public void type(WebElement element, String values) {
        element.clear();
        element.sendKeys(values);
    }
    public void enterKey(WebElement element){
        element.sendKeys(Keys.ENTER);
    }

    public void Maximize() {
        driver.manage().window().maximize();
    }

    public void navigateTo(String url) {
        driver.get(url);
    }

    public void scrollWindow(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView()", element);
    }

}