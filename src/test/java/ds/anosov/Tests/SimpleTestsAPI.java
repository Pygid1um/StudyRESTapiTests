package ds.anosov.Tests;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleTestsAPI {

    @Test
    public void simpleTest() {
        given()
                .log().uri() // это логи запроса
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status() // это логи ответа
                .body("used", is(0));
    }


    @Test
    public void simpleTestWithoutGiven() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .statusCode(200)
                .body("used", is(0));
    }

    @Test
    public void simpleTest2() {
       int testUrl = get("https://selenoid.autotests.cloud/status")
                .then().log().all()
               .extract().path("pending"); //сюда пишется ключ, который будет получен из Json документа

    /* Пример, если надо получить total в path(total) пишется total
         {
               total: 20,
                       used: 0,
                 queued: 0,
                    pending: 0,
    */

       int answerTest = 0;
       assertEquals(testUrl, answerTest);
    }

    @Test
    public void simpleTest3() {
        get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .statusCode(401);
    }

    @Test
    public void simpleTest4() {
        get("https://user1:1234@selenoid.autotests.cloud/wd/hub/status")
                .then()
                .statusCode(200);
    }


    @Test
    public void simpleTest5() {
        given()
                .auth().basic("user1","1234")
                .get("https://user1:1234@selenoid.autotests.cloud/wd/hub/status")
                .then()
                .statusCode(200);
    }

}
