package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class OrderModal {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By name = By.id("name");
    private final By country = By.id("country");
    private final By city = By.id("city");
    private final By card = By.id("card");
    private final By month = By.id("month");
    private final By year = By.id("year");
    private final By purchaseBtn = By.xpath("//button[text()='Purchase']");
    private final By closeBtn = By.xpath("//div[@id='orderModal']//button[text()='Close']");

    private final By confirmation = By.cssSelector("div.sweet-alert.showSweetAlert.visible");
    private final By confText = By.cssSelector("div.sweet-alert.showSweetAlert.visible p");
    private final By confOK = By.xpath("//button[text()='OK']");

    public OrderModal(WebDriver driver, WebDriverWait wait) {
        this.driver = driver; this.wait = wait;
    }

    public void fillAll(String n, String ctry, String cty, String cardNo, String mm, String yy) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(name)).clear();
        driver.findElement(name).sendKeys(n);
        driver.findElement(country).clear(); driver.findElement(country).sendKeys(ctry);
        driver.findElement(city).clear(); driver.findElement(city).sendKeys(cty);
        driver.findElement(card).clear(); driver.findElement(card).sendKeys(cardNo);
        driver.findElement(month).clear(); driver.findElement(month).sendKeys(mm);
        driver.findElement(year).clear(); driver.findElement(year).sendKeys(yy);
    }

    public void clickPurchase() {
        driver.findElement(purchaseBtn).click();
    }

    public String waitForConfirmationText() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmation));
        return driver.findElement(confText).getText();
    }

    public void acceptConfirmation() {
        driver.findElement(confOK).click();
    }

    public void closeModal() {
        driver.findElement(closeBtn).click();
    }
}
