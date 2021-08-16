package test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

/**
 * Базовые проверки на работу сервера
 */
public class SmokeTest extends BaseTest{

    private final String requestWithoutObligatoryParam = "https://api.weather.yandex.ru/v2/forecast";

    @Test
    public void simpleRequestGet_thenStatusOK() {
       given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get(simpleRequestForMoscow)
                .then().log().all()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void emptyRequestGet_thenForbidden() {
        given().log().all()
                .when().get(requestWithoutObligatoryParam)
                .then().log().all()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .contentType(ContentType.TEXT);
    }

    /*
    * Ответ на запрос возвращается в формате JSON. Информация в ответе содержит:
    * now	Время сервера в формате Unixtime.	Число
    * now_dt	Время сервера в UTC.	Строка
    * info	Объект информации о населенном пункте.	Объект
    * fact	Объект фактической информации о погоде.	Объект
    * forecasts	Объект прогнозной информации о погоде.	Объект
    */
    @Test
    public void simpleRequestGet_thenOK() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get(simpleRequestForMoscow)
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .assertThat().body("now", Matchers.notNullValue())
                .assertThat().body("now_dt", Matchers.notNullValue())
                .assertThat().body("info", Matchers.notNullValue())
                .assertThat().body("fact", Matchers.notNullValue())
                .assertThat().body("forecasts", Matchers.notNullValue());
    }
}
