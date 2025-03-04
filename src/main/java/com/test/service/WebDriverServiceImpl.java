package com.test.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverServiceImpl implements WebDriverService {
    private WebDriver driver;

    public WebDriverServiceImpl() {
        WebDriverManager.chromedriver().setup();
        this.driver = new ChromeDriver();
    }

    @Override
    public WebDriver getDriver() {
        return driver;
    }

    @Override
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
