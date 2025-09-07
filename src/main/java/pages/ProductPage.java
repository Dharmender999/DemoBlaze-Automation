package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class ProductPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By title = By.cssSelector("div#tbodyid h2.name");
    private final By price = By.cssSelector("div#tbodyid h3.price-container");
    private final By description = By.cssSelector("div#more-information p");
    private final By addToCartBtn = By.linkText("Add to cart");

    public ProductPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver; this.wait = wait;
    }

    public String getTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(title)).getText().trim();
    }

    public String getPriceText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(price)).getText().trim();
    }

    public String getDescription() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(description)).getText().trim();
    }

    public void addToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();
    }
}
