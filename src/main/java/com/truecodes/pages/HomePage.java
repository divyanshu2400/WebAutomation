package com.truecodes.pages;

import com.truecodes.shared.Actions;
import com.truecodes.shared.FindElements;
import com.truecodes.shared.PageWaits;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class HomePage {
    private static HomePage homePageInstance;
    private final FindElements findElements;
    private final Actions actions;
    private final PageWaits wait;
    private final WebDriver driver;
    private HomePage(WebDriver webDriver){
        this.actions = Actions.getActionsObject(webDriver);
        this.findElements = FindElements.getInstance(webDriver);
        this.wait = PageWaits.getPageWaitsObject(webDriver);
        this.driver = webDriver;
    }
    public static synchronized HomePage getInstance(WebDriver webDriver){
        if(homePageInstance == null){
            homePageInstance = new HomePage(webDriver);
        }
        return homePageInstance;
    }
    public List<WebElement> listElements(String cssLocators){
        return findElements.getListByCSS(cssLocators);
    }
    private List<WebElement> listElementsByXpath(String xpath){
        return findElements.getListByXpath(xpath);
    }
    public List<WebElement> goToBrandsSection(){
        WebElement brandsSection = findElements.ByXPath("//h2[normalize-space()='Brands']");
        actions.scrollWindow(brandsSection);
        wait.waitUntilVisibility(brandsSection);
        return this.listElementsByXpath("//div[@class='brands-name']//ul[@class='nav nav-pills nav-stacked']//li");

    }
    public List<WebElement> goToFeaturedSection(){
        WebElement featuresSection = findElements.ByXPath("//h2[normalize-space()='Features Items']");
        actions.scrollWindow(featuresSection);
        wait.waitUntilVisibility(featuresSection);
        return listElements(".features_items .col-sm-4");
    }
    public List<WebElement> goToFooterSection(){
        List<WebElement> webElements = new ArrayList<>();
        WebElement footerSection = findElements.ByXPath("//h2[normalize-space()='Subscription']");
        actions.scrollWindow(footerSection);
        wait.waitUntilVisibility(footerSection);

        WebElement emailInput = wait.waitUntilVisibilityById(driver,"susbscribe_email");
        WebElement subscribeButton = findElements.ByID("subscribe");
        webElements.add(footerSection);
        webElements.add(emailInput);
        webElements.add(subscribeButton);
        return webElements;
    }
}
