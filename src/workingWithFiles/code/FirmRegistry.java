package workingWithFiles.code;

import java.util.ArrayList;
import java.util.Comparator;
import java.io.*;

public class FirmRegistry {

    private ArrayList<Firm> firms;

    public FirmRegistry() {
        this.firms = new ArrayList<>();
    }

    public void readFromTextFile(String filename) {
        this.firms.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");

                if (parts.length == 4) {
                    try {
                        String name = parts[0];
                        int employees = Integer.parseInt(parts[1].trim());
                        double revenue = Double.parseDouble(parts[2].trim());
                        String industry = parts[3];

                        this.firms.add(new Firm(name, employees, revenue, industry));

                    } catch (NumberFormatException e) {
                        System.out.println("[Помилка парсингу] Невірний формат числа у рядку: " + line);
                    }
                }
            }
            System.out.println("\n[Успіх] Дані успішно зчитано з " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("[Помилка] Файл не знайдено: " + filename);
        } catch (IOException e) {
            System.out.println("[Помилка] Помилка читання файлу: " + e.getMessage());
        }
    }

    public void writeToBinaryFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this.firms);
            System.out.println("\n[Успіх] Дані успішно записано у бінарний файл " + filename);
        } catch (IOException e) {
            System.out.println("[Помилка] Помилка запису у файл: " + e.getMessage());
        }
    }

    public void readFromBinaryFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            this.firms = (ArrayList<Firm>) ois.readObject();
            System.out.println("\n[Успіх] Дані успішно зчитано з бінарного файлу " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("[Помилка] Файл не знайдено: " + filename);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[Помилка] Помилка зчитування або клас не знайдено: " + e.getMessage());
        } catch (ClassCastException e) {
            System.out.println("[Помилка] Невірний тип даних у файлі.");
        }
    }

    public void displayAllFirms() {
        if (firms.isEmpty()) {
            System.out.println("Список фірм порожній.");
            return;
        }
        for (Firm firm : firms) {
            System.out.println(firm);
        }
    }

    public void modifyFirm(int index, String newName, int newEmployees, double newRevenue, String newIndustry) {
        if (index >= 0 && index < firms.size()) {
            Firm firmToUpdate = firms.get(index);
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
            firms.sort(Comparator.comparingInt(Firm::getEmployeeCount));
            System.out.println("\n## Масив відсортовано за зростанням кількості співробітників ##");
        } else {
            firms.sort(Comparator.comparingInt(Firm::getEmployeeCount).reversed());
            System.out.println("\n## Масив відсортовано за спаданням кількості співробітників ##");
        }
        displayAllFirms();
    }
}