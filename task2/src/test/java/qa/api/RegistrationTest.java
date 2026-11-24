package qa.api;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class RegistrationTest {

    @Test
    void registerReturnCreatedUser() {
        String username = "student_" + System.currentTimeMillis();
        String requestedJson = """
                {"username": "%s", "password": "strong_password"}
                """.formatted(username);

        given()
                .baseUri("https://qa-internship.avito.com")
                .contentType(ContentType.JSON)
                .body(requestedJson)
                .when()
                .post("/api/1/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("id", instanceOf(Number.class))
                .body("username", equalTo(username));

    }

    @Test
    void shouldRejectRegistrationWithoutUsername() {
        String requestedJson = """
                {"password": "strong_password"}
                """;

        given()
                .baseUri("https://qa-internship.avito.com")
                .contentType(ContentType.JSON)
                .body(requestedJson)
                .when()
                .post("/api/1/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("id", nullValue())
                .body("username", nullValue());

    }

    @Test
    void shouldRejectRegistrationWithEmptyUserName() {

        String requestedJson = """
                {"username": "", "password": "strong_password"}
                """;

        given()
                .baseUri("https://qa-internship.avito.com")
                .contentType(ContentType.JSON)
                .body(requestedJson)
                .when()
                .post("/api/1/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("id", nullValue())
                .body("username", nullValue());

    }

    @Test
    void shouldRejectRegistrationWithDoubleRegistration() {
        String username = "student_" + System.currentTimeMillis();
        String requestedJson = """
                {"username": "%s", "password": "strong_password"}
                """.formatted(username);

        String secondJson = """
                {"username": "%s", "password": "another_strong_password"}
                """.formatted(username);

        given()
                .baseUri("https://qa-internship.avito.com")
                .contentType(ContentType.JSON)
                .body(requestedJson)
                .when()
                .post("/api/1/register")
                .then()
                .log().status()
                .statusCode(201);

        given()
                .baseUri("https://qa-internship.avito.com")
                .contentType(ContentType.JSON)
                .body(secondJson)
                .when()
                .post("/api/1/register")
                .then()
                .log().status()
                .statusCode(409);

        given()
                .baseUri("https://qa-internship.avito.com")
                .contentType(ContentType.JSON)
                .body(requestedJson)
                .when()
                .post("/api/1/autorize")
                .then()
                .log().status()
                .statusCode(200)
                .body("accessToken", not(isEmptyOrNullString()));

        given()
                .baseUri("https://qa-internship.avito.com")
                .contentType(ContentType.JSON)
                .body(secondJson)
                .when()
                .post("/api/1/autorize")
                .then()
                .log().status()
                .statusCode(401)
                .body("accessToken", nullValue());
    }

    @Test
    void authReturnUser() {
        String username = "student_" + System.currentTimeMillis();
        String requestedJson = """
                {"username": "%s", "password": "strong_password"}
                """.formatted(username);

        int sellerId = given()
                .baseUri("https://qa-internship.avito.com")
                .contentType(ContentType.JSON)
                .body(requestedJson)
                .when()
                .post("/api/1/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("id", instanceOf(Number.class))
                .body("username", equalTo(username))
                .extract()
                .path("id");

        String token = given()
                .baseUri("https://qa-internship.avito.com")
                .contentType(ContentType.JSON)
                .body(requestedJson)
                .when()
                .post("/api/1/autorize")
                .then()
                .log().status()
                .statusCode(200)
                .body("accessToken", not(isEmptyOrNullString()))
                .body("tokenType", equalTo("Bearer"))
                .extract().path("accessToken");

        given()
                .baseUri("https://qa-internship.avito.com")
                .header("Authorization", "Bearer " + token)
                .pathParam("sellerId", sellerId)
                .when()
                .get("/api/1/{sellerId}/item")
                .then()
                .log().status()
                .statusCode(200)
                .body("size()", equalTo(0));
    }

    @Test
    void shouldRejectRegistrationWithoutPassword() {
        String username = "student_" + System.currentTimeMillis();

        String requestedJson = """
                {"username": "%s"}
                """.formatted(username);

        given()
                .baseUri("https://qa-internship.avito.com")
                .contentType(ContentType.JSON)
                .body(requestedJson)
                .when()
                .post("/api/1/register")
                .then()
                .log().status()
                .statusCode(400)
                .body("id", nullValue())
                .body("username", nullValue());
    }

    @Test
    void shouldRejectRegistrationWithEmptyPassword() {
        String username = "student_" + System.currentTimeMillis();

        String requestedJson = """
                {"username": "%s", "password": ""}
                """.formatted(username);

        given()
                .baseUri("https://qa-internship.avito.com")
                .contentType(ContentType.JSON)
                .body(requestedJson)
                .when()
                .post("/api/1/register")
                .then()
                .log().status()
                .statusCode(400)
                .body("id", nullValue())
                .body("username", nullValue());
    }

    @Test
    void shouldRejectAuthorizationWithWrongPassword() {
        String username = "student_" + System.currentTimeMillis();
        String requestedJson = """
                {"username": "%s", "password": "strong_password"}
                """.formatted(username);

        given()
                .baseUri("https://qa-internship.avito.com")
                .contentType(ContentType.JSON)
                .body(requestedJson)
                .when()
                .post("/api/1/register")
                .then()
                .log().status()
                .statusCode(201);

        String wrongCredentials = """
                {"username": "%s", "password": "another_strong_password"}
                """.formatted(username);

        given()
                .baseUri("https://qa-internship.avito.com")
                .contentType(ContentType.JSON)
                .body(wrongCredentials)
                .when()
                .post("/api/1/autorize")
                .then()
                .log().status()
                .statusCode(401)
                .body("accessToken", nullValue());
    }

    @Test
    void shouldRejectAuthorizationWithUnknownUsername() {
        String username = "student_" + System.currentTimeMillis();
        String requestedJson = """
                {"username": "%s", "password": "strong_password"}
                """.formatted(username);

        given()
                .baseUri("https://qa-internship.avito.com")
                .contentType(ContentType.JSON)
                .body(requestedJson)
                .when()
                .post("/api/1/autorize")
                .then()
                .log().status()
                .statusCode(401)
                .body("accessToken", nullValue());
    }

    @Test
    void shouldRejectAuthorizationWithoutUsername() {
        String requestedJson = """
                {"password": "strong_password"}
                """;

        given()
                .baseUri("https://qa-internship.avito.com")
                .contentType(ContentType.JSON)
                .body(requestedJson)
                .when()
                .post("/api/1/autorize")
                .then()
                .log().status()
                .statusCode(400)
                .body("accessToken", nullValue());
    }

    @Test
    void shouldRejectAuthorizationWithoutPassword() {
        String username = "student_" + System.currentTimeMillis();
        String requestedJson = """
                {"username": "%s", "password": "strong_password"}
                """.formatted(username);

        given()
                .baseUri("https://qa-internship.avito.com")
                .contentType(ContentType.JSON)
                .body(requestedJson)
                .when()
                .post("/api/1/register")
                .then()
                .log().status()
                .statusCode(201);

        String incompleteJson = """
                {"username": "%s"}
                """.formatted(username);

        given()
                .baseUri("https://qa-internship.avito.com")
                .contentType(ContentType.JSON)
                .body(incompleteJson)
                .when()
                .post("/api/1/autorize")
                .then()
                .log().status()
                .statusCode(400)
                .body("accessToken", nullValue());
    }
}
