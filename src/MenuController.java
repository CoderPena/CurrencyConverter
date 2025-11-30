import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MenuController {

    private static final String BASE_CURRENCY = "BRL";
    private static final String DISPLAY_BASE = "Brazilian Real";
    private static final Scanner scanner = new Scanner(System.in);
    private static final String DEFAULT_CURRENCY_COUNT = "6";

    private String criterioMenu = DEFAULT_CURRENCY_COUNT; // Pode virar atributo configurável

    public void start() {
        try {
            System.out.println("#############################################");
            System.out.println("###### [Aplicação Conversor de Moedas] ######");
            System.out.println("#############################################");

            List<CurrencyInfo> currencies = CurrencyLoader.loadCurrencies(AppConfig.API_KEY);
            currencies.removeIf(c -> c.getCode().equals(BASE_CURRENCY));

            while (true) {
                processMenu(currencies);
            }

        } catch (Exception e) {
            System.out.println("Erro ao carregar moedas: " + e.getMessage());
        }
    }

    private void processMenu(List<CurrencyInfo> currencies) {
        String optionInput = "";
        try {
            List<MenuOption> menuOptions =
                    criterioMenu.matches("\\d+") ?
                            MenuGenerator.generateRandomMenu(currencies, BASE_CURRENCY, DISPLAY_BASE, Integer.parseInt(criterioMenu)) :
                            MenuGenerator.generateRandomMenu(currencies, BASE_CURRENCY, DISPLAY_BASE, criterioMenu);

            menuOptions.forEach(option -> System.out.println(option.getDescription()));

            optionInput = scanner.nextLine().trim().toUpperCase();
            criterioMenu = optionInput;

            handleOption(menuOptions, currencies, optionInput);

        } catch (NumberFormatException e) {
            handleInvalidInput(optionInput, currencies);
        }
    }

    private void handleOption(List<MenuOption> menuOptions, List<CurrencyInfo> currencies, String optionInput) {
        int optionNumber = Integer.parseInt(optionInput);

        if (optionNumber == 0) {
            criterioMenu = PaginationUtil.paginatedDisplay(currencies);
        } else if (optionNumber == 99) {
            System.out.println("\nPrograma encerrado pelo usuário.");
            System.exit(0);
        } else {
            Optional<MenuOption> selectedOption = menuOptions.stream()
                    .filter(o -> o.getOption() == optionNumber)
                    .findFirst();

            if (selectedOption.isPresent()) {
                MenuOption op = selectedOption.get();
                System.out.printf("Converter de %s para %s\n", op.getFromCurrency(), op.getToCurrency());
                double amount = CurrencyUtils.readAmountFromUser(scanner, op.getFromCurrency());

                // ConversionResult result = ApiConverter.convert(API_KEY, op.getFromCurrency(), op.getToCurrency(), amount);
                CurrencyConversionService service = new CurrencyConversionService();
                double converted = service.convert(op.getFromCurrency(), op.getToCurrency(), amount);

                System.out.printf("Valor convertido (formato nacional): %s %s -> %s %s\n",
                        CurrencyUtils.formatCurrencyBR(amount), op.getFromCurrency(),
                        CurrencyUtils.formatCurrencyBR(converted), op.getToCurrency()
                );
                System.out.printf("Valor convertido (formato internacional): %s %s -> %s %s\n",
                        CurrencyUtils.formatCurrencyInternational(amount), op.getFromCurrency(),
                        CurrencyUtils.formatCurrencyInternational(converted), op.getToCurrency()
                );

                criterioMenu = DEFAULT_CURRENCY_COUNT; // Volta para modo padrão

            } else {
                System.out.println("Opção inválida!");
            }
        }
    }

    private void handleInvalidInput(String optionInput, List<CurrencyInfo> currencies) {
        if (CurrencyUtils.isValidCurrencyCode(optionInput, currencies)) {
            criterioMenu = optionInput;
        } else {
            System.out.println("Opção inválida! -> " + optionInput);
            criterioMenu = DEFAULT_CURRENCY_COUNT; // Volta para modo padrão
        }
    }
}
