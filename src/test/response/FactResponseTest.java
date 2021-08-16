package test.response;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import test.BaseTest;

import static io.restassured.RestAssured.given;

/**
 * Проверяются нужные параметры в ответе из бъекта fact
 *
 * https://yandex.ru/dev/weather/doc/dg/concepts/forecast-test.html#req-format
 */
//TODO: дописать тесты по нужным параметрам
public class FactResponseTest extends BaseTest {

    @Test
    public void checkFactParameters() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get(simpleRequestForMoscow)
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("fact.temp", Matchers.notNullValue())
                .assertThat().body("fact.temp", Matchers.isA(Integer.TYPE))
                .assertThat().body("fact.feels_like", Matchers.notNullValue())
                .assertThat().body("fact.feels_like", Matchers.isA(Integer.TYPE))
                .assertThat().body("fact.icon", Matchers.isA(String.class));
    }
}
