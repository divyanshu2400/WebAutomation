package com.truecodes;
import com.truecodes.pages.HomePage;
import com.truecodes.shared.FindElements;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class HomePageElementTest extends BaseSetup{
    HomePage homePage = HomePage.getInstance(driver);
    FindElements findElements = FindElements.getInstance(driver);
    @Test
    public void verifyMenuOptions() {
        // Locate the top navigation menu items
        List<WebElement> menuItems = homePage.listElements(".navbar-nav > li");

        // List of expected menu options
        String[] expectedMenuItems = {
                "Home", "Products", "Cart", "Signup / Login",
                "Test Cases", "API Testing", "Video Tutorials", "Contact us"
        };
        Assert.assertEquals(menuItems.size(), 8, "Expected 8 menu items");
        for (int i = 0; i < expectedMenuItems.length; i++) {
            Assert.assertTrue(menuItems.get(i).getText().contains(expectedMenuItems[i]),
                    "Menu item not found: " + expectedMenuItems[i]);
        }
    }
    @Test
    public void verifyCarouselSlides() {
        // Locate the carousel items within the carousel-inner div
        List<WebElement> carouselSlides = homePage.listElements("#slider-carousel .carousel-inner .item");
        WebElement firstSlide = carouselSlides.get(0);
        WebElement secondSlide = carouselSlides.get(1);
        WebElement thirdSlide = carouselSlides.get(2);

        // Verify that exactly 3 carousel slides are present
        Assert.assertEquals(carouselSlides.size(), 3, "Expected 3 carousel slides");

        // Check if the first slide has the class 'active'
        Assert.assertTrue(firstSlide.getAttribute("class").contains("active"), "First slide should be active");
        // other two slides don't have the 'active' class
        Assert.assertFalse(secondSlide.getAttribute("class").contains("active"), "Second slide should not be active");
        Assert.assertFalse(thirdSlide.getAttribute("class").contains("active"), "Third slide should not be active");
    }
    @Test
    public void verifyBrandsSection() {
        List<WebElement> brandListItems = homePage.goToBrandsSection();
        Assert.assertEquals(brandListItems.size(), 8, "Expected 8 brands in the list");

        // verify that each brand name is visible and has the correct format
        for (WebElement brandItem : brandListItems) {
            String brandName = brandItem.getText();
            Assert.assertFalse(brandName.isEmpty(), "Brand name should not be empty");
            Assert.assertTrue(brandName.contains("("), "Brand name should have a product count in parentheses");
        }
    }
    @Test
    public void verifyFeaturedSection(){
        List<WebElement> products = homePage.goToFeaturedSection();

        // Verify that several products are displayed (at least 3 products in the first row)
        Assert.assertTrue(products.size() >= 3, "Less than 3 products are displayed in the Featured Items section");
        for (int i = 0; i < Math.min(3, products.size()); i++) {  // Check the first 3 products
            WebElement product = products.get(i);

            // Verify that the product has an image
            Assert.assertTrue(findElements.ByCSS(".productinfo img").isDisplayed(), "Product image is not displayed for product " + (i + 1));

            // Verify that the product has a name
            Assert.assertTrue(findElements.ByCSS(".productinfo p").isDisplayed(), "Product name is not displayed for product " + (i + 1));

            // Verify that the product has a price
            Assert.assertTrue(findElements.ByCSS(".productinfo h2").isDisplayed(), "Product price is not displayed for product " + (i + 1));

            // Verify that the "Add to cart" button is visible
            Assert.assertTrue(findElements.ByCSS(".add-to-cart").isDisplayed(), "Add to cart button is not visible for product " + (i + 1));

            // Verify the "View Product" link
            Assert.assertTrue(findElements.ByCSS(".choose .nav-pills li a").isDisplayed(), "View Product link is not visible for product " + (i + 1));
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
        List<WebElement> footerWebElements = homePage.goToFooterSection();
        WebElement footerSection = footerWebElements.get(0);
        WebElement emailInput = footerWebElements.get(1);
        WebElement subscribeButton = footerWebElements.get(2);
        Assert.assertTrue(emailInput.isDisplayed(), "Email input field is not displayed in the footer");

        // Locate the submit button
        Assert.assertTrue(subscribeButton.isDisplayed(), "Subscribe button is not displayed in the footer");

        // Optionally, check if the submit button is clickable
        Assert.assertTrue(subscribeButton.isEnabled(), "Subscribe button is not clickable");

        // Check if the input is interactable by entering an email
        emailInput.sendKeys("divyanshu0924@gmail.com");

        // Verify the submit button is still clickable after entering an email
        Assert.assertTrue(subscribeButton.isEnabled(), "Subscribe button is not clickable after entering an email");

    }

}
