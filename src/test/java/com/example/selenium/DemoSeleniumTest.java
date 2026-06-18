package com.example.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DemoSeleniumTest {

    @Test
    void shouldLoadLocalDemoPageAndSubmitForm() throws URISyntaxException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);

        try {
            URL pageUrl = getClass().getClassLoader().getResource("demo-page.html");
            if (pageUrl == null) {
                throw new IllegalStateException("demo-page.html was not found in test resources");
            }

            Path pagePath = Path.of(pageUrl.toURI());
            driver.get(pagePath.toUri().toString());

            assertEquals("Selenium Demo", driver.getTitle());

            WebElement nameInput = driver.findElement(By.id("name"));
            WebElement submitButton = driver.findElement(By.id("submit"));

            nameInput.sendKeys("Rahul");
            submitButton.click();

            assertEquals("Hello, Rahul", driver.findElement(By.id("message")).getText());
        } finally {
            driver.quit();
        }
    }
}