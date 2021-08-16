package test.request;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import test.BaseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

/**
 * Параметр запроса - limit
 * Количество дней в прогнозе, включая текущий.
 * Для тарифа «Тестовый» максимально допустимое значение — 7.
 *
 * https://yandex.ru/dev/weather/doc/dg/concepts/forecast-test.html#req-format
 */
public class LimitTest extends BaseTest {

    @Test
    public void defaultLimit_thenSevenDays() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get(simpleRequestForMoscow)
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("forecasts", Matchers.hasSize(7));
    }

    @Test
    public void limitOne_thenOneDay() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get(simpleRequestForMoscow + "&limit=1")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("forecasts", Matchers.hasSize(1));
    }

    @Test
    public void limitFive_thenFiveDays() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get(simpleRequestForMoscow + "&limit=5")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("forecasts", Matchers.hasSize(5));
    }

    /*
    * Для тарифа «Тестовый» максимально допустимое значение — 7
    */
    @Test
    public void limitEleven_thenSevenDaysForTestTariff() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get(simpleRequestForMoscow + "&limit=11")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("forecasts", Matchers.hasSize(7));
    }

    @Test
    public void wrongLimit_zero_thenError() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get(simpleRequestForMoscow + "&limit=0")
                .then().log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(containsString("limit should be in [1, 11]"));
    }

    @Test
    public void wrongLimit_minusSeven_thenError() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get(simpleRequestForMoscow + "&limit=-7")
                .then().log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(containsString("schema: error converting value for \\\"limit\\\""));
    }

    @Test
    public void wrongLimit_string_thenError() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get(simpleRequestForMoscow + "&limit=wrongLimit")
                .then().log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(containsString("schema: error converting value for \\\"limit\\\""));
    }
}
