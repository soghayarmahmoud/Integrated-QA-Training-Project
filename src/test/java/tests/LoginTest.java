package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    // TC-01
    @Test
    public void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("inventory.html"), "Login failed: Inventory page not reached!");
    }

    // TC-02
    @Test
    public void testLoginWithEmptyFields() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", "");
        String actualError = loginPage.getErrorMessage();
        Assert.assertTrue(actualError.contains("Username is required"), "Error message not displayed correctly!");
    }

    // TC-03: handles leading/trailing spaces
    @Test
    public void testLoginWithSpaces() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(" standard_user ", "secret_sauce");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("inventory.html"), "Bug Found: System did not trim spaces and login failed!");
    }

    // TC-04
    @Test
    public void testPasswordVisibilityToggle() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        boolean isEyeIconPresent = !driver.findElements(By.className("password-toggle-icon")).isEmpty();
        Assert.assertTrue(isEyeIconPresent, "Bug Found: Password visibility toggle (Eye icon) is missing!");
    }

    // TC-05: Username case sensitivity check
    @Test
    public void testUsernameCaseSensitivity() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Standard_User", "secret_sauce");

        // Asserting that the system should handle casing gracefully and log in
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("inventory.html"), "Bug: Username field is strictly case-sensitive!");
    }

    // TC-06: enter key form submission
    @Test
    public void testEnterKeySubmitsForm() {
        LoginPage loginPage = new LoginPage(driver);

        // Using a new method that pressing the "Enter" key
        loginPage.loginWithEnterKey("standard_user", "secret_sauce");

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("inventory.html"), "Bug: Form submission via Enter key failed!");
    }


    // TC-07: "locked out user" error message validation
    @Test
    public void testLockedOutUserLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("locked_out_user", "secret_sauce");

        // Verify that the specific locked out error message is displayed
        String actualError = loginPage.getErrorMessage();
        Assert.assertTrue(actualError.contains("Sorry, this user has been locked out."), "Locked out error message is incorrect!");
    }

    // TC-08
    @Test
    public void testErrorMessageDisappearsOnTyping() {
        LoginPage loginPage = new LoginPage(driver);

        // Trigger the locked out error
        loginPage.login("locked_out_user", "secret_sauce");

        // Start typing a new username to see if the error clears
        driver.findElement(By.id("user-name")).sendKeys("a");

        // Check the current text of the error message
        String currentError = loginPage.getErrorMessage();

        // Assert that the error message should be empty (cleared)
        Assert.assertTrue(currentError.isEmpty(), "Bug Found: Error message stays visible when typing!");
    }

    // TC-09: lockout duration
    @Test
    public void testLockoutDurationInfo() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("locked_out_user", "secret_sauce");

        String actualError = loginPage.getErrorMessage();

        // Asserting directly that the message contains a time duration keyword like "minutes"
        Assert.assertTrue(actualError.contains("minutes"), "Bug Found: Error message lacks lockout duration!");
    }

    // TC-10: Password field cleared after lockout error
    @Test
    public void testPasswordFieldClearedAfterLockout() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("locked_out_user", "secret_sauce");

        // Get the current text inside the password field using the "value" attribute
        String passwordFieldValue = driver.findElement(By.id("password")).getAttribute("value");

        // Assert that the password field should be empty for security
        Assert.assertTrue(passwordFieldValue.isEmpty(), "Bug Found: Password field is not cleared after lockout error!");
    }

    // TC-11: Login button state during error
    @Test
    public void testLoginButtonDisabledOnError() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("locked_out_user", "secret_sauce");

        // Check if the login button is still active/clickable
        boolean isButtonEnabled = driver.findElement(By.id("login-button")).isEnabled();

        // Assert that the button should NOT be enabled
        Assert.assertFalse(isButtonEnabled, "Bug Found: Login button remains clickable during error display!");
    }


}

