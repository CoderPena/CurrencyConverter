import java.util.List;

public class CurrencyInfo {
    private String code;
    private String name;

    public CurrencyInfo(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() { return code; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return name + " [" + code + "]";
    }

    public static boolean isValidCurrencyCode(String code, List<CurrencyInfo> currencies) {
        for (CurrencyInfo c : currencies) {
            if (c.getCode().equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }

}
