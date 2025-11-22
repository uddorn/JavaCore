import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

public class Firm implements Serializable {

    private static final long serialVersionUID = 1L;

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
    public int getEmployeeCount() { return employeeCount; }
    public double getAnnualRevenue() { return annualRevenue; }
    public String getIndustry() { return industry; }
    public void setName(String name) { this.name = name; }
    public void setEmployeeCount(int employeeCount) { this.employeeCount = employeeCount; }
    public void setAnnualRevenue(double annualRevenue) { this.annualRevenue = annualRevenue; }
    public void setIndustry(String industry) { this.industry = industry; }

    @Override
    public String toString() {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        String formattedRevenue = currencyFormatter.format(annualRevenue);

        return "Фірма [Назва: " + name +
                ", Співробітники: " + employeeCount +
                ", Дохід: " + formattedRevenue +
                ", Галузь: " + industry + "]";
    }
}