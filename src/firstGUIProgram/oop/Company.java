package firstGUIProgram.oop;

public abstract class Company {
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