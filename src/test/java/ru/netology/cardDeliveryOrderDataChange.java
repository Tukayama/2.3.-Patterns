package ru.netology;


import com.github.javafaker.Faker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.exactText;
;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class cardDeliveryOrderDataChange {
    private Faker faker;

    @BeforeEach
    void setUpAll() {
        faker = new Faker(new Locale("ru"));
    }

    @Test
    void shouldPreventSendRequestMultipleTimes() {
        String city = faker.address().cityName();
        String name = faker.name().fullName();
        String phone = faker.phoneNumber().phoneNumber();
        System.out.println(name);
        System.out.println(phone);
        System.out.println(city);

    }

    String generateDate() {

        return LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    }

    String planningDate = generateDate();

    @Test
    void fillingOutTheForm() {
        open("http://localhost:9999");
        $("[class=\"input__control\"][placeholder=\"Город\"]").setValue("Воронеж");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[class=\"input__control\"][name=\"name\"]").setValue("Иванов Иван");
        $("[class=\"input__control\"][name=\"phone\"]").setValue("+79098787865");
        $(".checkbox__box").click();
        $("[class=\"button__text\"]").click();
        $(".notification__content").shouldHave(exactText("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15));
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue("10.01.2022");


    }
}


