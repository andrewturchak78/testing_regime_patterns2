import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

class AuthTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldRegisterSuccessful() {
        var registeredUser = DataRegistrator.Registration.getRegisteredUser("active");
        $("[data-test-id=login] input").val(registeredUser.getLogin());
        $("[data-test-id=password] input").val(registeredUser.getPassword());
        $("[data-test-id=action-login]").click();
        $x("//h2").should(text("Личный кабинет"));
    }

    @Test
    void shouldShowBlockedUser() {
        var blockedUser = DataRegistrator.Registration.getRegisteredUser("blocked");
        $("[data-test-id=login] input").val(blockedUser.getLogin());
        $("[data-test-id=password] input").val(blockedUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldBe(text("Ошибка! " + "Пользователь заблокирован"));
    }
    @Test
    void shouldUnregisteredUserIfWrongPassword() {
        var unRegisteredUser = DataRegistrator.Registration.getRegisteredUser("active");
        var wrongPassword = DataRegistrator.getRandomPassword();
        $("[data-test-id=login] input").val(unRegisteredUser.getLogin());
        $("[data-test-id=password] input").val(wrongPassword);
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldBe(text("Ошибка! " + "Неверно указан логин или пароль"));
    }
    @Test
    void shouldUnregisteredUserIfWrongLogin() {
        var unRegisteredUser = DataRegistrator.Registration.getRegisteredUser("active");
        var wrongLogin = DataRegistrator.getRandomLogin();
        $("[data-test-id=login] input").val(unRegisteredUser.getLogin());
        $("[data-test-id=password] input").val(wrongLogin);
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldBe(text("Ошибка! " + "Неверно указан логин или пароль"));
    }
}
