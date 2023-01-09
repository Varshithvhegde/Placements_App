package com.varshith.placements;

public class Model {
    private String Name;
    private String Company;
    private double Salary;

    public Model(String name, double salary, String company) {
        this.Name = name;
        this.Salary = salary;
        this.Company = company;
    }

    public String getName() {
        return Name;
    }

    public double getSalary() {
        return Salary;
    }

    public String getCompany() {
        return Company;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setSalary(int salary) {
        this.Salary = salary;
    }
    public void setCompany(String company) {
        this.Company = company;
    }
}