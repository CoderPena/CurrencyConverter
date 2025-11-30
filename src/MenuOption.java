public class MenuOption {
    private int option;
    private String fromCurrency;
    private String toCurrency;
    private String description;

    public MenuOption(int option, String fromCurrency, String toCurrency, String description) {
        this.option = option;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.description = description;
    }

    public int getOption() {
        return option;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
