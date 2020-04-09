package com.promUA.tests;

import com.codeborne.selenide.Selenide;
import com.promUA.pages.HomePage;
import com.promUA.pages.ProductListPage;
import com.promUA.util.TestHelper;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SearchTests extends BaseTests {

    @DataProvider
    public static Object[][] searchTermProvider() {
        return new Object[][]{
                {"плеер"},
                {"телевизор"},
                {"доберман"}
        };
    }

    @DataProvider
    public static Object[][] productNamesProvider() {
        return new Object[][]{
                {"плеер"},
                {"телевизор"},
        };
    }

    @Test(dataProvider = "searchTermProvider")
    @Description("Verify that auto ")
    public void testAutocompleteAndSearchTermPresisting(String searchTerm) {
        Selenide.open("/");
        HomePage homePage = new HomePage();
        homePage.searchFor(searchTerm);
        String url = this.getCurrentUrl();
        Allure.step("Url after search submit " + url);
        String[] split = url.split("search_term=");
        Assert.assertTrue(split.length > 1, "search term existing");
        Assert.assertEquals(TestHelper.decodeHtml(split[1]), searchTerm, " passed search term in url ");
    }

    @Test(dataProvider = "productNamesProvider")
    @Description("Go to search resulting page and assert that products has searchTerm in product description")
    public void testValidateProductsOnPage(String productNamesSearch) {
        Selenide.open("/search?search_term=" + productNamesSearch);
        ProductListPage page = new ProductListPage();
        List<String> productListText = page.getProductTexts();
        page.goToNextPage();
        productListText.addAll(page.getProductTexts());
        List<String> validProducts = new ArrayList<>();
        List<String> notValidProducts = new ArrayList<>();

        for (String productText :
                productListText) {
            if (productText.contains(productNamesSearch)) {
                validProducts.add(productText
                        .concat("\n-------------------------------------------------------\n"));
            } else notValidProducts.add(productText
                    .concat("\n-------------------------------------------------------\n"));
        }
        Allure.step("List Of valid products");
        attachListToReport(validProducts, "validList");
        if (!notValidProducts.isEmpty()) {
            Allure.step("List Of NOT valid products");
            attachListToReport(notValidProducts, "NOTvalidList");
        }
        Assert.assertTrue(notValidProducts.isEmpty());

    }

    private void attachListToReport(List<String> validProducts, String name) {
        Path validProductsFile = Paths.get(UUID.randomUUID() + ".txt");
        try {
            Files.write(validProductsFile, validProducts, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Allure.addAttachment(name, new FileInputStream(validProductsFile.toFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
