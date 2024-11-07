package com.truecodes;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class ShoppingCartFlowTest extends BaseSetup{
    @Test
    public void addProductsToCartTest() {
        // 1. Launch the Website
        driver.get("https://automationexercise.com/");

        // 2. Navigate to "Men" Category and "T-shirts" Subcategory
        WebElement menCategory = driver.findElement(By.linkText("Men"));
        actions.moveToElement(menCategory).build().perform(); // Hover over "Men" category

        // Wait for the subcategory to be visible
        WebElement tshirtSubcategory = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.linkText("T-shirts")));
        tshirtSubcategory.click(); // Click on "T-shirts"

        // 3. Scroll to "Pure Cotton Neon Green Tshirt"
        WebElement neonGreenTshirt = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Pure Cotton Neon Green Tshirt')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", neonGreenTshirt);

        // 4. Verify Product Details (Price, Add to Cart button, and View Details button)
        WebElement productPrice = driver.findElement(By.xpath("//a[contains(text(),'Pure Cotton Neon Green Tshirt')]/ancestor::div[@class='productinfo text-center']//span[@class='price']"));
        WebElement addToCartButton = driver.findElement(By.xpath("//a[contains(text(),'Pure Cotton Neon Green Tshirt')]/ancestor::div[@class='productinfo text-center']//button[text()='Add to cart']"));
        WebElement viewDetailsButton = driver.findElement(By.xpath("//a[contains(text(),'Pure Cotton Neon Green Tshirt')]/ancestor::div[@class='productinfo text-center']//button[text()='View Details']"));

        Assert.assertTrue(productPrice.isDisplayed(), "Product price should be visible");
        Assert.assertTrue(addToCartButton.isDisplayed(), "Add to Cart button should be visible");
        Assert.assertTrue(viewDetailsButton.isDisplayed(), "View Details button should be visible");

        // 5. Hover Over the Product
        actions.moveToElement(neonGreenTshirt).build().perform();

        // 6. Click on "Add to Cart" Button
        addToCartButton.click();

        // 7. Verify Success Message after adding the product to the cart
        WebElement successMessage = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='alert alert-success alert-dismissible']//button[@class='close']")));
        Assert.assertTrue(successMessage.isDisplayed(), "Success message should be displayed");

        // 8. Click on "Continue Shopping" Button
        WebElement continueShoppingButton = driver.findElement(By.xpath("//button[text()='Continue Shopping']"));
        continueShoppingButton.click();

        // 9. Add Another T-shirt to Cart (Any other option)
        WebElement anotherTshirt = driver.findElement(By.xpath("//a[contains(text(),'Men Tshirt')]")); // You can change the T-shirt name
        WebElement addAnotherToCartButton = driver.findElement(By.xpath("//a[contains(text(),'Men Tshirt')]/ancestor::div[@class='productinfo text-center']//button[text()='Add to cart']"));
        actions.moveToElement(anotherTshirt).build().perform();
        addAnotherToCartButton.click();

        // 10. Click on "View Cart" from the Success Popup
        WebElement viewCartButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/view_cart']")));
        viewCartButton.click();

        // 11. Verify Products in Cart
        WebElement cartProductName1 = driver.findElement(By.xpath("//table[@class='table table-bordered table-hover table-striped']//tr[1]//td[2]"));
        WebElement cartProductPrice1 = driver.findElement(By.xpath("//table[@class='table table-bordered table-hover table-striped']//tr[1]//td[3]"));
        WebElement cartProductName2 = driver.findElement(By.xpath("//table[@class='table table-bordered table-hover table-striped']//tr[2]//td[2]"));
        WebElement cartProductPrice2 = driver.findElement(By.xpath("//table[@class='table table-bordered table-hover table-striped']//tr[2]//td[3]"));

        // Verify product names and prices are correct
        Assert.assertEquals(cartProductName1.getText(), "Pure Cotton Neon Green Tshirt", "Product name should match in the cart");
        Assert.assertEquals(cartProductPrice1.getText(), productPrice.getText(), "Product price should match in the cart");
        Assert.assertEquals(cartProductName2.getText(), "Men Tshirt", "Product name should match in the cart");
        // Note: Update the expected product name for second product as per the actual test data

        // 12. Click on "Proceed to Checkout" Button
        WebElement proceedToCheckoutButton = driver.findElement(By.xpath("//button[@class='btn btn-default check_out']"));
        proceedToCheckoutButton.click();
    }

}
