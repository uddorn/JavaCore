package Strings.code;

import java.util.Arrays;
import java.util.Comparator;

public class FirmRegistry {
    private Firm[] firms;

    public FirmRegistry() {
        this.firms = new Firm[10];
        firms[0] = new Firm("TechCorp", 500, 120.5, "IT");
        firms[1] = new Firm("BuildIt", 120, 30.2, "Будівництво");
        firms[2] = new Firm("Foodies", 350, 75.0, "Харчова промисловість");
        firms[3] = new Firm("EcoFuel", 80, 45.8, "Енергетика");
        firms[4] = new Firm("Innovate Solutions", 1200, 300.0, "IT");
        firms[5] = new Firm("HealthFirst", 450, 110.3, "Медицина");
        firms[6] = new Firm("AutoDrive", 700, 210.0, "Автомобілі");
        firms[7] = new Firm("GlobalLogistics", 220, 88.0, "Логістика");
        firms[8] = new Firm("MediaHub", 150, 25.5, "Медіа");
        firms[9] = new Firm("GreenFarm", 90, 15.0, "Агро");
    }

    public void displayAllFirms() {
        for (Firm firm : firms) {
            System.out.println(firm);
        }
    }

    public void modifyFirm(int index, String newName, int newEmployees, double newRevenue, String newIndustry) {
        if (index >= 0 && index < firms.length) {
            firms[index].setName(newName);
            firms[index].setEmployeeCount(newEmployees);
            firms[index].setAnnualRevenue(newRevenue);
            firms[index].setIndustry(newIndustry);
            System.out.println("\n[Успіх] Фірму оновлено.");
        } else {
            System.out.println("\n[Помилка] Невірний індекс.");
        }
    }

    public void displayFirmsWithMoreThan(int minEmployees) {
        System.out.println("\nФірми з кількістю співробітників > " + minEmployees + "");
        for (Firm firm : firms) {
            if (firm.getEmployeeCount() > minEmployees) {
                System.out.println(firm);
            }
        }
    }

    public void sortFirmsByEmployeeCount(boolean ascending) {
        if (ascending) {
            Arrays.sort(firms, Comparator.comparingInt(Firm::getEmployeeCount));
            System.out.println("\nВідсортовано за зростанням співробітників");
        } else {
            Arrays.sort(firms, Comparator.comparingInt(Firm::getEmployeeCount).reversed());
            System.out.println("\nВідсортовано за спаданням співробітників");
        }
        displayAllFirms();
    }

    public void sortFirmsByName(boolean ascending) {
        if (ascending) {
            Arrays.sort(firms, (f1, f2) -> f1.getName().compareToIgnoreCase(f2.getName()));
        } else {
            Arrays.sort(firms, (f1, f2) -> f2.getName().compareToIgnoreCase(f1.getName()));
        }
        displayAllFirms();
    }

    public void displayFirmsByNameRegex(String regex) {
        System.out.println("\nФільтрація за Regex [" + regex + "]");
        boolean found = false;
        for (Firm firm : firms) {
            if (firm.getName().matches(regex)) {
                System.out.println(firm);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Фірм за заданим критерієм не знайдено.");
        }
    }

    public void displayMinMaxRevenue() {
        if (firms == null || firms.length == 0) return;

        Firm minRevenueFirm = firms[0];
        Firm maxRevenueFirm = firms[0];

        for (int i = 1; i < firms.length; i++) {
            if (firms[i].getAnnualRevenue() < minRevenueFirm.getAnnualRevenue()) {
                minRevenueFirm = firms[i];
            }
            if (firms[i].getAnnualRevenue() > maxRevenueFirm.getAnnualRevenue()) {
                maxRevenueFirm = firms[i];
            }
        }

        System.out.println("Статистика доходів");
        System.out.println("Найменший дохід " + minRevenueFirm.getAnnualRevenue() +
                " млн має фірма " + minRevenueFirm.getName() +
                ", найбільший — " + maxRevenueFirm.getAnnualRevenue() +
                " млн — " + maxRevenueFirm.getName());
    }
}