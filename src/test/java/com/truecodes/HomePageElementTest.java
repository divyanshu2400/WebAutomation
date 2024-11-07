package com.truecodes;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class HomePageElementTest extends BaseSetup{


    public static final String PAGE_URL = "https://automationexercise.com/";


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
        // Locate the brands section container (the div containing the list of brands)
        WebElement brandsSection = driver.findElement(By.xpath("//h2[normalize-space()='Brands']"));

        // Scroll down to the brands section to ensure it's in the viewport
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", brandsSection);

        // Wait until the brands section is visible (optional, but helpful for stability)
        wait.until(ExpectedConditions.visibilityOf(brandsSection));
        // Locate the brands list items
        List<WebElement> brandListItems = driver.findElements(By.xpath("//div[@class='brands-name']//ul[@class='nav nav-pills nav-stacked']//li"));

        // Verify that exactly 9 brands are listed
        Assert.assertEquals(brandListItems.size(), 8, "Expected 9 brands in the list");

        // Optionally, verify that each brand name is visible and has the correct format
        for (WebElement brandItem : brandListItems) {
            String brandName = brandItem.getText();
            Assert.assertFalse(brandName.isEmpty(), "Brand name should not be empty");
            Assert.assertTrue(brandName.contains("("), "Brand name should have a product count in parentheses");
        }
    }


}
