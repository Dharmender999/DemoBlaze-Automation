package tests;

import base.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductPage;

public class BrowseTests extends TestBase {

    @Test(description = "Browse Laptops and verify product details")
    public void browseLaptops() {
        HomePage home = new HomePage(driver, wait);
        ProductPage product = new ProductPage(driver, wait);

        home.chooseCategory("Laptops");
        home.openProductByName("MacBook air");
        Assert.assertTrue(product.getTitle().toLowerCase().contains("macbook"));
        Assert.assertTrue(product.getPriceText().matches(".*\\d+.*"));
        Assert.assertFalse(product.getDescription().isBlank());
    }

    @Test(description = "Browse Phones and verify product details")
    public void browsePhones() {
        HomePage home = new HomePage(driver, wait);
        ProductPage product = new ProductPage(driver, wait);

        home.chooseCategory("Phones");
        home.openProductByName("Samsung galaxy s6");
        Assert.assertTrue(product.getTitle().toLowerCase().contains("samsung"));
        Assert.assertTrue(product.getPriceText().matches(".*\\d+.*"));
        Assert.assertFalse(product.getDescription().isBlank());
    }

    @Test(description = "Browse Monitors and verify product details")
    public void browseMonitors() {
        HomePage home = new HomePage(driver, wait);
        ProductPage product = new ProductPage(driver, wait);

        home.chooseCategory("Monitors");
        home.openProductByName("Apple monitor 24");
        Assert.assertTrue(product.getTitle().toLowerCase().contains("apple"));
        Assert.assertTrue(product.getPriceText().matches(".*\\d+.*"));
        Assert.assertFalse(product.getDescription().isBlank());
    }
}
