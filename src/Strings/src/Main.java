public class Main {
    public static void main(String[] args) {
        FirmRegistry registry = new FirmRegistry();

        System.out.println("Початковий масив фірм");
        registry.displayAllFirms();

        System.out.println("\n=============================================");
        System.out.println("Сортування за назвою (зростання)");
        registry.sortFirmsByName(true);

        System.out.println("\nСортування за назвою (спадання)");
        registry.sortFirmsByName(false);

        System.out.println("\n=============================================");
        registry.displayFirmsByNameRegex("(?i).*a$");

        System.out.println("\n=============================================");
        registry.displayMinMaxRevenue();
    }
}