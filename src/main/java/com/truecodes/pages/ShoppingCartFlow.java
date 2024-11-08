package com.truecodes.pages;

import com.truecodes.shared.Activities;
import com.truecodes.shared.FindElements;
import com.truecodes.shared.PageWaits;
import com.truecodes.utils.ConfigReader;
import org.openqa.selenium.By;
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
    private WebElement getWebElementByCssAndWebElemnet(WebElement webElement, String cssLocator){
        return findElements.ByCSSAndWebElement(webElement, cssLocator);
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
        activities.clickElement(menCategory);// Open Men category section
        WebElement tshirt = findElements.ByXPath("//a[normalize-space()='Tshirts']");// now open t-shirts section under men
        wait.waitUntilVisibility(tshirt);
        activities.clickElement(tshirt);
        wait.waitUntilUrlToBe(ConfigReader.getBaseURL() + "category_products/3");// wait until url get loaded
        return wait.waitUntilVisibilityOfElementLocatedLinkText("TSHIRTS");
    }

    public List<WebElement> productDescs(){
        return findElements.getListByCSS(".product-image-wrapper");
    }

    public WebElement productName(WebElement webElement){
        return this.getWebElementByCssAndWebElemnet(webElement, "p");
    }
    public WebElement productPrice(WebElement webElement){
        return this.getWebElementByCssAndWebElemnet(webElement, "h2");
    }
    public WebElement productImg(WebElement webElement){
        return this.getWebElementByCssAndWebElemnet(webElement, "img");
    }
    public WebElement productLink(WebElement webElement){
        return this.getWebElementByCssAndWebElemnet(webElement, ".choose .nav-pills li a");
    }
    public WebElement addToCartBtn(WebElement webElement){
        return this.getWebElementByCssAndWebElemnet(webElement, ".add-to-cart");
    }
    public WebElement neonTshirt(WebElement webElement){
        // Scroll to "Pure Cotton Neon Green Tshirt"
        activities.scrollWindow(webElement);
        wait.waitUntilVisibility(webElement);

        // Hover over the tshirt
        activities.hover(webElement);
        // Wait for the overlay to become visible after hover
        return wait.waitUntilVisibilityOfWebElementByCssSelector(webElement,".product-overlay");
    }
    public WebElement neonName(WebElement webElement){
        return this.getWebElementByCssAndWebElemnet(webElement, ".overlay-content p");
    }
    public WebElement neonPrice(WebElement webElement){
        return this.getWebElementByCssAndWebElemnet(webElement, ".overlay-content h2");
    }
    public WebElement neonAddToCartBtn(WebElement webElement){
        return this.getWebElementByCssAndWebElemnet(webElement, ".overlay-content .add-to-cart");
    }

    public List<WebElement> modalVisibility(WebElement addToCartButtonHover, boolean [] modalVisible){
        // Click on "Add to Cart" Button:
        addToCartButtonHover.click();
        List<WebElement> modalElements = new ArrayList<>();

        // Locate the modal by its ID
        WebElement modal = findElements.ByID("cartModal");

        // Custom wait condition to check for the 'show' class in the modal

        wait.waitAnonymous(modal,modalVisible,"class","show");
        WebElement modalContent = wait.waitUntilVisibilityOfWebElementByClassName("modal-content");
        // Verify modal title is "Added!"
        WebElement modalTitle = findElements.ByClassAndWebElement(modalContent, "modal-title");
        String modalTitleText = modalTitle.getText();

        // Verify modal body text is "Your product has been added to cart."
        WebElement modalBodyText = findElements.ByXPathWebElement(modalContent,"//*[@id=\"cartModal\"]/div/div/div[2]/p[1]");

        // Verify "Continue Shopping" button is present and clickable
        WebElement continueShoppingButton = wait.waitUntilVisibilityOfWebElementByCssSelector(modalContent,".btn.btn-success.close-modal");
        modalElements.add(modal);
        modalElements.add(modalContent);
        modalElements.add(modalTitle);
        modalElements.add(modalBodyText);
        modalElements.add(continueShoppingButton);

        // click on continue shopping button
        continueShoppingButton.click();
        return modalElements;

    }
}
