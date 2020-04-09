package com.promUA.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;


public class BaseTests {

    @BeforeSuite
    @Parameters("baseUrl")
    public void setupSuite(@Optional("https://prom.ua") String baseUrl) {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true).savePageSource(false));
        Configuration.baseUrl = baseUrl;
        Configuration.timeout = 10000;
        Configuration.browser = "chrome";
        Configuration.savePageSource = false;
    }

    @AfterSuite
    public void tearDownSuite() {
        SelenideLogger.removeListener("AllureSelenide");
    }

    protected String getCurrentUrl() {
        return WebDriverRunner.url();
    }
}
