package com.truecodes.pages;

import com.truecodes.shared.Activities;
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
    private final Activities activities;
    private final PageWaits wait;
    private final WebDriver driver;

    private ShoppingCartFlow(WebDriver webDriver){
        this.findElements = FindElements.getInstance(webDriver);
        this.activities = Activities.getActionsObject(webDriver);
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
        activities.scrollWindow(menCategory);
        wait.waitUntilVisibility(menCategory);

        // Open Men category section
        activities.clickElement(menCategory);

        // now open t-shirts section under men
        WebElement tshirt = findElements.ByXPath("//a[normalize-space()='Tshirts']");
        wait.waitUntilVisibility(tshirt);
        activities.clickElement(tshirt);

        // wait until url get loaded
        wait.waitUntilUrlToBe(ConfigReader.getBaseURL() + "category_products/3");

        return wait.waitUntilVisibilityOfElementLocatedLinkText("TSHIRTS");
    }
    public List<WebElement> getListTshirt(){
        return findElements.getListByCSS(".product-image-wrapper");
    }
    public List<WebElement> getTshirtElements(WebElement webElement){
        List<WebElement> listTshirtDesc= new ArrayList<>();
        WebElement productNameElement = findElements.byWebElementNCss(webElement,"p");
        WebElement productPriceElement = findElements.byWebElementNCss(webElement,"h2");
        WebElement productImage = findElements.byWebElementNCss(webElement,"img");
        WebElement viewProductLink = webElement.findElement(By.cssSelector(".choose .nav-pills li a"));
        WebElement addToCartButton = findElements.byWebElementNCss(webElement,".add-to-cart");
        listTshirtDesc.add(productNameElement);
        listTshirtDesc.add(productPriceElement);
        listTshirtDesc.add(productImage);
        listTshirtDesc.add(viewProductLink);
        listTshirtDesc.add(addToCartButton);
        return listTshirtDesc;
    }
    public void baseWork(WebElement webElement){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webElement);
        wait.waitUntilVisibility(webElement);
    }
    public List<WebElement> verifyHover(WebElement webElement){
        List<WebElement> overlay= new ArrayList<>();
        activities.hover(webElement);
        // Wait for the overlay to become visible after hover
        WebElement productOverlay = wait.waitUntilVisibilityOfWebElementByCssSelector(webElement,".product-overlay");
        WebElement priceElement = findElements.byWebElementNCss(productOverlay,".overlay-content h2");
        WebElement addToCartButtonHover = findElements.byWebElementNCss(productOverlay,".overlay-content .add-to-cart");
        WebElement overlayTextElement = findElements.byWebElementNCss(productOverlay,".overlay-content p");
        overlay.add(productOverlay);
        overlay.add(priceElement);
        overlay.add(addToCartButtonHover);
        overlay.add(overlayTextElement);
        return overlay;
    }
    public List<WebElement> modalVerify(){
        List<WebElement> modalDesc= new ArrayList<>();
        WebElement modal = findElements.ByID("cartModal");// Locate the modal by its ID
        final boolean[] modalVisible = new boolean[1];
        wait.waitAnonymous(modal,modalVisible,"class","show");//condition to check for the 'show' class in the modal
        WebElement modalContent = wait.waitUntilVisibilityOfWebElementByClassName("modal-content");
        WebElement modalTitle = findElements.byWebElementNClass(modalContent,"modal-title");// Verify modal title is "Added!"
        // Verify modal body text is "Your product has been added to cart."
        WebElement modalBodyText = findElements.byWebElementNXpath(modalContent,"//*[@id=\"cartModal\"]/div/div/div[2]/p[1]");
        WebElement continueShoppingButton = wait.waitUntilVisibilityOfWebElementByCssSelector(modalContent,".btn.btn-success.close-modal");
        modalDesc.add(modal);
        modalDesc.add(modalTitle);
        modalDesc.add(modalBodyText);
        modalDesc.add(continueShoppingButton);
        return modalDesc;
    }
    public List<WebElement> verifyGraphic(WebElement webElement){
        List<WebElement> eles= new ArrayList<>();
        activities.hover(webElement);
        WebElement productOverlay = wait.waitUntilVisibilityOfWebElementByCssSelector(webElement,".product-overlay");
        WebElement priceElement = findElements.byWebElementNCss(webElement,".overlay-content h2");// Validate that the price is visible
        WebElement addToCartButtonHover = wait.waitUntilVisibilityOfWebElementByCssSelector(productOverlay,".overlay-content .add-to-cart");
        eles.add(priceElement);
        eles.add(addToCartButtonHover);
        return eles;
    }
    public List<WebElement> viewCart(){
        // click on view cart button
        WebElement viewCart = wait.waitUntilElementToBeClickable("//*[@id=\"cartModal\"]/div/div/div[2]/p[2]/a");
        activities.clickElement(viewCart);
        // wait until cart get opened
        wait.waitUntilUrlToBe("https://automationexercise.com/view_cart");
        WebElement cartTableBody = driver.findElement(By.cssSelector("#cart_info_table tbody"));
        return findElements.byWebElementNTag(cartTableBody,"tr");
    }
}
