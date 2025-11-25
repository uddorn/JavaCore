package Arrays.code;

public class Main {
    public static void main(String[] args) {
        FirmRegistry registry = new FirmRegistry();

        System.out.println("Початковий масив фірм");
        registry.displayAllFirms();

        System.out.println("\n=============================================");
        System.out.println("Модифікація елемента з індексом 3");
        registry.modifyFirm(3, "SuperEcoFuel+", 100, 50.0, "Нова Енергетика");

        System.out.println("\n--- Масив після модифікації ---");
        registry.displayAllFirms();

        System.out.println("\n=============================================");
        System.out.println("Фільтрація (співробітників > 400)");
        registry.displayFirmsWithMoreThan(400);

        System.out.println("\n=============================================");
        System.out.println("Сортування за зростанням");
        registry.sortFirmsByEmployeeCount(true);

        System.out.println("\n=============================================");
        System.out.println("Сортування за спаданням");
        registry.sortFirmsByEmployeeCount(false);
    }
}