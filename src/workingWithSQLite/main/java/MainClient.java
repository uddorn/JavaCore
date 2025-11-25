package workingWithSQLite.main.java;

import java.util.Scanner;

public class MainClient {

    private static FirmDatabase db;
    private static Scanner scanner;

    public static void main(String[] args) {
        db = new FirmDatabase();
        scanner = new Scanner(System.in);
        runMenu();
        db.closeConnection();
        scanner.close();
    }

    private static void runMenu() {
        boolean running = true;
        while (running) {
            printMenu();
            try {
                String input = scanner.nextLine();
                int choice = Integer.parseInt(input.replaceAll("\\s",""));

                switch (choice) {
                    case 1:
                        db.viewAllFirms();
                        break;
                    case 2:
                        handleAddFirm();
                        break;
                    case 3:
                        handleViewByIndustry();
                        break;
                    case 4:
                        handleDeleteFirm();
                        break;
                    case 5:
                        handleEditFirm();
                        break;
                    case 6:
                        handleDeleteAll();
                        break;
                    case 0:
                        running = false;
                        System.out.println("Завершення роботи...");
                        break;
                    default:
                        System.out.println("Невірний вибір. Спробуйте ще раз.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[Помилка вводу] Будь ласка, введіть число.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=================================");
        System.out.println("    КЛІЄНТ БАЗИ ДАНИХ ФІРМ    ");
        System.out.println("=================================");
        System.out.println("1. Переглянути всі фірми");
        System.out.println("2. Додати нову фірму");
        System.out.println("3. Пошук фірм за галуззю");
        System.out.println("4. Видалити фірму (за ID)");
        System.out.println("5. Редагувати фірму (за ID)");
        System.out.println("6. Очистити БД та скинути ID");
        System.out.println("0. Вихід");
        System.out.print("Ваш вибір: ");
    }

    private static Firm getFirmDetailsFromUser() {
        System.out.print("Введіть назву фірми: ");
        String name = scanner.nextLine();

        int employees = 0;
        while (true) {
            try {
                System.out.print("Введіть кількість співробітників: ");
                String employeeInput = scanner.nextLine();
                String cleanedInput = employeeInput.replaceAll("\\s", "");
                employees = Integer.parseInt(cleanedInput);
                break;
            } catch (NumberFormatException e) {
                System.out.println("[Помилка вводу] Введіть ціле число.");
            }
        }

        double revenue = 0.0;
        while (true) {
            try {
                System.out.print("Введіть річний дохід: ");
                String revenueInput = scanner.nextLine();
                String cleanedInput = revenueInput.replaceAll("\\s", "");
                revenue = Double.parseDouble(cleanedInput);
                break;
            } catch (NumberFormatException e) {
                System.out.println("[Помилка вводу] Введіть число.");
            }
        }

        System.out.print("Введіть галузь: ");
        String industry = scanner.nextLine();

        return new Firm(name, employees, revenue, industry);
    }

    private static void handleAddFirm() {
        System.out.println("\n--- [2] Додавання нової фірми ---");
        Firm newFirm = getFirmDetailsFromUser();
        db.addFirm(newFirm);
    }

    private static void handleViewByIndustry() {
        System.out.print("Введіть назву галузі для пошуку: ");
        String industry = scanner.nextLine();
        db.viewFirmsByIndustry(industry);
    }

    private static void handleDeleteFirm() {
        System.out.println("\n--- [4] Видалення фірми ---");
        try {
            System.out.print("Введіть ID фірми, яку потрібно видалити: ");
            String idInput = scanner.nextLine();
            String cleanedInput = idInput.replaceAll("\\s", "");
            int id = Integer.parseInt(cleanedInput);
            db.deleteFirm(id);
        } catch (NumberFormatException e) {
            System.out.println("[Помилка вводу] ID має бути числом.");
        }
    }

    private static void handleEditFirm() {
        System.out.println("\n--- [5] Редагування фірми ---");
        try {
            System.out.print("Введіть ID фірми, яку потрібно редагувати: ");
            String idInput = scanner.nextLine();
            String cleanedInput = idInput.replaceAll("\\s", "");
            int id = Integer.parseInt(cleanedInput);

            System.out.println("Введіть НОВІ дані для фірми (ID=" + id + "):");
            Firm updatedFirm = getFirmDetailsFromUser();
            db.editFirm(id, updatedFirm);

        } catch (NumberFormatException e) {
            System.out.println("[Помилка вводу] ID має бути числом.");
        }
    }

    private static void handleDeleteAll() {
        System.out.println("\n--- [6] Очищення всієї БД ---");
        System.out.print("ПОПЕРЕДЖЕННЯ: Ця дія видалить ВСІ записи і скине лічильник ID. \nВи впевнені? (так/ні): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if (confirmation.equals("так")) {
            db.deleteAllAndReset();
        } else {
            System.out.println("Очищення скасовано.");
        }
    }
}