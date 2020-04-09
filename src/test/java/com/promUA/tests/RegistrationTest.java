package com.promUA.tests;

import com.codeborne.selenide.Selenide;
import com.promUA.pages.HomePage;
import com.promUA.util.AppProperties;
import com.promUA.util.MailHelper;
import com.promUA.util.TestHelper;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegistrationTest extends BaseTests {
    private String testMail, testPassword;

    @BeforeMethod
    public void setUp() {
        testPassword = AppProperties.getDefaultPassword();
        testMail = TestHelper.generateTestMail();
    }

    @Test(description = "Company registration test")
    @Description("Test for ability to register company and receive company profile page ")
    public void testCompanyRegistration() {
        Selenide.open("/");
        new HomePage().registerAsACompany().registerCompany(testMail, testPassword);
        String url = this.getCurrentUrl();
        Allure.step("Assert that url is  \"/cabinet/edit-checklist\" after registration ");
        Assert.assertTrue(url.contains("cms/edit-checklist"), "Redirected url in " + url);
    }


    @Test(description = "Verification of received mail ")
    @Description("Verify that received email after registration has subject \"Подтвердите email для дальнейшей работы с Prom.ua\"")
    public void testMailVerificationAfterRegistration() {
        String expectedSubject = "Подтвердите email для дальнейшей работы с Prom.ua";
        MailHelper ms = new MailHelper();
        String subject = ms.getMessageSubjectForReceiver("maksym.statkevych+tgeew@gmail.com");
        Assert.assertEquals(subject, expectedSubject, "Subject of email ");
    }
}
