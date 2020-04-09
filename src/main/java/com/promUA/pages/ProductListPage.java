package com.promUA.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProductListPage {
    private String productCardsSelector = "div[data-qaid='product_gallery']>div[data-qaid='product_block']";
    private ElementsCollection paginationButtons = $$("*[data-qaid='pagination_button']");
    private SelenideElement paginationNextPage = $("*[data-qaid='next']");

    @Step("Gather product texts to list ")
    public List<String> getProductTexts() {
        ArrayList<String> list = new ArrayList<>();
        ElementsCollection productCards = $$(productCardsSelector);
        for (SelenideElement productCard : productCards) {
            String text = productCard.text();
            if (text != null) {
                list.add(productCard.text().toLowerCase());
            }
        }
        return list;
    }

    @Step("Go to next page ")
    public void goToNextPage() {
        paginationButtons.first().click();
        Selenide.sleep(3000);
    }
}
