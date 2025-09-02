import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TokenTest {


    @Test
    public void testRestAssured(){

        Map<String, String> headers = new HashMap<>();
        headers.put("myHeader1","myValue1");
        headers.put("myHeader2","myValue2");

        // Парсим ответ, что бы вынуть потом token
        JsonPath response = RestAssured
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
//        НУЖНО УДАЛИТЬ ЭТО ДЛЯ ОТЛАДКИ
        response.prettyPrint();

        // Объявляем переменую типа String и извлекает token из response в виде строки.
        String tokenOne = response.get("token");
//        НУЖНО УДАЛИТЬ ЭТО ДЛЯ ОТЛАДКИ
        System.out.println(tokenOne);

        JsonPath response1 = RestAssured
                .given()
                .queryParam("token", tokenOne)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();

        // Объявляем переменую типа String и извлекает token из response в виде строки.
        String status = response.get("status");
//        НУЖНО УДАЛИТЬ ЭТО ДЛЯ ОТЛАДКИ
        response1.prettyPrint();

        if (status == "Job is NOT ready") {


        }
    }
}
