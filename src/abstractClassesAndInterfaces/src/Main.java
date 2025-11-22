package abstractClassesAndInterfaces.src;

import java.util.ArrayList;

// Інтерфейс "Бонус"
interface Payable {
    double calculateBonus();
}

// Абстрактний клас "Компанія"
abstract class Company {
    protected String name;
    protected String address;

    public Company(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public abstract void showInfo();

    public void companyInfo() {
        System.out.println("Назва компанії: " + name);
        System.out.println("Адреса: " + address);
    }
}

// Клас "Працівник"
class Employee implements Payable {
    private String name;
    private String position;
    private double salary;

    public Employee() {
        this("Невідомо", "Невідомо", 0.0);
    }

    public Employee(String name, String position, double salary) {
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    public void showInfo() {
        System.out.println("Працівник: " + name + ", посада: " + position + ", зарплата: " + salary);
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public double calculateBonus() {
        return salary * 0.1; // 10% бонус
    }
}

// Клас "Фірма"
class Firm extends Company implements Payable {
    protected ArrayList<Employee> employees;

    public Firm() {
        this("Без назви", "Невідомо");
    }

    public Firm(String name, String address) {
        super(name, address);
        employees = new ArrayList<>();
    }

    public void addEmployee(Employee e) {
        employees.add(e);
    }

    @Override
    public void showInfo() {
        System.out.println("Фірма: " + name + ", адреса: " + address);
        System.out.println("Працівники:");
        for (Employee e : employees) {
            e.showInfo();
        }
    }

    public double totalSalary() {
        double sum = 0;
        for (Employee e : employees) {
            sum += e.getSalary();
        }
        return sum;
    }

    @Override
    public double calculateBonus() {
        return totalSalary() * 0.05; // 5% бонус для всієї фірми
    }
}

// "ITFirm" успадковує "Firm"
class ITFirm extends Firm {
    private String specialization;

    public ITFirm() {
        super();
        this.specialization = "Невідомо";
    }

    public ITFirm(String name, String address, String specialization) {
        super(name, address);
        this.specialization = specialization;
    }

    @Override
    public void showInfo() {
        System.out.println("IT-Фірма: " + name + " (спеціалізація: " + specialization + ")");
        System.out.println("Адреса: " + address);
        System.out.println("Працівники:");
        for (Employee e : employees) {
            e.showInfo();
        }
        System.out.println("Загальна зарплата: " + totalSalary());
        System.out.println("Бонус фірми: " + calculateBonus());
    }

    public void developSoftware() {
        System.out.println(name + " розробляє програмне забезпечення у сфері " + specialization);
    }
}

// Головний клас
public class Main {
    public static void main(String[] args) {
        Employee e1 = new Employee("Ігор", "Програміст", 30000);
        Employee e2 = new Employee("Олександр", "Тестувальник", 20000);
        Employee e3 = new Employee("Вадим", "Менеджер", 25000);

        ITFirm firm = new ITFirm("SoftWare", "Київ, пр. М. Руденка", "розробка веб-додатків");

        Payable p = new Employee();
        firm.addEmployee(e1);
        firm.addEmployee(e2);
        firm.addEmployee(e3);

        firm.companyInfo();
        firm.showInfo();
        firm.developSoftware();
    }
}
