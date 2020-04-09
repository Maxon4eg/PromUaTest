package com.promUA.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class RegistrationPage {
    private SelenideElement
            nameField = $("input[data-qaid='name']"),
            emailField = $("input[data-qaid='email']"),
            passwordField = $("input[data-qaid='password']"),
            submitBtn = $("button[data-qaid='submit']"),
            companyMail = $("*[data-qaid='register_form'] input[name='email']"),
            companyPassword = $("input[name='password']");

    @Step("Register as USER with {name},{mail},{password}")
    public void registerAsUser(String name, String mail, String password) {
        nameField.sendKeys(name);
        emailField.sendKeys(mail);
        passwordField.sendKeys(password);
        submitBtn.click();
    }

    @Step("Register as Company with {email} ,{password}")
    public CompanyProfile registerCompany(String email, String password) {
        companyMail.sendKeys(email);
        companyPassword.sendKeys(password);
        submitBtn.click();
        $("*[data-extend='RegistrationStep']").waitUntil(Condition.visible, 5000);
        return new CompanyProfile();
    }
}
