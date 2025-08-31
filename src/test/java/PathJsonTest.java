import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import java.util.List;

public class PathJsonTest {

    @Test
    public void testRestAssured(){
        JsonPath response = RestAssured
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();
// Объявляем переменую типа list и извлекает элементы из response в виде списка
        List answer = response.getList("messages");
        System.out.println(answer.get(1));
    }
}