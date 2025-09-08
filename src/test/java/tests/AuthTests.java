package tests;

import base.TestBase;
import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AuthModals;
import pages.HomePage;

public class AuthTests extends TestBase {

    @Test(description = "Signup with a new user and verify alert")
    public void signupNewUser() {
        HomePage home = new HomePage(driver, wait);
        AuthModals auth = new AuthModals(driver, wait);
        Faker faker = new Faker();

        String username = "user_" + System.currentTimeMillis();
        String password = faker.internet().password(8, 12);

        home.openSignup();
        auth.signup(username, password);
        waitAndAcceptAlert();
    }

//    @Test(description = "Signup with already registered user should show exists alert")
//    public void signupExistingUser() {
//        HomePage home = new HomePage(driver, wait);
//        AuthModals auth = new AuthModals(driver, wait);
//
//        String username = "fixed_user_" + (System.currentTimeMillis() / 10000);
//        String password = "Pass@123";
//
//        // first time
//        home.openSignup(); auth.signup(username, password); waitAndAcceptAlert();
//        // second time same user
//        home.openSignup(); auth.signup(username, password); waitAndAcceptAlert();
//    }

    @Test(description = "Login with valid credentials after signup")
    public void loginAfterSignup() {
        HomePage home = new HomePage(driver, wait);
        AuthModals auth = new AuthModals(driver, wait);

        String username = "user_" + System.currentTimeMillis();
        String password = "Pass@123";

        home.openSignup(); auth.signup(username, password); waitAndAcceptAlert();
        home.openLogin();  auth.login(username, password);
        String welcome = auth.waitForWelcomeUser();
        Assert.assertTrue(welcome.contains(username), "Welcome label should contain username");
    }

    @DataProvider
    public Object[][] invalidCreds() {
        return new Object[][]{
                {"", ""},
                {"someUser", ""},
                {"", "somePass"},
                {"wrongUser", "wrongPass"}
        };
    }

    @Test(dataProvider = "invalidCreds", description = "Login invalid combinations -> expect alert")
    public void loginInvalid(String u, String p) {
        HomePage home = new HomePage(driver, wait);
        AuthModals auth = new AuthModals(driver, wait);

        home.openLogin();
        auth.login(u, p);
        waitAndAcceptAlert();
    }

    @Test(description = "Login wrong password only -> alert")
    public void loginWrongPasswordOnly() {
        HomePage home = new HomePage(driver, wait);
        AuthModals auth = new AuthModals(driver, wait);

        String username = "user_" + System.currentTimeMillis();
        String password = "Pass@123";

        home.openSignup(); auth.signup(username, password); waitAndAcceptAlert();

        home.openLogin();  auth.login(username, "Wrong@123");
        waitAndAcceptAlert();
    }
}
