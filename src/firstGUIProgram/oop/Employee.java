package firstGUIProgram.oop;

public class Employee implements Payable {
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

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public void showInfo() {
        System.out.println("Працівник: " + name + ", посада: " + position + ", зарплата: " + salary);
    }

    @Override
    public double calculateBonus() {
        return salary * 0.1;
    }
}