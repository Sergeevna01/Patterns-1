package ru.netology.delivery.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setup() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldReturnSuccessIfDateCorrect() {

        $("[data-test-id=city] [placeholder='Город']").setValue(DataGenerator.generateCity());
        $("[data-test-id=date] [class='input__box'] [placeholder='Дата встречи']")
                .doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [class='input__box'] [placeholder='Дата встречи']")
                .setValue(DataGenerator.generateDate(1));
        $("[data-test-id=name] [type=text]").setValue(DataGenerator.generateName("ru"));
        $("[data-test-id=phone] [type=tel]").setValue("+7" + DataGenerator.generatePhone());
        $("[data-test-id=agreement]").click();
        $("[role=button] .button__content").click();
        $(withText("Успешно!")).should(visible);
        $("[data-test-id='success-notification'] [class='notification__content']")
                .shouldHave(exactText("Встреча успешно запланирована на " + DataGenerator.generateDate(1)));

        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [class='input__box'] [placeholder='Дата встречи']")
                .setValue(DataGenerator.generateDate(2));
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='replan-notification']").shouldHave(exactText("Необходимо подтверждение\n" +
                "У вас уже запланирована встреча на другую дату. Перепланировать?\n" + "Перепланировать"));
        $$("button").find(exactText("Перепланировать")).shouldBe(visible).click();
        $(withText("Успешно!")).should(visible);
        $("[data-test-id=success-notification] .notification__content").shouldBe(visible).
                shouldHave(exactText("Встреча успешно запланирована на " + DataGenerator.generateDate(2)));
    }
}


