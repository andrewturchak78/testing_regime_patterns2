import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

class AuthTest {
@BeforeEach
void setup(){
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
    void shouldShowBlockedUser(){
        var blockedUser = DataRegistrator.Registration.getRegisteredUser("blocked");
        $("[data-test-id=login] input").val(blockedUser.getLogin());
        $("[data-test-id=password] input").val(blockedUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldBe(text("Ошибка! " + "Пользователь заблокирован"));
    }
    }
