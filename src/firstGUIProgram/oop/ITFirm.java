package firstGUIProgram.oop;

public class ITFirm extends Firm {
    private String specialization;

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
        System.out.println("Бонус фірми: " + calculateBonus()); // Виклик методу з інтерфейсу
    }

    public void developSoftware() {
        System.out.println(name + " розробляє програмне забезпечення у сфері " + specialization);
    }
}