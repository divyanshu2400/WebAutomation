package com.truecodes;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class ShoppingCartFlowTest extends BaseSetup{
    List<WebElement> products;
    @Test(priority = 1)
    public void navigateMenTShirtTest() {
        WebElement menCategory = driver.findElement(By.xpath("//a[normalize-space()='Men']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", menCategory);
        wait.until(ExpectedConditions.visibilityOf(menCategory));

        // Open Men category section
        menCategory.click();

        // now open t-shirts section under men
        WebElement tshirt = driver.findElement(By.xpath("//a[normalize-space()='Tshirts']"));
        wait.until(ExpectedConditions.visibilityOf(tshirt));
        tshirt.click();

        // wait until url get loaded
        wait.until(ExpectedConditions.urlToBe("https://automationexercise.com/category_products/3"));

        WebElement tshirtsLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("TSHIRTS")));

        // Verify the link's text
        String linkText = tshirtsLink.getText();
        Assert.assertEquals(linkText.trim(), "TSHIRTS", "Link text does not match the expected value!");
        // Assert that the href attribute is correct
        String href = tshirtsLink.getAttribute("href");
        Assert.assertEquals(href, "https://automationexercise.com/category_products/3#", "The link URL is incorrect!");

        // Optionally, you can also verify that the element is an <a> tag (anchor tag)
        Assert.assertTrue(tshirtsLink.getTagName().equals("a"), "The element is not a link!");


        // validate tshirts price, product info, add to cart button
         products = driver.findElements(By.cssSelector(".product-image-wrapper"));
        // Define the T-shirt keywords to check in the product name
        String[] tshirtKeywords = {"T-Shirt", "Tshirt", "T-Shirts", "Tshirt", "T SHIRT"};

        for (int i = 0; i < products.size(); i++) {
            WebElement product = products.get(i);
            WebElement productNameElement = product.findElement(By.cssSelector("p"));
            String productName = productNameElement.getText();
            System.out.println(productName);
            boolean containsKeyword = false;
            for (String keyword : tshirtKeywords) {
                if (productName.toLowerCase().contains(keyword.toLowerCase())) {
                    containsKeyword = true;
                    break;
                }
            }

            // Assert that the product name contains at least one of the tshirt keyword
            Assert.assertTrue(containsKeyword, "Product name does not contain any of the expected keywords (T-Shirt, Tshirt, Cotton, etc.): " + productName);

            // Validate the price
            WebElement productPriceElement = product.findElement(By.cssSelector("h2"));
            String productPrice = productPriceElement.getText();
            Assert.assertTrue(productPrice.startsWith("Rs."), "Price format is incorrect for product: " + productName);

            // Validate the image exists
            WebElement productImage = product.findElement(By.cssSelector("img"));
            Assert.assertTrue(productImage.isDisplayed(), "Image is not displayed for product: " + productName);

            // Validate the 'View Product' link is correct
            WebElement viewProductLink = product.findElement(By.cssSelector(".choose .nav-pills li a"));
            String viewProductHref = viewProductLink.getAttribute("href");
            Assert.assertTrue(viewProductHref.contains("/product_details/"), "View Product link is incorrect for product: " + productName);

            // Validate the 'Add to Cart' button exists
            WebElement addToCartButton = product.findElement(By.cssSelector(".add-to-cart"));
            Assert.assertTrue(addToCartButton.isDisplayed(), "Add to Cart button is not displayed for product: " + productName);
        }
    }
    Integer priceNeonExpected;
    @Test(priority = 2)
    public void neonGreenTshirtVisibiltiyTest(){
        WebElement neonProduct=null;
        String productName="";
        for (int i = 0; i < products.size(); i++) {
            neonProduct = products.get(i);
            WebElement productNameElement = neonProduct.findElement(By.cssSelector("p"));
            productName = productNameElement.getText();
            if(productName.trim().contains("Pure Cotton Neon Green Tshirt"))break;
        }
        // Scroll to "Pure Cotton Neon Green Tshirt"
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", neonProduct);
        wait.until(ExpectedConditions.visibilityOf(neonProduct));

//         Verify price, add to cart and view details button is visible

        // verify price
        WebElement productPriceElement = neonProduct.findElement(By.cssSelector("h2"));
        String productPrice = productPriceElement.getText();
        Assert.assertTrue(productPrice.startsWith("Rs."), "Price format is incorrect for product: " + productName);

        // Validate the 'View Product' link is correct
        WebElement viewProductLink = neonProduct.findElement(By.cssSelector(".choose .nav-pills li a"));
        String viewProductHref = viewProductLink.getAttribute("href");
        Assert.assertTrue(viewProductHref.contains("/product_details/"), "View Product link is incorrect for product: " + productName);

        // Validate the 'Add to Cart' button exists
        WebElement addToCartButton = neonProduct.findElement(By.cssSelector(".add-to-cart"));
        Assert.assertTrue(addToCartButton.isDisplayed(), "Add to Cart button is not displayed for product: " + productName);


        // Hover over the tshirt
        actions.moveToElement(neonProduct).perform();
        // Wait for the overlay to become visible after hover
        WebElement productOverlay = wait.until(ExpectedConditions.visibilityOf(neonProduct.findElement(By.cssSelector(".product-overlay"))));
        // Validate the overlay is visible and contains the expected elements

        // Validate that the price is visible
        WebElement priceElement = productOverlay.findElement(By.cssSelector(".overlay-content h2"));
        String priceText = priceElement.getText();
        String saveNeon = priceText.replace("Rs. ","");
        priceNeonExpected = Integer.parseInt(saveNeon);
        Assert.assertTrue(priceText.contains("Rs."), "Price is not displayed or is in an incorrect format: " + priceText);

        // Validate that the "Add to Cart" button is visible
        WebElement addToCartButtonHover = productOverlay.findElement(By.cssSelector(".overlay-content .add-to-cart"));
        Assert.assertTrue(addToCartButtonHover.isDisplayed(), "Add to Cart button is not displayed after hover.");

        // Validate that the text "Pure Cotton Neon Green Tshirt" appears in the overlay
        WebElement overlayTextElement = productOverlay.findElement(By.cssSelector(".overlay-content p"));
        String overlayText = overlayTextElement.getText();
        Assert.assertTrue(overlayText.contains("Pure Cotton Neon Green Tshirt"), "The product name is not correct in the overlay: " + overlayText);

        // Click on "Add to Cart" Button:
        addToCartButtonHover.click();

    }

    @Test(priority = 3)
    public void verifyModal(){
        // Locate the modal by its ID
        WebElement modal = driver.findElement(By.id("cartModal"));
        WebElement show = wait.until(ExpectedConditions.)
        boolean modalVisible = modal.getAttribute("class").contains("show");
        Assert.assertEquals(modalVisible, true, "Modal is not visible");
        WebElement modalContent = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
        // Verify modal title is "Added!"
        WebElement modalTitle = modalContent.findElement(By.className("modal-title"));
        String modalTitleText = modalTitle.getText();
        Assert.assertEquals(modalTitleText, "Added!", "Modal title is not as expected.");

        // Verify modal body text is "Your product has been added to cart."
        WebElement modalBodyText = modalContent.findElement(By.xpath("//div[@class='modal-body']/p)[1]"));
        Assert.assertEquals(modalBodyText.getText().trim(),"Your product has been added to cart.", "Modal body text is not displayed correctly.");

        // Verify "Continue Shopping" button is present and clickable
        WebElement continueShoppingButton = wait.until(ExpectedConditions.visibilityOf(modalContent.findElement(By.cssSelector(".btn.btn-success.close-modal"))));
        Assert.assertTrue(continueShoppingButton.isDisplayed(), "'Continue Shopping' button is not visible.");
        Assert.assertTrue(continueShoppingButton.isEnabled(), "'Continue Shopping' button is not clickable.");

        // click on continue shopping button
        continueShoppingButton.click();


        boolean visibility = modal.getAttribute("class").contains("fade");
        Assert.assertEquals(visibility, true, "Modal is still visible");

        // Add another t-shirt to the cart
        WebElement graphicDesignProduct=null;
        for (int i = 0; i < products.size(); i++) {
            graphicDesignProduct = products.get(i);
            WebElement productNameElement = graphicDesignProduct.findElement(By.cssSelector("p"));
            String productName = productNameElement.getText();
            if(productName.trim().contains("GRAPHIC DESIGN MEN T SHIRT - BLUE"))break;
        }

        // Scroll to "GRAPHIC DESIGN MEN T SHIRT - BLUE"
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", graphicDesignProduct);
        wait.until(ExpectedConditions.visibilityOf(graphicDesignProduct));
        actions.moveToElement(graphicDesignProduct).perform();
        WebElement productOverlay = wait.until(ExpectedConditions.visibilityOf(graphicDesignProduct.findElement(By.cssSelector(".product-overlay"))));

        // Validate that the price is visible
        WebElement priceElement = productOverlay.findElement(By.cssSelector(".overlay-content h2"));
        Integer priceGraphicExpected = Integer.parseInt(priceElement.getText().replace("Rs. ",""));

        WebElement addToCartButtonHover = wait.until(ExpectedConditions.visibilityOf(productOverlay.findElement(By.cssSelector(".overlay-content .add-to-cart"))));
        addToCartButtonHover.click();

        // click on view cart button
        WebElement viewCart = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"cartModal\"]/div/div/div[2]/p[2]/a")));
        viewCart.click();

        // wait until cart get opened
        wait.until(ExpectedConditions.urlToBe("https://automationexercise.com/view_cart"));

        WebElement cartTableBody = driver.findElement(By.cssSelector("#cart_info_table tbody"));

        // Get all the rows (tr) in the cart body
        List<WebElement> rows = cartTableBody.findElements(By.tagName("tr"));

        // Verify the number of products in the cart (expecting 2)
        Assert.assertEquals(rows.size(), 2, "The cart does not contain exactly 2 products.");

        // Now verify the names of the products
        String firstProductName = rows.get(0).findElement(By.cssSelector(".cart_description h4 a")).getText();
        String secondProductName = rows.get(1).findElement(By.cssSelector(".cart_description h4 a")).getText();

        String firstProductPrice = rows.get(0).findElement(By.cssSelector(".cart_price p")).getText().trim();
        String secondProductPrice = rows.get(1).findElement(By.cssSelector(".cart_price p")).getText().trim();



        // Validate the product names
        Assert.assertEquals(firstProductName.trim(), "Pure Cotton Neon Green Tshirt", "The first product name is incorrect.");
        Assert.assertEquals(secondProductName.trim(), "GRAPHIC DESIGN MEN T SHIRT - BLUE", "The second product name is incorrect.");

        // Validate neon tshirt price
        Assert.assertEquals(Integer.parseInt(firstProductPrice), priceNeonExpected, "Neon product price mismatch");
        Assert.assertEquals(Integer.parseInt(secondProductPrice), priceGraphicExpected, "Neon product price mismatch");

        // Click on the proceed to checkout button
        WebElement checkout = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//a[@class='btn btn-default check_out']"))));
        checkout.click();
    }
}
