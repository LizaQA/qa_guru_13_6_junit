package guru.qa;


import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;


import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class ParamsTest {

    @ValueSource(strings = {"Стул", "Кровать"})
    @ParameterizedTest(name = "При поиске в Hoff по запросу {0} в результатах отображается текст {0}")
    void hoffTestStol(String testData) {

        Selenide.open("https://hoff.ru/");
        $("input[name = 'search']").setValue(testData);
        $(".search-button").click();
        $$(".product-card").find(text(testData)).shouldBe(visible);

    }

    //@CsvFileSource(resources = "test_data/1. csv")
    @CsvSource(value = {
            "Стул, Стул Avalon",
            "Кровать, Кровать без подъёмного механизма Соренто"
    })
    @ParameterizedTest(name = "При поиске в Hoff по запросу {0} в результатах отображается текст {1}")
    void hoffTestStolComplex(String searchData, String expectedResult) {

        Selenide.open("https://hoff.ru/");
        $("input[name = 'search']").setValue(searchData);
        $(".search-button").click();
        $$(".product-card").find(text(expectedResult)).shouldBe(visible);

    }

    static Stream<Arguments> hoffTestStolVeryComplex() {
        return Stream.of(
                Arguments.of("Стул", List.of("Стул", "цвет")),
                Arguments.of("Кровать", List.of("Стул", "цвет"))
        );
    }

    @MethodSource
    @ParameterizedTest(name = "При поиске в Hoff по запросу {0} в результатах отображается текст {1}")
    void hoffTestStolVeryComplex(String searchData, List<String> expectedResult) {

        Selenide.open("https://hoff.ru/");
        $("input[name = 'search']").setValue(searchData);
        $(".search-button").click();
        $$(".product-card").shouldHave(CollectionCondition.texts());

    }

    @EnumSource(Sex.class)
    @ParameterizedTest
    void enumTest(Sex sex) {
        Selenide.open("https://hoff.ru/");
        $("input[name = 'search']").setValue(sex.desc);
        $(".search-button").click();
        $$(".product-card").find(text(sex.desc)).shouldBe(visible);

    }

}
