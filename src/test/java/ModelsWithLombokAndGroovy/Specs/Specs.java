package ModelsWithLombokAndGroovy.Specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

public class Specs {
    public static ResponseSpecification responseSpec = new ResponseSpecBuilder().expectStatusCode(200)
//            .expectBody(containsString("success"))
            .build();
}
