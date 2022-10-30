import io.restassured.http.ContentType;
import jdk.dynalink.linker.support.Guards;
import org.junit.jupiter.api.Test;
import pojos.CreateUserRequest;
import pojos.CreateUserResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.assertj.core.api.Assertions.*;

public class RestTest {

    @Test
    public void getUsers(){
        given()
                .baseUri("https://reqres.in/api")
                .basePath("/users")
                .contentType(ContentType.JSON)
                .when().get()
                .then()
                .statusCode(200)
                .body("data[0].email", equalTo("george.bluth@reqres.in"));
    }

    @Test
    public void createUser(){
        CreateUserRequest rq = new CreateUserRequest();
        rq.setName("SomebodyNicolas");
        rq.setJob("Gaming");

        CreateUserResponse rs = given()
                .baseUri("https://reqres.in/api")
                .basePath("/users")
                .contentType(ContentType.JSON)
                .body(rq)
                .when().post()
                .then().extract().as(CreateUserResponse.class);

        assertThat(rs)
                .isNotNull()
                .extracting(CreateUserResponse::getName)
                .isEqualTo(rq.getName());
    }

}
