package Arrays.code;

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
        firms[6] = new Firm("AutoDrive", 600, 210.0, "Автомобілі");
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
            Firm firmToUpdate = firms[index];
            firmToUpdate.setName(newName);
            firmToUpdate.setEmployeeCount(newEmployees);
            firmToUpdate.setAnnualRevenue(newRevenue);
            firmToUpdate.setIndustry(newIndustry);
            System.out.println("\n[Успіх] Фірму за індексом " + index + " оновлено.");
        } else {
            System.out.println("\n[Помилка] Невірний індекс: " + index);
        }
    }

    public void displayFirmsWithMoreThan(int minEmployees) {
        System.out.println("\n## Фірми з кількістю співробітників > " + minEmployees + " ##");
        boolean found = false;
        for (Firm firm : firms) {
            if (firm.getEmployeeCount() > minEmployees) {
                System.out.println(firm);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Фірм, що відповідають умові, не знайдено.");
        }
    }

    public void sortFirmsByEmployeeCount(boolean ascending) {
        if (ascending) {
            // Сортування за зростанням
            Arrays.sort(firms, Comparator.comparingInt(Firm::getEmployeeCount));
            System.out.println("\n## Масив відсортовано за зростанням кількості співробітників ##");
        } else {
            // Сортування за спаданням
            Arrays.sort(firms, Comparator.comparingInt(Firm::getEmployeeCount).reversed());
            System.out.println("\n## Масив відсортовано за спаданням кількості співробітників ##");
        }

        displayAllFirms();
    }
}