import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CurrencyConverterApp {

    public static void main(String[] args) {

        final String BASE_CURRENCY = "BRL";
        final String DISPLAY_BASE = "Brazilian Real";
        final String API_KEY = System.getenv("API_KEY");
        final String qdeOpcoes = "6";

        String criterioMenu = qdeOpcoes; // Menu sendo uma lista de N moedas ou menu de uma moeda especifica.

        Scanner scanner = new Scanner(System.in);

        System.out.println("#############################################");
        System.out.println("###### [Aplicação Conversor de Moedas] ######");
        System.out.println("#############################################");

        try {
            // 1. Carregar moedas
            List<CurrencyInfo> currencies = CurrencyLoader.loadCurrencies(API_KEY);
            // Remover BRL da lista
            currencies.removeIf(c -> c.getCode().equals(BASE_CURRENCY));

            String optionInput = "";
            List<MenuOption> menuOptions;
            while (true) {
                try {
                    // 2. Gerar menu aleatório com atributos
                    if (criterioMenu.matches("\\d+")) {
                        // processa como quantidade
                        menuOptions = MenuGenerator.generateRandomMenu(currencies, BASE_CURRENCY, DISPLAY_BASE, Integer.parseInt(criterioMenu));
                    } else {
                        // processa como moeda
                        menuOptions = MenuGenerator.generateRandomMenu(currencies, BASE_CURRENCY, DISPLAY_BASE, criterioMenu);
                    }

                    // 3. Exibir menu
                    for (MenuOption option : menuOptions) {
                        System.out.println(option.getDescription());
                    }
                    // 4. Captura da opção
                    optionInput = scanner.nextLine().trim().toUpperCase();
                    criterioMenu = optionInput;

                    int optionNumber = Integer.parseInt(optionInput);
                    if (optionNumber == 0) {
                        // Listar todas em forma paginada
                        optionInput = PaginationUtil.paginatedDisplay(currencies);
                        if (CurrencyUtils.isValidCurrencyCode(optionInput, currencies)) {
                            System.out.println("Opção válida! -> " + optionInput);
                            criterioMenu = optionInput;
                        } else {
                            System.out.println("Opção inválida! -> " + optionInput);
                            criterioMenu = qdeOpcoes;
                        }

                    } else if (optionNumber == 99) {
                        System.out.println("\nPrograma encerrado pelo usuário.");
                        return;
                    } else {
                        // Procurar opção válida
                        Optional<MenuOption> selectedOption = menuOptions.stream()
                                .filter(o -> o.getOption() == optionNumber)
                                .findFirst();

                        if (selectedOption.isPresent()) {
                            MenuOption op = selectedOption.get();
                            System.out.printf("Converter de %s para %s\n",
                                    op.getFromCurrency(), op.getToCurrency());

                            // Aqui você pode chamar diretamente a API de conversão
                            // Exemplo:
                            // double amount;
                            // System.out.print("Informe valor para conversão: ");
                            // amount = Double.parseDouble(scanner.nextLine());
                            // ConversionResult result = ApiConverter.convert(API_KEY, op.getFromCurrency(), op.getToCurrency(), amount);

                            break;

                        } else {
                            System.out.println("Opção inválida!");
                        }
                    }
                } catch (NumberFormatException e) {
                    if (CurrencyUtils.isValidCurrencyCode(optionInput, currencies)) {
                        System.out.println("Opção válida! -> " + optionInput);
                        criterioMenu = optionInput;
                    } else {
                        System.out.println("Opção inválida! -> " + optionInput);
                        criterioMenu = qdeOpcoes;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Erro ao carregar moedas: " + e.getMessage());
        }
    }
}
