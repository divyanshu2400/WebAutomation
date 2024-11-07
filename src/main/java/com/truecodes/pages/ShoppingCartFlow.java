package com.truecodes.pages;

import com.truecodes.shared.Actions;
import com.truecodes.shared.FindElements;
import com.truecodes.shared.PageWaits;
import com.truecodes.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartFlow {
    private final FindElements findElements;
    private static ShoppingCartFlow shoppingCartFlowInstance;
    private final Actions actions;
    private final PageWaits wait;
    private final WebDriver driver;

    private ShoppingCartFlow(WebDriver webDriver){
        this.findElements = FindElements.getInstance(webDriver);
        this.actions = Actions.getActionsObject(webDriver);
        this.wait = PageWaits.getPageWaitsObject(webDriver);
        this.driver = webDriver;
    }
    public static synchronized ShoppingCartFlow getShoppingCartInstance(WebDriver driver){
        if(shoppingCartFlowInstance == null){
            shoppingCartFlowInstance = new ShoppingCartFlow(driver);
        }
        return shoppingCartFlowInstance;
    }
    public WebElement navigateToTshirt(){
        WebElement menCategory = findElements.ByXPath("//a[normalize-space()='Men']");
        actions.scrollWindow(menCategory);
        wait.waitUntilVisibility(menCategory);

        // Open Men category section
        actions.clickElement(menCategory);

        // now open t-shirts section under men
        WebElement tshirt = findElements.ByXPath("//a[normalize-space()='Tshirts']");
        wait.waitUntilVisibility(tshirt);
        actions.clickElement(tshirt);

        // wait until url get loaded
        wait.waitUntilUrlToBe(ConfigReader.getBaseURL() + "category_products/3");

        return wait.waitUntilVisibilityOfElementLocatedLinkText("TSHIRTS");
    }
}
