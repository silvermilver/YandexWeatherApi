package test.request;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import test.BaseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

/**
 * Параметр запроса - extra
 * Расширенная информация об осадках. Допустимые значения:
 * «true» — расширенная информация об осадках возвращается.
 * «false» — значение по умолчанию, расширенная информация об осадках не возвращается.
 *
 * https://yandex.ru/dev/weather/doc/dg/concepts/forecast-test.html#req-format
 */
public class ExtraTest extends BaseTest {

    @Test
    //TODO: уточнить, где должна быть расширенная информация об осадках
    public void defaultParam_thenWithoutExtra() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get(simpleRequestForMoscow)
                .then().log().all()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    //TODO: уточнить, где должна быть расширенная информация об осадках
    public void withExtraTrue_thenWithExtra() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get(simpleRequestForMoscow + "&extra=true")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    //TODO: уточнить, где должна быть расширенная информация об осадках
    public void withExtraFalse_thenWithoutExtra() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get(simpleRequestForMoscow + "&extra=false")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    //TODO: BUG - не обрабатывается неправильное значение
    public void wrongExtra_thenError() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get(simpleRequestForMoscow + "&extra=wrongExtra")
                .then().log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(containsString("schema: error converting value for \\\"extra\\\""));
    }
}
