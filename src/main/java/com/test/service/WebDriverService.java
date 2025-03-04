package com.test.service;

import org.openqa.selenium.WebDriver;

public interface WebDriverService {
    WebDriver getDriver();
    void quitDriver();
}
