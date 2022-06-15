package ds.anosov.Tests;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * 17. REST API. Пишем автотесты с Rest-assured
 */

public class FirstTestAPI {

    @Test
    public void singleUserGetTest() {
        get("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(2))
                .body("data.first_name",is("Janet"));
    }

    @Test
    public void deleteTest() {
        delete("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .statusCode(204);
    }

    @Test
    public void missingPasswordPostTest() {
        String requestBody = "{ \"email\": \"sydney@fife\" }";

        given()
                .log().uri()
                .log().body()
                .body(requestBody) //сюда вставляется тело отправляемого POST запроса
                .contentType(JSON) //обязательно нужно указывать тип (img, video, JSON, xml...)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));//это проверка ответа
    }

    @Test
    public void createPostTest() {
        String requestBodyTest = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

        given()
                .body(requestBodyTest)
                .contentType(JSON)
                .post("https://reqres.in/api/users")
                .then().log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"))
                .body("id", notNullValue())
                .body("createdAt", notNullValue());
    }

    @Test
    public void updatePutTest() {
        String requestBodyPutTest = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";

        given()
                .body(requestBodyPutTest)
                .contentType(JSON)
                .put("https://reqres.in/api/users/2")
                .then().log().body()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"))
                .body("updatedAt", notNullValue());
    }
}
