package test.request;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import test.BaseTest;

import static io.restassured.RestAssured.given;

/**
 * Заголовок запроса - X-Yandex-API-Key
 * Ключ, полученный на этапе подключения к API Яндекс.Погоды. Обязательное поле.
 *
 * https://yandex.ru/dev/weather/doc/dg/concepts/forecast-test.html#req-format
 */
public class ApiKeyTest extends BaseTest {

    /*
     * Передаем ключ, ожидаем успешный ответ от сервера
     */
    @Test
    public void witApiKey_thenStatusOk() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get(simpleRequestForMoscow)
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON);
    }

    /*
     * Не передаем ключ, ожидаем ошибку
     */
    @Test
    public void withoutApiKey_thenForbidden() {
        given().log().all()
                .when().get(simpleRequestForMoscow)
                .then().log().all()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    /*
     * Передаем неправильный ключ, ожидаем ошибку
     */
    @Test
    public void wrongApiKey_thenForbidden() {
        given().log().all()
                .header("X-Yandex-API-Key", "Wrong_API_Key")
                .when().get(simpleRequestForMoscow)
                .then().log().all()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
