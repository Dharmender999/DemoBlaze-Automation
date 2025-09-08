package tests;

import base.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.ProductPage;

public class CartTests extends TestBase {

    @Test(description = "Add single product to cart and verify presence")
    public void addSingleProduct() {
        HomePage home = new HomePage(driver, wait);
        ProductPage product = new ProductPage(driver, wait);
        CartPage cart = new CartPage(driver, wait);

        home.chooseCategory("Phones");

        // Handle stale element
        int attempts = 0;
        while (attempts < 2) {
            try {
                home.openProductByName("Samsung galaxy s6");
                break;
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                attempts++;
            }
        }

        String chosen = product.getTitle();
        product.addToCart();
        waitAndAcceptAlert();

        home.openCart();
        Assert.assertTrue(cart.isProductPresent(chosen), "Cart should contain " + chosen);
    }

//    @Test(description = "Add multiple products and verify rows and total")
//    public void addMultipleProducts() {
//        HomePage home = new HomePage(driver, wait);
//        ProductPage product = new ProductPage(driver, wait);
//        CartPage cart = new CartPage(driver, wait);
//
//        // Add first product
//        home.chooseCategory("Phones");
//        home.openProductByName("Nokia lumia 1520");
//        product.addToCart();
//        waitAndAcceptAlert();
//
//        // Add second product
//        home.goHome();
//        home.chooseCategory("Laptops");
//        home.openProductByName("Sony vaio i5");
//        product.addToCart();
//        waitAndAcceptAlert();
//
//        home.openCart();
//
//        int rows = cart.getCartRowsCount();
//        Assert.assertTrue(rows >= 2, "Cart should have at least 2 rows but has " + rows);
//
//        int total = cart.getDisplayedTotal();
//        Assert.assertTrue(total > 0, "Cart total should be positive but is " + total);
//    }

    @Test(description = "Cart persistence after navigation")
    public void cartPersistence() {
        HomePage home = new HomePage(driver, wait);
        ProductPage product = new ProductPage(driver, wait);
        CartPage cart = new CartPage(driver, wait);

        home.chooseCategory("Laptops");

        int attempts = 0;
        while (attempts < 2) {
            try {
                home.openProductByName("Sony vaio i5");
                break;
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                attempts++;
            }
        }

        String chosen = product.getTitle();
        product.addToCart();
        waitAndAcceptAlert();

        home.goHome();
        home.openCart();
        Assert.assertTrue(cart.isProductPresent(chosen), "Cart should still contain " + chosen);
    }
}
