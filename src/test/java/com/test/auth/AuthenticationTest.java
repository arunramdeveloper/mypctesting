package com.test.auth;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.test.service.WebDriverService;
import com.test.service.WebDriverServiceImpl;
import com.test.utils.ConfigReader;
import java.time.Duration;

public class AuthenticationTest {
    private WebDriverService webDriverService;
    private WebDriver driver;
    private WebDriverWait wait;

    private String email = ConfigReader.getProperty("email"); // Securely fetch email
    private String password = ConfigReader.getProperty("password"); // Securely fetch password

    @BeforeClass
    public void setup() {
        webDriverService = new WebDriverServiceImpl();
        driver = webDriverService.getDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Load login page and wait for full page load
        driver.get("https://rezzqa2.its-cs.com/Auth/Login");
        waitForPageLoad();
    }

    @Test(priority = 1)
    public void verifyWelcomeText() {
        // Wait for the welcome text and verify its presence
        WebElement welcomeText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
            "/html[1]/body[1]/app-root[1]/app-auth[1]/div[1]/div[1]/div[1]/div[1]/app-login[1]/h1[1]"
        )));
        String actualText = welcomeText.getText();
        System.out.println("Welcome Text: " + actualText);
        Assert.assertEquals(actualText, "Welcome", "❌ Welcome text does not match!");
        System.out.println("✅ Welcome text is correct.");
    }

    @Test(priority = 2)
    public void verifyVersionNumber() {
        // Wait for the version text and verify it
        WebElement versionText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
            "//div[contains(@class, 'version')]"
        )));
        String actualVersion = versionText.getText();
        System.out.println("Version Text: " + actualVersion);
        Assert.assertEquals(actualVersion, "1.2.13", "❌ Version number does not match!");
        System.out.println("✅ Version number is correct.");
    }


    @Test(priority = 3)
    public void loginAndLogoutTest() {
        // Wait for email field and enter email
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        emailField.sendKeys(email);

        // Wait for password field and enter password
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        passwordField.sendKeys(password);

        // Wait for Login button and click
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='btn btn-default btn-login btn-main']")));
        loginButton.click();

        // Wait for Dashboard page to load
        wait.until(ExpectedConditions.urlToBe("https://rezzqa2.its-cs.com/Dashboard"));
        System.out.println("✅ Successfully logged in.");

        // Click Settings Icon
        WebElement settingsIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
            "/html[1]/body[1]/app-root[1]/app-main[1]/div[1]/app-top-bar[1]/nav[1]/div[1]/div[2]/app-login-info[1]/div[1]/div[1]/a[1]/div[2]"
        )));
        settingsIcon.click();

        // Click Sign Out
        WebElement signOutButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
            "/html[1]/body[1]/app-root[1]/app-main[1]/div[1]/app-top-bar[1]/nav[1]/div[1]/div[2]/app-login-info[1]/div[1]/div[1]/div[1]/a[3]"
        )));
        signOutButton.click();

        // Verify user is logged out
        wait.until(ExpectedConditions.urlToBe("https://rezzqa2.its-cs.com/Auth/Login"));
        System.out.println("✅ Successfully logged out.");
    }

    @AfterClass
    public void tearDown() {
        webDriverService.quitDriver();
    }

    // ✅ Helper Method: Wait for Page Load Completion
    private void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
            webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete")
        );
    }
}
