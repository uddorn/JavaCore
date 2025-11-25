package firstGUIProgram.oop;

import java.util.ArrayList;

public class Firm extends Company implements Payable {
    protected ArrayList<Employee> employees;

    public Firm(String name, String address) {
        super(name, address);
        this.employees = new ArrayList<>();
    }

    public void addEmployee(Employee e) {
        employees.add(e);
    }

    public double totalSalary() {
        double sum = 0;
        for (Employee e : employees) {
            sum += e.getSalary();
        }
        return sum;
    }

    /**
     * [cite_start]Реалізація абстрактного методу showInfo з класу Company. [cite: 183, 196]
     */
    @Override
    public void showInfo() {
        System.out.println("Фірма: " + name + ", адреса: " + address);
        System.out.println("Працівники:");
        for (Employee e : employees) {
            e.showInfo();
        }
    }

    @Override
    public double calculateBonus() {
        return totalSalary() * 0.05;
    }
}