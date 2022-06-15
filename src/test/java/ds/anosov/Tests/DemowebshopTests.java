package ds.anosov.Tests;

import com.codeborne.selenide.WebDriverRunner;
import ds.anosov.ConfigTest.registrationWorkShopConfig;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ds.anosov.helpers.CustomApiListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

/**
 * 18. REST API. Декомпозируем UI-тесты.
 */

public class DemowebshopTests extends TestBase {

    registrationWorkShopConfig configWorkShop = ConfigFactory.create(registrationWorkShopConfig.class, System.getProperties());
    String loginWorkShop = configWorkShop.loginWorkShop();
    String passwordWorkShop = configWorkShop.passwordWorkShop();
    String authCookieName = configWorkShop.authCookieName();

    @Test
    @DisplayName("Проверка корректности отображения успешной авторизации")
    void loginWithApiAndCustomListenerTest() {
        step("Получить куки авторизации из API метода и добавить их в браузер", () -> {
            String authCookieValue = given()
                    .filter(withCustomTemplates()) //Настроенный листнер (улучшена отчетность в аллюре)
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("Email", loginWorkShop)
                    .formParam("Password", passwordWorkShop)
                    .log().all()
                    .when()
                    .post("/login")
                    .then()
                    .log().all()
                    .statusCode(302)
                    .extract().cookie(authCookieName);

            step("Открыть легковесную страницу сайта", () ->
                    open("/Themes/DefaultClean/Content/images/logo.png"));
            step("Добавить куки в открытый браузер(с легковесной страницей", () -> {
                Cookie authCookie = new Cookie(authCookieName, authCookieValue);
                WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
            });
        });

        step("Открыть главную страницу сайта", () ->
                open(""));
        step("Проверка успешной авторизации по логину в личном кабинете", () ->
                $(".account").shouldHave(text(loginWorkShop)));
    }
}
