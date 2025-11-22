package org.example;

import java.io.Serializable;

public class Firm implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private int employeeCount;
    private double annualRevenue;
    private String industry;

    public Firm(int id, String name, int employeeCount, double annualRevenue, String industry) {
        this.id = id;
        this.name = name;
        this.employeeCount = employeeCount;
        this.annualRevenue = annualRevenue;
        this.industry = industry;
    }

    // Конструктор для нових об'єктів (без ID)
    public Firm(String name, int employeeCount, double annualRevenue, String industry) {
        this(0, name, employeeCount, annualRevenue, industry);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

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
        return name + " (" + industry + ")";
    }
}