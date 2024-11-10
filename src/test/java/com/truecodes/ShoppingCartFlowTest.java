package com.truecodes;

import com.truecodes.pages.ShoppingCartFlow;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Objects;

public class ShoppingCartFlowTest extends BaseSetup{
    ShoppingCartFlow shoppingCartFlow = ShoppingCartFlow.getShoppingCartInstance(driver);
    List<WebElement> products;
    Integer priceNeonExpected;
    WebElement neonProduct = null;
    Integer priceGraphicExpected;
    @Test(priority = 1)
    public void navigateMenTShirtTest() {
        WebElement tshirtsLink = shoppingCartFlow.navigateToTshirt();
        String linkText = tshirtsLink.getText();
        String href = tshirtsLink.getAttribute("href");
        Assert.assertEquals(linkText.trim(), "TSHIRTS", "Link text does not match the expected value!");// Verify the link's text
        Assert.assertEquals(href, "https://automationexercise.com/category_products/3#", "The link URL is incorrect!");// Assert that the href attribute is correct
        Assert.assertEquals(tshirtsLink.getTagName(), "a", "The element is not a link!");// verify that the element is an <a> tag
    }

    @Test(priority = 2)
    public void verifyTshirtsDesc(){
        products = shoppingCartFlow.getListTshirt();
        String[] tshirtKeywords = {"T-Shirt", "Tshirt", "T-Shirts", "Tshirt", "T SHIRT"};// Define the T-shirt keywords to check in the product nam
        for (WebElement product : products) {
            List<WebElement> eles = shoppingCartFlow.getTshirtElements(product);
            WebElement productNameElement = eles.get(0);
            String productName = productNameElement.getText();
            System.out.println(productName);
            boolean containsKeyword = false;
            for (String keyword : tshirtKeywords) {
                if (productName.toLowerCase().contains(keyword.toLowerCase())) {
                    containsKeyword = true;
                    break;
                }
            }
            Assert.assertTrue(containsKeyword, "Product name does not contain any of the expected keywords (T-Shirt, Tshirt, Cotton, etc.): " + productName);// Assert that the product name contains at least one of the tshirt keyword
            Assert.assertTrue(eles.get(1).getText().startsWith("Rs."), "Price format is incorrect for product: " + productName);// Validate the price
            Assert.assertTrue(eles.get(2).isDisplayed(), "Image is not displayed for product: " + productName);// Validate the image exists
            Assert.assertTrue(eles.get(3).getAttribute("href").contains("/product_details/"), "View Product link is incorrect for product: " + productName);// Validate the 'View Product' link is correct
            Assert.assertTrue(eles.get(4).isDisplayed(), "Add to Cart button is not displayed for product: " + productName);// Validate the 'Add to Cart' button exists
        }
    }

    @Test(priority =3)
    public void neonGreenTshirtVisibiltiyTest() {
        String productName = "";
        for (WebElement product : products) {
            neonProduct = product;
            WebElement productNameElement = neonProduct.findElement(By.cssSelector("p"));
            productName = productNameElement.getText();
            if (productName.trim().contains("Pure Cotton Neon Green Tshirt")) break;
        }
        shoppingCartFlow.baseWork(neonProduct);
        List<WebElement> eles = shoppingCartFlow.getTshirtElements(neonProduct);
        String productPrice = eles.get(1).getText();
        Assert.assertTrue(productPrice.startsWith("Rs."), "Price format is incorrect for product: " + productName);// verify price
        Assert.assertTrue(eles.get(3).getAttribute("href").contains("/product_details/"), "View Product link is incorrect for product: " + productName);// Validate the 'View Product' link is correct
        Assert.assertTrue(eles.get(4).isDisplayed(), "Add to Cart button is not displayed for product: " + productName);// Validate the 'Add to Cart' button exists
    }

    @Test(priority = 4)
    public void overlayVerifyWhenHover(){
        List<WebElement> eles = shoppingCartFlow.verifyHover(neonProduct);
        String priceText = eles.get(1).getText();
        String saveNeon = priceText.replace("Rs. ","");
        priceNeonExpected = Integer.parseInt(saveNeon);
        Assert.assertTrue(priceText.contains("Rs."), "Price is not displayed or is in an incorrect format: " + priceText);// Validate that the price is visible
        Assert.assertTrue(eles.get(2).isDisplayed(), "Add to Cart button is not displayed after hover.");// Validate that the "Add to Cart" button is visible
        Assert.assertTrue(eles.get(3).getText().contains("Pure Cotton Neon Green Tshirt"), "The product name is not correct in the overlay: " + eles.get(3).getText());// Validate that the text "Pure Cotton Neon Green Tshirt" appears in the overlay
        eles.get(2).click();//click on add to cart button
    }

    @Test(priority = 5)
    public void verifyModal() {
        List<WebElement> eles = shoppingCartFlow.modalVerify();
        WebElement continueShoppingButton = eles.get(3);// Verify "Continue Shopping" button is present and clickable
//        Assert.assertTrue(modalVisible[0], "Modal is not visible");
        Assert.assertEquals(eles.get(1).getText(), "Added!", "Modal title is not as expected.");
        Assert.assertEquals(eles.get(2).getText().trim(), "Your product has been added to cart.", "Modal body text is not displayed correctly.");
        Assert.assertTrue(continueShoppingButton.isDisplayed(), "'Continue Shopping' button is not visible.");
        Assert.assertTrue(continueShoppingButton.isEnabled(), "'Continue Shopping' button is not clickable.");
        continueShoppingButton.click();// click on continue shopping button
        boolean visibility = Objects.requireNonNull(eles.get(0).getAttribute("class")).contains("fade");
        Assert.assertTrue(visibility, "Modal is still visible");
    }

    @Test(priority = 6)
    public void addAnotherTshirt() {
        WebElement graphicDesignProduct = null;
        for (WebElement product : products) {
            graphicDesignProduct = product;
            WebElement productNameElement = graphicDesignProduct.findElement(By.cssSelector("p"));
            String productName = productNameElement.getText();
            if (productName.trim().contains("GRAPHIC DESIGN MEN T SHIRT - BLUE")) break;
        }
        shoppingCartFlow.baseWork(graphicDesignProduct);// Scroll to "GRAPHIC DESIGN MEN T SHIRT - BLUE"
        List<WebElement> eles = shoppingCartFlow.verifyGraphic(graphicDesignProduct);
        priceGraphicExpected = Integer.parseInt(eles.get(0).getText().replace("Rs. ", ""));
        WebElement addToCartButtonHover = eles.get(1);
        addToCartButtonHover.click();
    }

    @Test(priority = 7)
    public void verifyCart() {
        List<WebElement> rows = shoppingCartFlow.viewCart();
        // Now verify the names of the products
        String firstProductName = rows.get(0).findElement(By.cssSelector(".cart_description h4 a")).getText();
        String secondProductName = rows.get(1).findElement(By.cssSelector(".cart_description h4 a")).getText();

        String firstProductPrice = rows.get(0).findElement(By.cssSelector(".cart_price p")).getText().trim();
        Integer neonActualPrice = Integer.parseInt(firstProductPrice.replace("Rs. ", ""));
        String secondProductPrice = rows.get(1).findElement(By.cssSelector(".cart_price p")).getText().trim();
        Integer graphicActualPrice = Integer.parseInt(secondProductPrice.replace("Rs. ", ""));

        Assert.assertEquals(rows.size(), 2, "The cart does not contain exactly 2 products.");// Validate count of products
        // Validate the product names
        Assert.assertEquals(firstProductName.trim(), "Pure Cotton Neon Green Tshirt", "The first product name is incorrect.");
        Assert.assertEquals(secondProductName.trim(), "GRAPHIC DESIGN MEN T SHIRT - BLUE", "The second product name is incorrect.");

        // Validate prices
        Assert.assertEquals(neonActualPrice, priceNeonExpected, "Neon product price mismatch");
        Assert.assertEquals(graphicActualPrice, priceGraphicExpected, "Neon product price mismatch");
    }

    @Test(priority = 8)
    public void checkout(){
        // Click on the proceed to checkout button
        WebElement checkout = wait.waitUntilElementToBeClickable(driver,"//a[@class='btn btn-default check_out']");
        checkout.click();
    }
}
