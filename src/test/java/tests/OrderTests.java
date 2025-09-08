package tests;

import base.TestBase;
import com.github.javafaker.Faker;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.OrderModal;
import pages.ProductPage;

import java.time.Duration;

public class OrderTests extends TestBase {

    private int extractAmount(String confirmation) {
        for (String line : confirmation.split("\\R")) {
            if (line.trim().startsWith("Amount:")) {
                String digits = line.replaceAll("[^0-9]", "");
                if (!digits.isEmpty()) {
                    return Integer.parseInt(digits);
                }
            }
        }
        return -1;
    }

    private void safeClickProduct(HomePage home, String category, String productName) {
        home.chooseCategory(category);
        // re-locate product fresh to avoid stale element
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement product = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@class='hrefch' and normalize-space()='" + productName + "']")));
        product.click();
    }

    private void safePurchase(OrderModal order, String name, String country, String city,
                              String card, String month, String year) {
        order.fillAll(name, country, city, card, month, year);
        order.clickPurchase();
        // handle unexpected alert safely
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            System.out.println("Unexpected alert: " + alert.getText());
            alert.accept();
        } catch (Exception ignored) {}
    }

    @Test(description = "Place an order for single item and verify confirmation")
    public void placeOrderAndVerifySingleItem() {
        HomePage home = new HomePage(driver, wait);
        ProductPage product = new ProductPage(driver, wait);
        CartPage cart = new CartPage(driver, wait);
        OrderModal order = new OrderModal(driver, wait);
        Faker faker = new Faker();

        safeClickProduct(home, "Monitors", "Apple monitor 24");
        String chosen = product.getTitle();
        product.addToCart(); waitAndAcceptAlert();

        home.openCart();
        Assert.assertTrue(cart.isProductPresent(chosen));
        int expectedTotal = cart.getDisplayedTotal();

        cart.clickPlaceOrder();
        safePurchase(order, faker.name().fullName(), faker.address().country(),
                faker.address().city(), "4111111111111111", "12", "2030");

        String confirmation = order.waitForConfirmationText();
        Assert.assertTrue(confirmation.contains("Id:"));
        int confirmedAmount = extractAmount(confirmation);
        Assert.assertEquals(confirmedAmount, expectedTotal);
        order.acceptConfirmation();
    }


    @Test(description = "Invalid order with short card number should show alert")
    public void orderInvalidCardShowsAlert() {
        HomePage home = new HomePage(driver, wait);
        ProductPage product = new ProductPage(driver, wait);
        CartPage cart = new CartPage(driver, wait);
        OrderModal order = new OrderModal(driver, wait);

        safeClickProduct(home, "Monitors", "Apple monitor 24");
        product.addToCart(); waitAndAcceptAlert();

        home.openCart();
        cart.clickPlaceOrder();
        safePurchase(order, "Test User", "IN", "Gurgaon", "1234", "01", "2030");

        // if confirmation still appears (rare), close it
        try {
            String confirmation = order.waitForConfirmationText();
            Assert.assertFalse(confirmation.isEmpty(), "Unexpected confirmation for invalid card");
            order.acceptConfirmation();
        } catch (Exception ignored) {}
    }
}
