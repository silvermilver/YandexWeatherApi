package test.request;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import test.BaseTest;

import static io.restassured.RestAssured.given;

/**
 * Параметр запроса - lang
 * Сочетания языка и страны, для которых будут возвращены данные погодных формулировок. Допустимые значения:
 * «ru_RU» — русский язык для домена России.
 * «ru_UA» — русский язык для домена Украины.
 * «uk_UA» — украинский язык для домена Украины.
 * «be_BY» — белорусский язык для домена Беларуси.
 * «kk_KZ» — казахский язык для домена Казахстана.
 * «tr_TR» — турецкий язык для домена Турции.
 * «en_US» — международный английский.
 *
 * https://yandex.ru/dev/weather/doc/dg/concepts/forecast-test.html#req-format
 */
public class LangTest extends BaseTest {

    @Test
    public void checkDefaultLang_thenRuLang() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get(simpleRequestForMoscow)
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("geo_object.country.name", Matchers.equalTo("Россия"));
    }

    @Test
    public void checkLangFor_ru_RU_thenRightLang() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get(simpleRequestForMoscow + "&lang=ru_RU")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("geo_object.country.name", Matchers.equalTo("Россия"));
    }

    @Test
    public void checkLangFor_uk_UA_thenRightLang() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get(simpleRequestForMoscow + "&lang=uk_UA")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("geo_object.country.name", Matchers.equalTo("Росія"));
    }

    @Test
    public void checkLangFor_tr_TR_thenRightLang() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get(simpleRequestForMoscow + "&lang=tr_TR")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("geo_object.country.name", Matchers.equalTo("Rusya"));
    }

    @Test
    public void wrongLang_thenEnLang() {
        given().log().all()
                .header("X-Yandex-API-Key", keyValue)
                .when().get(simpleRequestForMoscow + "&lang=wrongLang")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("geo_object.country.name", Matchers.equalTo("Russian Federation"));
    }
}
