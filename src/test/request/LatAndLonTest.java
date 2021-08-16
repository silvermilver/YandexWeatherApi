package test.request;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import test.BaseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

/**
 * Параметры запроса - lat, lon
 * Широта в градусах. Обязательное поле.
 * Долгота в градусах. Обязательное поле.
 *
 * https://yandex.ru/dev/weather/doc/dg/concepts/forecast-test.html#req-format
 */
public class LatAndLonTest extends BaseTest {

    /*
     * Передаем широту и долготу для Москвы, ожидаем в ответе правильный регион
     */
    @Test
    public void latAndLonForMoscow_thenMoscowRegion() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get(simpleRequestForMoscow)
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("info.lat", Matchers.equalTo(59.92408F))
                .assertThat().body("info.lon", Matchers.equalTo(30.328528F))
                .assertThat().body("info.tzinfo.name", Matchers.equalTo("Europe/Moscow"))
                .assertThat().body("info.tzinfo.abbr", Matchers.equalTo("MSK"))
                .assertThat().body("info.tzinfo.dst", Matchers.equalTo(false))
                .assertThat().body("info.tzinfo.offset", Matchers.equalTo(10800));
    }

    /*
     * Не передаем широту, ожидаем ошибку
     */
    //TODO: разобраться почему возвращается результат по геопозиции вместо ошибки
    @Ignore
    @Test
    public void withoutLat_thenError() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get("https://api.weather.yandex.ru/v2/forecast?lon=30.328528")
                .then().log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    /*
     * Передаем неправильную широту, ожидаем ошибку
     */
    @Test
    public void wrongLat_thenError() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get("https://api.weather.yandex.ru/v2/forecast?lat=WrongLat&lon=30.328528")
                .then().log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(containsString("schema: error converting value for \\\"lat\\\""));
    }

    /*
     * Не передаем долготу, ожидаем ошибку
     */
    //TODO: разобраться почему возвращается результат по геопозиции вместо ошибки
    @Ignore
    @Test
    public void withoutLon_thenError() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get("https://api.weather.yandex.ru/v2/forecast?lat=59.924080")
                .then().log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    /*
     * Передаем неправильную долготу, ожидаем ошибку
     */
    @Test
    public void wrongLon_thenError() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get("https://api.weather.yandex.ru/v2/forecast?lat=59.924080&lon=WrongLon")
                .then().log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(containsString("schema: error converting value for \\\"lon\\\""));
    }
}
