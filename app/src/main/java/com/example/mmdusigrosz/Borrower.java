package com.example.mmdusigrosz;

public class Borrower {

    private String name;
    private double dept;

    public Borrower(String name, double dept) {
        this.name = name;
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Dłużnik: " + name + "\nDług: " + dept;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDept() {
        return dept;
    }

    public void setDept(double dept) {
        this.dept = dept;
    }
}
