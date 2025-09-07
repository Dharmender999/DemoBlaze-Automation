package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.util.List;

public class CartPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By tableRows = By.cssSelector("#tbodyid > tr");
    private final By totalPrice = By.id("totalp");
    private final By placeOrder = By.xpath("//button[text()='Place Order']");

    public CartPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver; this.wait = wait;
    }

    public int getCartRowsCount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tableRows));
        return driver.findElements(tableRows).size();
    }

    public boolean isProductPresent(String productName) {
        List<WebElement> rows = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows));
        return rows.stream().anyMatch(r -> r.getText().contains(productName));
    }

    public int getDisplayedTotal() {
        String txt = wait.until(ExpectedConditions.visibilityOfElementLocated(totalPrice)).getText().trim();
        try { return Integer.parseInt(txt); } catch (NumberFormatException e) { return -1; }
    }

    public void clickPlaceOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(placeOrder)).click();
    }

    /** Removes the first row (top-most) from the cart. */
    public void removeFirstProduct() {
        int before = driver.findElements(tableRows).size();
        By deleteLink = By.xpath("//table[@id='cartTable']//tr[1]//a[text()='Delete']|//tr[1]//a[text()='Delete']");
        wait.until(ExpectedConditions.elementToBeClickable(deleteLink)).click();
        wait.until(d -> driver.findElements(tableRows).size() < before);
    }

    /** Removes product by name, if present. */
    public void removeProductByName(String productName) {
        List<WebElement> rows = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows));
        for (int i = 1; i <= rows.size(); i++) {
            WebElement row = driver.findElement(By.xpath("//table[@id='cartTable']//tr[" + i + "]|//tbody[@id='tbodyid']/tr[" + i + "]"));
            if (row.getText().contains(productName)) {
                WebElement del = row.findElement(By.xpath(".//a[text()='Delete']"));
                del.click();
                int before = rows.size();
                wait.until(d -> driver.findElements(tableRows).size() < before);
                return;
            }
        }
    }
}
