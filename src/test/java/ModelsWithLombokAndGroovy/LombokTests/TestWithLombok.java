package ModelsWithLombokAndGroovy.LombokTests;

import ModelsWithLombokAndGroovy.Lombok.LombokUserData;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static ModelsWithLombokAndGroovy.Specs.Specs.responseSpec;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestWithLombok {

    @Test
    public void singleUserGetTest() {
        get("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(2))
                .body("data.first_name", is("Janet"));
    }


    //сделай спеку
    @Test
    public void singleUserGetTestWithLombok() {
        LombokUserData data = given()
                .contentType(ContentType.JSON)
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .spec(responseSpec)
                .log().status()
                .log().body()
                //.statusCode(200)

                //.body("data.id", is(2))
               // .body("data.first_name", is("Janet"));
                .extract().as(LombokUserData.class);
            //     assertEquals(2, data.getUser().getId());
    }
}
