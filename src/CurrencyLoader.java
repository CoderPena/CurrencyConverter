import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CurrencyLoader {

    public static List<CurrencyInfo> loadCurrencies(String apiKey) throws Exception {
        String urlString = "https://v6.exchangerate-api.com/v6/" + apiKey + "/codes";
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Erro na API: " + conn.getResponseCode());
        }

        // Lê JSON
        Scanner sc = new Scanner(url.openStream());
        StringBuilder inline = new StringBuilder();
        while (sc.hasNext()) {
            inline.append(sc.nextLine());
        }
        sc.close();

        // Converte JSON para objetos
        Gson gson = new Gson();
        JsonObject json = gson.fromJson(inline.toString(), JsonObject.class);

        JsonArray codesArray = json.getAsJsonArray("supported_codes");

        List<CurrencyInfo> currencies = new ArrayList<>();

        for (int i = 0; i < codesArray.size(); i++) {
            JsonArray item = codesArray.get(i).getAsJsonArray();
            String code = item.get(0).getAsString();
            String name = item.get(1).getAsString();

            // Se quiser excluir BRL do menu de destino, pode pular aqui
            if (!code.equals("BRL")) {
                currencies.add(new CurrencyInfo(code, name));
            }
        }

        return currencies;
    }
}
