import java.util.List;

public class CurrencyUtils {

    public static boolean isValidCurrencyCode(String Currencycode, List<CurrencyInfo> currencies) {
        return Currencycode != null && !Currencycode.isBlank() &&
                currencies.stream()
                        .anyMatch(c -> c.getCode().equalsIgnoreCase(Currencycode));
    }
}
