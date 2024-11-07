package com.truecodes;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class HomePageElementTest extends BaseSetup{

    @Test
    public void verifyMenuOptions() {
        // Locate the top navigation menu items
        List<WebElement> menuItems = driver.findElements(By.cssSelector(".navbar-nav > li"));
        Assert.assertEquals(menuItems.size(), 8, "Expected 8 menu items");

        // List of expected menu options
        String[] expectedMenuItems = {
                "Home", "Products", "Cart", "Signup / Login",
                "Test Cases", "API Testing", "Video Tutorials", "Contact us"
        };

        for (int i = 0; i < expectedMenuItems.length; i++) {
            Assert.assertTrue(menuItems.get(i).getText().contains(expectedMenuItems[i]),
                    "Menu item not found: " + expectedMenuItems[i]);
        }
    }
    @Test
    public void verifyCarouselSlides() {
        // Locate the carousel items within the carousel-inner div
        List<WebElement> carouselSlides = driver.findElements(By.cssSelector("#slider-carousel .carousel-inner .item"));

        // Verify that exactly 3 carousel slides are present
        Assert.assertEquals(carouselSlides.size(), 3, "Expected 3 carousel slides");

        // Check if the first slide has the class 'active'
        WebElement firstSlide = carouselSlides.get(0);
        Assert.assertTrue(firstSlide.getAttribute("class").contains("active"), "First slide should be active");

        // Optionally, you can also check that the other two slides don't have the 'active' class
        WebElement secondSlide = carouselSlides.get(1);
        WebElement thirdSlide = carouselSlides.get(2);
        Assert.assertFalse(secondSlide.getAttribute("class").contains("active"), "Second slide should not be active");
        Assert.assertFalse(thirdSlide.getAttribute("class").contains("active"), "Third slide should not be active");
    }
    @Test
    public void verifyBrandsSection() {
        WebElement brandsSection = driver.findElement(By.xpath("//h2[normalize-space()='Brands']"));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", brandsSection);

        wait.waitUntilVisibility(brandsSection);
        List<WebElement> brandListItems = driver.findElements(By.xpath("//div[@class='brands-name']//ul[@class='nav nav-pills nav-stacked']//li"));

        Assert.assertEquals(brandListItems.size(), 8, "Expected 9 brands in the list");

        // Optionally, verify that each brand name is visible and has the correct format
        for (WebElement brandItem : brandListItems) {
            String brandName = brandItem.getText();
            Assert.assertFalse(brandName.isEmpty(), "Brand name should not be empty");
            Assert.assertTrue(brandName.contains("("), "Brand name should have a product count in parentheses");
        }
    }
    @Test
    public void verifyFeaturedSection(){
        WebElement featuresSection = driver.findElement(By.xpath("//h2[normalize-space()='Features Items']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", featuresSection);
         wait.waitUntilVisibility(featuresSection);

        List<WebElement> products = driver.findElements(By.cssSelector(".features_items .col-sm-4"));

        // Verify that several products are displayed (e.g., at least 3 products in the first row)
        Assert.assertTrue(products.size() >= 3, "Less than 3 products are displayed in the Featured Items section");
        for (int i = 0; i < Math.min(3, products.size()); i++) {  // Check the first 3 products
            WebElement product = products.get(i);

            // Verify that the product has an image
            WebElement productImage = product.findElement(By.cssSelector(".productinfo img"));
            Assert.assertTrue(productImage.isDisplayed(), "Product image is not displayed for product " + (i + 1));

            // Verify that the product has a name
            WebElement productName = product.findElement(By.cssSelector(".productinfo p"));
            Assert.assertTrue(productName.isDisplayed(), "Product name is not displayed for product " + (i + 1));

            // Verify that the product has a price
            WebElement productPrice = product.findElement(By.cssSelector(".productinfo h2"));
            Assert.assertTrue(productPrice.isDisplayed(), "Product price is not displayed for product " + (i + 1));

            // Verify that the "Add to cart" button is visible
            WebElement addToCartButton = product.findElement(By.cssSelector(".add-to-cart"));
            Assert.assertTrue(addToCartButton.isDisplayed(), "Add to cart button is not visible for product " + (i + 1));

            // Optionally: Verify the "View Product" link
            WebElement viewProductLink = product.findElement(By.cssSelector(".choose .nav-pills li a"));
            Assert.assertTrue(viewProductLink.isDisplayed(), "View Product link is not visible for product " + (i + 1));
        }

        // Verify that the products are displayed in rows of 3
        for (int i = 0; i < products.size(); i++) {
            // Each product should be inside a div with class 'col-sm-4'
            WebElement product = products.get(i);
            Assert.assertTrue(product.getAttribute("class").contains("col-sm-4"), "Product " + (i + 1) + " is not correctly placed in a 3-column grid");
        }
    }

    @Test
    public void footerSubsSection(){
        WebElement footerSection = driver.findElement(By.xpath("//h2[normalize-space()='Subscription']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", footerSection);
        wait.waitUntilVisibility(footerSection);

        WebElement emailInput = wait.waitUntilVisibilityById(driver,"susbscribe_email");
        Assert.assertTrue(emailInput.isDisplayed(), "Email input field is not displayed in the footer");

        // Locate the submit button
        WebElement subscribeButton = driver.findElement(By.id("subscribe"));
        Assert.assertTrue(subscribeButton.isDisplayed(), "Subscribe button is not displayed in the footer");

        // Optionally, check if the submit button is clickable
        Assert.assertTrue(subscribeButton.isEnabled(), "Subscribe button is not clickable");

        // Check if the input is interactable by entering an email
        emailInput.sendKeys("divyanshu0924@gmail.com");

        // Verify the submit button is still clickable after entering an email
        Assert.assertTrue(subscribeButton.isEnabled(), "Subscribe button is not clickable after entering an email");

    }

}
