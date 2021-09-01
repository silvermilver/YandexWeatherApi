package test.response;


import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import test.BaseTest;

import static io.restassured.RestAssured.given;

//import org.hamcrest.Matchers;

/**
 * Проверяются нужные параметры в ответе из бъекта forecasts
 *
 * https://yandex.ru/dev/weather/doc/dg/concepts/forecast-test.html#req-format
 */
//TODO: дописать тесты по нужным параметрам
public class ForecastsResponseTest extends BaseTest {

    private final int [] moonCodes = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
    private final String [] moonTexts =
            {"moon-code-1",
            "moon-code-2",
            "moon-code-3",
            "moon-code-4",
            "moon-code-5",
            "moon-code-6",
            "moon-code-7",
            "moon-code-8",
            "moon-code-9",
            "moon-code-10",
            "moon-code-11",
            "moon-code-12",
            "moon-code-13",
            "moon-code-14",
            "moon-code-15"};

    @Test
    public void checkForecastsParameters_date() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get(simpleRequestForMoscow)
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("forecasts.date", Matchers.notNullValue())
                .assertThat().body("forecasts.date_ts", Matchers.notNullValue());
    }

    @Ignore
    @Test
    //TODO: привести типы к общему знаменателю в проверке
    public void checkForecastsParameters_moon() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get(simpleRequestForMoscow + "&limit=1")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("forecasts.moon_code", Matchers.isOneOf(moonCodes))
                .assertThat().body("forecasts.moon_text", Matchers.isOneOf(moonTexts));
    }
}
