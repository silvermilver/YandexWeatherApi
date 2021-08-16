package test.response;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import test.BaseTest;

import static io.restassured.RestAssured.given;

/**
 * Проверяются нужные параметры в ответе из бъекта info
 *
 * lat	Широта (в градусах).	Число
 * lon	Долгота (в градусах).	Число
 * tzinfo	Информация о часовом поясе. Содержит поля offset, name, abbr и dst.	Объект
 * offset	Часовой пояс в секундах от UTC.	Число
 * name	Название часового пояса.	Строка
 * abbr	Сокращенное название часового пояса.	Строка
 * dst	Признак летнего времени.	Логический
 * def_pressure_mm	Норма давления для данной координаты (в мм рт. ст.).	Число
 * def_pressure_pa	Норма давления для данной координаты (в гектопаскалях).	Число
 * url	Страница населенного пункта на сайте Яндекс.Погода.	Строка
 *
 * https://yandex.ru/dev/weather/doc/dg/concepts/forecast-test.html#req-format
 */
public class InfoResponseTest extends BaseTest {

    @Test
    public void checkInfoParameters() {
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
                .assertThat().body("info.tzinfo.offset", Matchers.equalTo(10800))
                .assertThat().body("info.def_pressure_mm", Matchers.notNullValue())
                .assertThat().body("info.def_pressure_pa", Matchers.notNullValue())
                .assertThat().body("info.url", Matchers.notNullValue());
    }
}
