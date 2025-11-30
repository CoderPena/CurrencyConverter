import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.Scanner;

public class CurrencyConversionService {

    private final Gson gson = new Gson();

    public double convert(String from, String to, double amount) {

        String apiUrl = String.format(
                Locale.US, // garante separador decimal = ponto
                "https://v6.exchangerate-api.com/v6/%s/pair/%s/%s/%.4f",
                AppConfig.API_KEY, from, to, amount
        );

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Erro ao conectar à API: " + connection.getResponseCode());
            }

            // Lê resposta da API
            StringBuilder jsonResponse = new StringBuilder();
            try (Scanner scanner = new Scanner(connection.getInputStream())) {
                while (scanner.hasNext()) {
                    jsonResponse.append(scanner.nextLine());
                }
            }

            // Parse usando Gson
            JsonObject json = gson.fromJson(jsonResponse.toString(), JsonObject.class);

            if (!json.has("conversion_result")) {
                throw new RuntimeException("Resposta inválida da API!");
            }

            return json.get("conversion_result").getAsDouble();

        } catch (IOException e) {
            throw new RuntimeException("Erro na requisição da API", e);
        }
    }
}
