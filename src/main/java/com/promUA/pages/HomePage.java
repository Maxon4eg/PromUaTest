package com.promUA.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$;

public class HomePage {

    private SelenideElement loginBtn = $("div[data-info-auth-user-id] button"),
            registrationDrpdwnElmnt = $("span[data-qaid='reg_element']"),
            regAsAByuer = $("a[data-qaid='reg_as_a_byuer_btn']"),
            regAsACompany = $("a[data-qaid='reg_as_company_btn']"),
            searchInput = $("input[data-qaid='search_input']"),
            searchSbmt = $("button[data-qaid='search_button']");
    @Getter
    private SelenideElement autocompleteBlock = $("div[data-qaid='autocomplete_block']");

    @Step("Go to register byer page ")
    public RegistrationPage registerAsAByuer() {
        registrationDrpdwnElmnt.hover();
        regAsAByuer.shouldBe(Condition.visible).click();
        return new RegistrationPage();
    }

    @Step("Go to register company page ")
    public RegistrationPage registerAsACompany() {
        registrationDrpdwnElmnt.hover();
        regAsACompany.shouldBe(Condition.visible).click();
        return new RegistrationPage();
    }

    @Step("Go to login page ")
    public LoginPage goToLoginPage() {
        loginBtn.click();
        return new LoginPage();
    }

    @Step("Search for = {0}")
    public ProductListPage searchFor(String searchTerm) {
        searchInput.sendKeys(searchTerm);
        searchSbmt.click();
        autocompleteBlock.waitUntil(Condition.disappear, 10000);
        return new ProductListPage();
    }

}
