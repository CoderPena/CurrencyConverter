import java.util.List;
import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyUtils {

    public static boolean isValidCurrencyCode(String Currencycode, List<CurrencyInfo> currencies) {
        return Currencycode != null && !Currencycode.isBlank() &&
                currencies.stream()
                        .anyMatch(c -> c.getCode().equalsIgnoreCase(Currencycode));
    }

    public static double readAmountFromUser(Scanner scanner, String currencyCode) {
        while (true) {
            System.out.print("Digite o montante em " + currencyCode + " no formato 9.9999: ");
            String input = scanner.nextLine().trim();

            try {
                double value = Double.parseDouble(input);
                if (value <= 0) {
                    System.out.println("O valor deve ser maior que zero.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número válido.");
            }
        }
    }

    public static String formatCurrency(double value) {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
        nf.setMinimumFractionDigits(4);
        nf.setMaximumFractionDigits(4);
        return nf.format(value);
    }

}
