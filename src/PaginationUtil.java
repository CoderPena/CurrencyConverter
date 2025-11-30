import java.util.List;
import java.util.Scanner;

public class PaginationUtil {

    public static String paginatedDisplay(List<CurrencyInfo> list) {
        Scanner scanner = new Scanner(System.in);
        int pageSize = 10;
        int totalPages = (int) Math.ceil((double) list.size() / pageSize);
        String chosenCode = "";

        for (int page = 0; page < totalPages; page++) {
            System.out.println("\n=== Página " + (page + 1) + "/" + totalPages + " ===");

            int start = page * pageSize;
            int end = Math.min(start + pageSize, list.size());

            list.subList(start, end).stream()
                    .sorted((a, b) -> a.getName().compareToIgnoreCase(b.getName()))
                    .forEach(c -> System.out.println("- " + c));

            if (page < totalPages - 1) {
                System.out.print("\nPressione ENTER para continuar, c para parar ou o código da moeda...");
                chosenCode = scanner.nextLine().toUpperCase();
                if (!chosenCode.isBlank()){
                    if(chosenCode.equalsIgnoreCase("c")){
                        chosenCode = "";
                    }
                    break;
                }
            }
        }
        return chosenCode;
    }
}
