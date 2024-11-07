package com.truecodes.shared;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;

public class PageWaits {
    private static PageWaits pageWaits = null;
    private WebDriver driver = null;

    private PageWaits(WebDriver driver) {
        this.driver = driver;
    }
    public static PageWaits getPageWaitsObject(WebDriver driver){
        if(PageWaits.pageWaits == null){
            PageWaits.pageWaits = new PageWaits(driver);
        }
        return PageWaits.pageWaits;
    }

    private FluentWait<WebDriver> waitForElement(){
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class);
    }
    public void waitAnonymous(WebElement webElement, boolean[] booleans, String attribute, String className){
        waitForElement().until((ExpectedCondition<Boolean>) driver -> {
            booleans[0] = webElement.getAttribute(attribute).contains(className);
            return booleans[0];
        });

    }
    public WebElement waitUntilElementToBeClickable(WebElement webElement){
        return waitForElement().until(ExpectedConditions.elementToBeClickable(webElement));
    }
    public WebElement waitUntilElementToBeClickable(String xpath){
        return waitForElement().until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }
    public WebElement waitUntilElementToBeClickable(WebDriver webDriver, String xpath){
        return waitForElement().until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.xpath(xpath))));
    }
    public WebElement waitUntilVisibility(String xpath){
        return waitForElement().until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(xpath))));
    }
    public void waitUntilVisibility(WebElement webElement){
        waitForElement().until(ExpectedConditions.visibilityOf(webElement));
    }
    public WebElement waitUntilVisibilityById(WebDriver webDriver, String id){
        return waitForElement().until(ExpectedConditions.visibilityOf(webDriver.findElement(By.id(id))));
    }
    public WebElement waitUntilVisibilityOfElementLocatedLinkText(String text){
        return waitForElement().until(ExpectedConditions.visibilityOfElementLocated(By.linkText(text)));
    }
    public WebElement waitUntilVisibilityOfWebElementByCssSelector(WebElement webElement, String text){
        return waitForElement().until(ExpectedConditions.visibilityOf(webElement.findElement(By.cssSelector(text))));
    }
    public WebElement waitUntilVisibilityOfWebElementByClassName(String text){
        return waitForElement().until(ExpectedConditions.visibilityOfElementLocated(By.className(text)));
    }
    public void waitUntilUrlToBe(String url){
        waitForElement().until(ExpectedConditions.urlToBe(url));
    }
    public WebElement waitUntilElementFoundByPartialLink(String partialLink){
        return waitForElement().until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(partialLink)));
    }
    public WebElement waitUntilElementFoundByID(String elementID){
        return waitForElement().until(ExpectedConditions.visibilityOfElementLocated(By.id(elementID)));
    }
    public WebElement waitUntilElementFoundByName(String elementName){
        return waitForElement().until(ExpectedConditions.visibilityOfElementLocated(By.name(elementName)));
    }
    public WebElement waitUntilElementFoundByCSS(String cssLocator){
        return waitForElement().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssLocator)));
    }
    public List<WebElement> waitUntilListOfElementFoundByCss(String cssLocator){
        return waitForElement().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(cssLocator)));
    }
    public WebElement waitUntilElementFoundByXPath(String xpath){
        return waitForElement().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public void waitForTitleToBeChanged(String titleName){
         waitForElement().until(ExpectedConditions.titleIs(titleName));
    }
}