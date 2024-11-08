package com.truecodes;

import com.truecodes.pages.ShoppingCartFlow;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Objects;

public class ShoppingCartFlowTest extends BaseSetup{
    ShoppingCartFlow shoppingCartFlow = ShoppingCartFlow.getShoppingCartInstance(driver);
    List<WebElement> products;
    @Test(priority = 1)
    public void navigateMenTShirtTest() {
        WebElement tshirtsLink = shoppingCartFlow.navigateToTshirt();
        String linkText = tshirtsLink.getText();
        String href = tshirtsLink.getAttribute("href");
        // Verify the link's text
        Assert.assertEquals(linkText.trim(), "TSHIRTS", "Link text does not match the expected value!");
        // Assert that the href attribute is correct
        Assert.assertEquals(href, "https://automationexercise.com/category_products/3#", "The link URL is incorrect!");

        // verify that the element is an <a> tag (anchor tag)
        Assert.assertEquals(tshirtsLink.getTagName(), "a", "The element is not a link!");

    }
    @Test(priority = 2)
    public void productDescTest(){
        products = shoppingCartFlow.productDescs();
        // Define the T-shirt keywords to check in the product name
        String[] tshirtKeywords = {"T-Shirt", "Tshirt", "T-Shirts", "Tshirt", "T SHIRT"};

        for (WebElement product : products) {
            WebElement productNameElement = shoppingCartFlow.productName(product);
            String productName = productNameElement.getText();
            System.out.println(productName);
            boolean containsKeyword = false;
            for (String keyword : tshirtKeywords) {
                if (productName.toLowerCase().contains(keyword.toLowerCase())) {
                    containsKeyword = true;
                    break;
                }
            }
            WebElement productPriceElement = shoppingCartFlow.productPrice(product);
            WebElement productImage = shoppingCartFlow.productImg(product);
            WebElement viewProductLink = shoppingCartFlow.productLink(product);
            WebElement addToCartButton = shoppingCartFlow.addToCartBtn(product);

            String productPrice = productPriceElement.getText();
            System.out.println(productPrice);
            String viewProductHref = viewProductLink.getAttribute("href");// Validate the 'View Product' link is correct

            // validate tshirts price, product info, add to cart button
            Assert.assertTrue(containsKeyword, "Product name does not contain any of the expected keywords " + productName);// Assert that the product name contains at least one of the tshirt keyword
            Assert.assertTrue(productPrice.startsWith("Rs."), "Price format is incorrect for product: " + productName);// Validate the price
            Assert.assertTrue(productImage.isDisplayed(), "Image is not displayed for product: " + productName);// Validate the image exists
            Assert.assertTrue(viewProductHref.contains("/product_details/"), "View Product link is incorrect for product: " + productName);
            Assert.assertTrue(addToCartButton.isDisplayed(), "Add to Cart button is not displayed for product: " + productName);// Validate the 'Add to Cart' button exists
        }
    }
    Integer priceNeonExpected;
    WebElement addToCartButtonHover;
    @Test(priority = 3)
    public void neonGreenTshirtVisibiltiyTest(){
        WebElement neonProduct=null;
        String productName="";
        for (WebElement product : products) {
            neonProduct = product;
            WebElement productNameElement = shoppingCartFlow.productName(neonProduct);
            productName = productNameElement.getText();
            if (productName.trim().contains("Pure Cotton Neon Green Tshirt")) break;
        }
        WebElement productOverlay = shoppingCartFlow.neonTshirt(neonProduct);
        WebElement productPriceElement = shoppingCartFlow.productPrice(neonProduct);
        WebElement viewProductLink = shoppingCartFlow.productLink(neonProduct);
        WebElement addToCartButton = shoppingCartFlow.addToCartBtn(neonProduct);
        WebElement priceElement = shoppingCartFlow.neonPrice(productOverlay);// Validate that the price is visible
        addToCartButtonHover = shoppingCartFlow.neonAddToCartBtn(productOverlay);
        WebElement overlayTextElement = shoppingCartFlow.neonName(productOverlay);

        String productPrice = productPriceElement.getText();
        String viewProductHref = viewProductLink.getAttribute("href");
        String priceText = priceElement.getText();
        String saveNeon = priceText.replace("Rs. ","");
        priceNeonExpected = Integer.parseInt(saveNeon);
        String overlayText = overlayTextElement.getText();

        Assert.assertTrue(productPrice.startsWith("Rs."), "Price format is incorrect for product: " + productName);// verify price
        Assert.assertTrue(viewProductHref.contains("/product_details/"), "View Product link is incorrect for product: " + productName);// Validate the 'View Product' link is correct
        Assert.assertTrue(addToCartButton.isDisplayed(), "Add to Cart button is not displayed for product: " + productName);// Validate the 'Add to Cart' button exists

        Assert.assertTrue(priceText.contains("Rs."), "Price is not displayed or is in an incorrect format: " + priceText);// Validate that overlay is visible and contains the expected elements
        Assert.assertTrue(addToCartButtonHover.isDisplayed(), "Add to Cart button is not displayed after hover.");// Validate that the "Add to Cart" button is visible
        Assert.assertTrue(overlayText.contains("Pure Cotton Neon Green Tshirt"), "The product name is not correct in the overlay: " + overlayText);// Validate that the text "Pure Cotton Neon Green Tshirt" appears in the overlay

    }

    @Test(priority = 4)
    public void verifyModal(){
        final boolean[] modalVisible = new boolean[1];
        List<WebElement> modalEles = shoppingCartFlow.modalVisibility(addToCartButtonHover, modalVisible);
        WebElement modal = modalEles.get(0);
        WebElement modalContent =modalEles.get(1);
        WebElement modalTitle = modalEles.get(2);
        WebElement modalBodyText = modalEles.get(3);
        WebElement continueShoppingButton = modalEles.get(4);
        String modalTitleText = modalTitle.getText();

        Assert.assertTrue(modalVisible[0], "Modal is not visible");
        Assert.assertEquals(modalTitleText, "Added!", "Modal title is not as expected.");
        Assert.assertEquals(modalBodyText.getText().trim(),"Your product has been added to cart.", "Modal body text is not displayed correctly.");
        Assert.assertTrue(continueShoppingButton.isDisplayed(), "'Continue Shopping' button is not visible.");
        Assert.assertTrue(continueShoppingButton.isEnabled(), "'Continue Shopping' button is not clickable.");

        boolean visibility = Objects.requireNonNull(modal.getAttribute("class")).contains("fade");
        Assert.assertTrue(visibility, "Modal is still visible");

        // Add another t-shirt to the cart
        WebElement graphicDesignProduct=null;
        for (WebElement product : products) {
            graphicDesignProduct = product;
            WebElement productNameElement = graphicDesignProduct.findElement(By.cssSelector("p"));
            String productName = productNameElement.getText();
            if (productName.trim().contains("GRAPHIC DESIGN MEN T SHIRT - BLUE")) break;
        }

        // Scroll to "GRAPHIC DESIGN MEN T SHIRT - BLUE"
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", graphicDesignProduct);
        wait.waitUntilVisibility(graphicDesignProduct);
        actions.moveToElement(graphicDesignProduct).perform();
        assert graphicDesignProduct != null;
        WebElement productOverlay = wait.waitUntilVisibilityOfWebElementByCssSelector(graphicDesignProduct,".product-overlay");

        // Validate that the price is visible
        WebElement priceElement = productOverlay.findElement(By.cssSelector(".overlay-content h2"));
        Integer priceGraphicExpected = Integer.parseInt(priceElement.getText().replace("Rs. ",""));

        WebElement addToCartButtonHover = wait.waitUntilVisibilityOfWebElementByCssSelector(productOverlay,".overlay-content .add-to-cart");
        addToCartButtonHover.click();

        // click on view cart button
        WebElement viewCart = wait.waitUntilElementToBeClickable("//*[@id=\"cartModal\"]/div/div/div[2]/p[2]/a");
        viewCart.click();

        // wait until cart get opened
        wait.waitUntilUrlToBe("https://automationexercise.com/view_cart");

        WebElement cartTableBody = driver.findElement(By.cssSelector("#cart_info_table tbody"));

        // Get all the rows (tr) in the cart body
        List<WebElement> rows = cartTableBody.findElements(By.tagName("tr"));

        // Verify the number of products in the cart (expecting 2)
        Assert.assertEquals(rows.size(), 2, "The cart does not contain exactly 2 products.");

        // Now verify the names of the products
        String firstProductName = rows.get(0).findElement(By.cssSelector(".cart_description h4 a")).getText();
        String secondProductName = rows.get(1).findElement(By.cssSelector(".cart_description h4 a")).getText();

        String firstProductPrice = rows.get(0).findElement(By.cssSelector(".cart_price p")).getText().trim();
        Integer neonActualPrice = Integer.parseInt(firstProductPrice.replace("Rs. ",""));
        String secondProductPrice = rows.get(1).findElement(By.cssSelector(".cart_price p")).getText().trim();
        Integer graphicActualPrice = Integer.parseInt(secondProductPrice.replace("Rs. ",""));



        // Validate the product names
        Assert.assertEquals(firstProductName.trim(), "Pure Cotton Neon Green Tshirt", "The first product name is incorrect.");
        Assert.assertEquals(secondProductName.trim(), "GRAPHIC DESIGN MEN T SHIRT - BLUE", "The second product name is incorrect.");

        // Validate neon tshirt price
        Assert.assertEquals(neonActualPrice, priceNeonExpected, "Neon product price mismatch");
        Assert.assertEquals(graphicActualPrice, priceGraphicExpected, "Neon product price mismatch");

        // Click on the proceed to checkout button
        WebElement checkout = wait.waitUntilElementToBeClickable(driver,"//a[@class='btn btn-default check_out']");
        checkout.click();
    }
}
