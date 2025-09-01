import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

public class RedirectLongTest {

    @Test
    public void testRestAssured(){
        String oneURL = "https://playground.learnqa.ru/api/long_redirect";
        boolean boolURL = false;
        int i = 0;

        Map<String, String> headers = new HashMap<>();
        headers.put("myHeader1","myValue1");
        headers.put("myHeader2","myValue2");

        do {
            Response response = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .when()
                    .get(oneURL)
                    .andReturn();
            if (response.statusCode() == 200 || oneURL == null) {
                boolURL = true;
            } else {
                oneURL = response.getHeader("Location");
                i++;
            }
//            String locationHeader = response.getHeader("Location");
//            System.out.println(locationHeader);
        } while (!boolURL);
//        response.prettyPrint();
        System.out.println("Итоговый адрес: " + oneURL);
        System.out.println("Rоличество редиректов " + i);
    }
}
