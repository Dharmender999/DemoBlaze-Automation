package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class AuthModals {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By signupUsername = By.id("sign-username");
    private final By signupPassword = By.id("sign-password");
    private final By signupBtn = By.xpath("//button[text()='Sign up']");

    private final By loginUsername = By.id("loginusername");
    private final By loginPassword = By.id("loginpassword");
    private final By loginBtn = By.xpath("//button[text()='Log in']");

    private final By welcomeUser = By.id("nameofuser");

    public AuthModals(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void signup(String username, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(signupUsername)).sendKeys(username);
        driver.findElement(signupPassword).sendKeys(password);
        driver.findElement(signupBtn).click();
    }

    public void login(String username, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginUsername)).sendKeys(username);
        driver.findElement(loginPassword).sendKeys(password);
        driver.findElement(loginBtn).click();
    }

    public String waitForWelcomeUser() {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(welcomeUser))
                .getText();
    }
}
