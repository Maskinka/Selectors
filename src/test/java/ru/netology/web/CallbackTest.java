package ru.netology.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CallbackTest {

    @BeforeEach
    public void openBrowser() {
        open("http://localhost:9999/");
    }

    @Test
    public void formSubmission() {
        $("[data-test-id=name] input").setValue("Иванов Андрей");
        $("[data-test-id=phone] input").setValue("+79130001020");
        $("[data-test-id=agreement]").click();
        $("[type = 'button']").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена!" +
                " Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    public void formSubmissionDoubleLastName() {
        $("[data-test-id=name] input").setValue("Иванов-Петров Андрей");
        $("[data-test-id=phone] input").setValue("+79130001020");
        $("[data-test-id=agreement]").click();
        $("[type = 'button']").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена!" +
                " Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    public void invalidName() {
        $("[data-test-id=name] input").setValue("Kevin");
        $("[data-test-id=phone] input").setValue("+79130001020");
        $("[data-test-id=agreement]").click();
        $("[type = 'button']").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные" +
                " неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void invalidPhone() {
        $("[data-test-id=name] input").setValue("Олегов Олег");
        $("[data-test-id=phone] input").setValue("+791");
        $("[data-test-id=agreement]").click();
        $("[type = 'button']").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно." +
                " Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void formSubmissionNotFilledName() {
        $("[data-test-id=phone] input").setValue("+79130001020");
        $("[data-test-id=agreement]").click();
        $("[type = 'button']").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для" +
                " заполнения"));
    }

    @Test
    public void formSubmissionNotFilledPhone() {
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=agreement]").click();
        $("[type = 'button']").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для" +
                " заполнения"));
    }

    @Test
    public void submitFormWithoutCheckbox() {
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79130001020");
        $("[type = 'button']").click();
        $("[data-test-id='agreement'].input_invalid").shouldHave(exactText("Я соглашаюсь с условиями" +
                " обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных" +
                " историй"));
    }
}