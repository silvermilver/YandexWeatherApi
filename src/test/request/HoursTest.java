package test.request;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import test.BaseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

/**
 * Параметр запроса - hours
 * Для каждого из дней ответ будет содержать прогноз погоды по часам. Допустимые значения:
 * «true» — значение по умолчанию, почасовой прогноз возвращается.
 * «false» — почасовой прогноз не возвращается.
 *
 * https://yandex.ru/dev/weather/doc/dg/concepts/forecast-test.html#req-format
 */
public class HoursTest extends BaseTest {

    @Test
    //TODO: BUG - уточнить, почему не соответствует спецификации
    public void defaultParam_thenWithHours() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get(simpleRequestForMoscow)
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("forecast.hours", Matchers.notNullValue());
    }

    @Test
    //TODO: BUG - уточнить, почему не соответствует спецификации
    public void withHoursTrue_thenWithHours() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get(simpleRequestForMoscow + "&hours=true")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("forecast.hours", Matchers.notNullValue());
    }

    @Test
    public void withoutHoursTrue_thenWithoutHours() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get(simpleRequestForMoscow + "&hours=false")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("forecast.hours", Matchers.nullValue());
    }

    @Test
    public void wrongHoursParam_thenError() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get(simpleRequestForMoscow + "&hours=wrongParam")
                .then().log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(containsString("schema: error converting value for \\\"hours\\\""));
    }
}
