import com.codeborne.selenide.Configuration;
import data.Catalog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class WebTest {

    @BeforeEach
    void setUp(){
        open("https://www.wildberries.ru/");
        Configuration.pageLoadStrategy = "eager"; // команда для того, чтобы селенид не ждал загрузки всех картинок и тяжелых элементов. Только html.
        Configuration.browserSize = "1920x1080";
//        Configuration.holdBrowserOpen = true;
    }

    @ValueSource(strings = {
            "Часы", "Очки", "iPhone"
    })
    @ParameterizedTest(name = "Поисковый запрос {0} должен отдавать не пустой список товаров")
    @Tag("Regression")
    void searchReturnsNonEmptyProductList(String searchQuery) {
        $("#searchInput").setValue(searchQuery).pressEnter();
        $$(".product-card-list").shouldBe(sizeGreaterThan(0));
    }

    @CsvFileSource(resources = "/test_data/SearchShouldMatchProductTitles.csv")
    @ParameterizedTest(name = "Поисковый запрос {0} должен соответствовать наименованию товаров {1}")
    @Tag("Regression")
    void searchShouldMatchProductTitles(String searchQuery, String expectedProduct) {
        $("#searchInput").setValue(searchQuery).pressEnter();
        $$(".product-card__brand-wrap").shouldHave(sizeGreaterThan(0)); // Проверяем, что есть какие-либо товары
        boolean expectedTitle = $$(".product-card__brand-wrap").texts()
                .stream()
                .anyMatch(title -> title.contains(expectedProduct));
    }
    @EnumSource(Catalog.class)
    @ParameterizedTest
    @Tag("Regression")
    void catalogShouldDisplayCorrectText(Catalog catalog) {
        $("[aria-label=\"Навигация по сайту\"]").click();
        $(".menu-burger__main-list").shouldBe(visible).shouldHave(text(catalog.name));
    }
}
