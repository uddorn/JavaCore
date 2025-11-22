public class Firm {
    private String name;
    private int employeeCount;
    private double annualRevenue;
    private String industry;

    public Firm(String name, int employeeCount, double annualRevenue, String industry) {
        this.name = name;
        this.employeeCount = employeeCount;
        this.annualRevenue = annualRevenue;
        this.industry = industry;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getEmployeeCount() { return employeeCount; }
    public void setEmployeeCount(int employeeCount) { this.employeeCount = employeeCount; }
    public double getAnnualRevenue() { return annualRevenue; }
    public void setAnnualRevenue(double annualRevenue) { this.annualRevenue = annualRevenue; }
    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }

    @Override
    public String toString() {
        return "Фірма [Назва: " + String.format("%-20s", name) +
                " | Співробітники: " + String.format("%-5d", employeeCount) +
                " | Дохід: " + String.format("%-6.1f", annualRevenue) + " млн" +
                " | Галузь: " + industry + "]";
    }
}