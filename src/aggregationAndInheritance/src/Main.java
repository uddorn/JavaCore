package aggregationAndInheritance.src;

import java.util.ArrayList;

// Клас Працівник
class Employee {
    private String name;
    private String position;
    private double salary;

    // Конструктор без параметрів
    public Employee() {
        this("Невідомо", "Невідомо", 0.0);
    }

    // Конструктор із параметрами
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
}

// Клас Фірма
class Firm {
    protected String name;
    protected String address;
    protected ArrayList<Employee> employees;

    // Конструктор без параметрів
    public Firm() {
        this("Без назви", "Невідомо");
    }

    // Конструктор із параметрами
    public Firm(String name, String address) {
        this.name = name;
        this.address = address;
        this.employees = new ArrayList<>();
    }

    public void addEmployee(Employee e) {
        employees.add(e);
    }

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
}

// Клас-нащадок ITFirm
class ITFirm extends Firm {
    private String specialization;

    // Конструктор без параметрів
    public ITFirm() {
        super();
        this.specialization = "Невідомо";
    }

    // Конструктор із параметрами
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
    }

    // Додатковий метод
    public void developSoftware() {
        System.out.println(name + " розробляє програмне забезпечення у сфері " + specialization);
    }
}

public class Main {
    public static void main(String[] args) {
        Employee e1 = new Employee("Ігор", "Програміст", 30000);
        Employee e2 = new Employee("Олександр", "Тестувальник", 20000);
        Employee e3 = new Employee("Вадим", "Менеджер", 25000);

        ITFirm firm = new ITFirm("SoftWare", "Київ, пр. М. Руденка", "розробка веб-додатків");

        firm.addEmployee(e1);
        firm.addEmployee(e2);
        firm.addEmployee(e3);

        firm.showInfo();
        firm.developSoftware();
    }
}
