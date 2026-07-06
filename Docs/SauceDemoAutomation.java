import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SauceDemoAutomation {
    public static void main(String[] args) {
        // 1. Initialize the Chrome Driver (Selenium 4 automatically manages driver binaries)
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Maximize browser window
            driver.manage().window().maximize();

            // 2. Navigate to the target application URL
            System.out.println("Navigating to SauceDemo...");
            driver.get("https://www.saucedemo.com/");

            // 3. Automation Step: Login Validation
            System.out.println("Executing Login Step...");
            WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
            WebElement passwordField = driver.findElement(By.id("password"));
            WebElement loginButton = driver.findElement(By.id("login-button"));

            usernameField.sendKeys("standard_user");
            passwordField.sendKeys("secret_sauce");
            loginButton.click();

            // Verify login success by checking if the product inventory container is visible
            WebElement inventoryContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_container")));
            if (inventoryContainer.isDisplayed()) {
                System.out.println("Login Test Status: PASSED");
            }

            // 4. Automation Step: Add Product to Cart
            System.out.println("Adding product to cart...");
            WebElement addToCartButton = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
            addToCartButton.click();

            // Navigate to the shopping cart container view
            WebElement cartLink = driver.findElement(By.className("shopping_cart_link"));
            cartLink.click();

            // Proceed to the Checkout page
            WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout")));
            checkoutButton.click();

            // 5. Automation Step: Fill Checkout Information
            System.out.println("Filling out checkout validation data...");
            WebElement firstName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));
            WebElement lastName = driver.findElement(By.id("last-name"));
            WebElement postalCode = driver.findElement(By.id("postal-code"));
            WebElement continueButton = driver.findElement(By.id("continue"));

            firstName.sendKeys("Farah");
            lastName.sendKeys("Ahmed");
            postalCode.sendKeys("12345");
            continueButton.click();

            // 6. Automation Step: Order Overview & Completion
            System.out.println("Finalizing order completion summary...");
            WebElement finishButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("finish")));
            finishButton.click();

            // 7. Verification: Validate Complete Success Header String
            WebElement completeHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("complete-header")));
            String expectedMessage = "Thank you for your order!";
            String actualMessage = completeHeader.getText();

            System.out.println("Actual Server Header Received: " + actualMessage);
            if (actualMessage.equalsIgnoreCase(expectedMessage)) {
                System.out.println("E-Commerce Complete Workflow Test Status: PASSED");
            } else {
                System.out.println("E-Commerce Complete Workflow Test Status: FAILED");
            }

        } catch (Exception e) {
            System.err.println("Test Execution Exception Encountered: " + e.getMessage());
        } finally {
            // 8. Gracefully close the browser windows and kill the background service process
            System.out.println("Closing automated browser instance...");
            driver.quit();
        }
    }
}
