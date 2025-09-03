import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


public class TokenTest {


    @Test
    public void testRestAssured() throws InterruptedException {

        Map<String, String> headers = new HashMap<>();
        headers.put("myHeader1","myValue1");
        headers.put("myHeader2","myValue2");

        // Парсим ответ, что бы вынуть потом token
        JsonPath response = RestAssured
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        // Объявляем переменую типа String и извлекает token из response в виде строки.
        String tokenOne = response.get("token");
        // Объявляем переменую типа int и извлекает seconds из response в виде числа.
        int oneSeconds = response.get("seconds");

        JsonPath response1 = RestAssured
                .given()
                .queryParam("token", tokenOne)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        // Объявляем переменую типа String и извлекает status из response в виде строки.
        String status = response1.get("status");
        assert status.equals("Job is NOT ready");

        int x = oneSeconds * 1000;
        Thread.sleep(x);

        JsonPath response2 = RestAssured
                .given()
                .queryParam("token", tokenOne)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        // Перезаписываем status из response в виде строки.
        status = response2.get("status");
        assert status.equals("Job is ready");
    }
}