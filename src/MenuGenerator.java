import java.util.*;

public class MenuGenerator {
    public static List<MenuOption> generateRandomMenu(
            List<CurrencyInfo> currencies,
            String baseCurrency,
            String displayBase,
            int qdeOpcoes
    ) {
        List<CurrencyInfo> shuffled = new ArrayList<>(currencies);
        Collections.shuffle(shuffled);

        int numberOfCurrencies = Math.min(qdeOpcoes, shuffled.size());
        List<CurrencyInfo> selected = shuffled.subList(0, numberOfCurrencies);

        return buildMenu(selected, baseCurrency, displayBase);
    }
    public static List<MenuOption> generateRandomMenu(
            List<CurrencyInfo> currencies,
            String baseCurrency,
            String displayBase,
            String currencyCode
    ) {
        List<CurrencyInfo> filtered = currencies.stream()
                .filter(c -> c.getCode().equalsIgnoreCase(currencyCode))
                .toList();

        return buildMenu(filtered, baseCurrency, displayBase);
    }
    private static List<MenuOption> buildMenu(
            List<CurrencyInfo> selected,
            String baseCurrency,
            String displayBase
    ) {
        List<MenuOption> menu = new ArrayList<>();
        int optionNumber = 1;

        menu.add(new MenuOption(-1, "", "", "\n " + selected.size() * 2 + " Opções de Conversão Ida e Volta de " + selected.size() + " Moedas aleatórias:\n"));

        for (CurrencyInfo c : selected) {
            String option1 = String.format("%2d - %s [%s] -> %s [%s]",
                    optionNumber, displayBase, baseCurrency, c.getName(), c.getCode());
            menu.add(new MenuOption(optionNumber++, baseCurrency, c.getCode(), option1));

            String option2 = String.format("%2d - %s [%s] -> %s [%s]",
                    optionNumber, c.getName(), c.getCode(), displayBase, baseCurrency);
            menu.add(new MenuOption(optionNumber++, c.getCode(), baseCurrency, option2));
        }

        menu.add(new MenuOption(0, "", "", "\n 0  - Listar todas as moedas disponíveis para conversão"));
        menu.add(new MenuOption(99, "", "", " 99 - Sair do programa\n"));
        menu.add(new MenuOption(99, "", "", "<ENTER> Apresenta 6 moedas aleatórias com conversão ida e volta"));
        menu.add(new MenuOption(99, "", "", "Número maior que " + selected.size() * 2 + " apresenta este número de moedas aleatórias\n"));
        menu.add(new MenuOption(-1, "", "", "----------------------------------------------"));
        menu.add(new MenuOption(-1, "", "", "Escolha uma opção acima ou entre o código da moeda: "));

        return menu;
    }
}
