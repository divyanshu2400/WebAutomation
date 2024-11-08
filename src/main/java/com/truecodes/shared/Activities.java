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

    public void type(WebElement element, String values) {
        element.clear();
        element.sendKeys(values);
    }
    public void enterKey(WebElement element){
        element.sendKeys(Keys.ENTER);
    }

    public void hover(WebElement element){
        Actions actions = new Actions(this.driver);
        actions.moveToElement(element).perform();
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