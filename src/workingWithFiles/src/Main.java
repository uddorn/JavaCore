public class Main {
    public static void main(String[] args) {
        FirmRegistry registry = new FirmRegistry();

        System.out.println("=============================================");
        System.out.println("1. Зчитування даних з текстового файлу 'firms_data.txt'");
        registry.readFromTextFile("firms_data.txt");
        registry.displayAllFirms();

        System.out.println("\n=============================================");
        System.out.println("2. Сортування масиву за спаданням");
        registry.sortFirmsByEmployeeCount(false);

        System.out.println("\n=============================================");
        System.out.println("3. Запис відсортованих даних у бінарний файл 'firms.dat'");
        registry.writeToBinaryFile("firms.dat");

        FirmRegistry newRegistry = new FirmRegistry();
        System.out.println("\n--- Створено новий порожній реєстр ---");
        newRegistry.displayAllFirms();

        System.out.println("\n=============================================");
        System.out.println("4. Зчитування даних з бінарного файлу 'firms.dat'");
        newRegistry.readFromBinaryFile("firms.dat");

        System.out.println("\n--- Дані, завантажені з 'firms.dat' (мають бути відсортовані) ---");
        newRegistry.displayAllFirms();
    }
}