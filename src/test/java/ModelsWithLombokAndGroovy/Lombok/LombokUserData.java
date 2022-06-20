package ModelsWithLombokAndGroovy.Lombok;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LombokUserData {
    // ниже берется ответ, который прислал GET запрос
    /*
    "data": {
        "id": 2,
        "email": "janet.weaver@reqres.in",
        "first_name": "Janet",
        "last_name": "Weaver",
     */

    @JsonProperty("data")
    private User user;
}
