package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By signupNav = By.id("signin2");
    private final By loginNav = By.id("login2");
    private final By cartNav = By.id("cartur");
    private final By homeNav = By.cssSelector("a.navbar-brand");

    private final By phonesCategory = By.xpath("//a[text()='Phones']");
    private final By laptopsCategory = By.xpath("//a[text()='Laptops']");
    private final By monitorsCategory = By.xpath("//a[text()='Monitors']");

    private final By productCards = By.cssSelector("div#tbodyid .card.h-100");
    private final String productLinkByName = "//a[@class='hrefch' and normalize-space()='%s']";

    public HomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // Updated with waits
    public void openSignup() {
        wait.until(ExpectedConditions.elementToBeClickable(signupNav)).click();
    }

    public void openLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginNav)).click();
    }

    public void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartNav)).click();
    }

    public void goHome() {
        wait.until(ExpectedConditions.elementToBeClickable(homeNav)).click();
    }

    public void chooseCategory(String category) {
    	By locator;

    	switch (category.toLowerCase()) {
    	    case "phones":
    	        locator = phonesCategory;
    	        break;
    	    case "laptops":
    	        locator = laptopsCategory;
    	        break;
    	    case "monitors":
    	        locator = monitorsCategory;
    	        break;
    	    default:
    	        throw new IllegalArgumentException("Unknown category: " + category);
    	}

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(productCards, 0));
    }

    public void openProductByName(String name) {
        By link = By.xpath(String.format(productLinkByName, name));
        wait.until(ExpectedConditions.elementToBeClickable(link)).click();
    }
}
