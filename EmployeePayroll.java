package employeepayroll;

import java.util.Scanner;

class Employee {

    private String id;
    private String name;
    private double hoursWorked;
    private double hourlyRate;

    private static int employeeCount = 0;

    public Employee(String id, String name, double hoursWorked, double hourlyRate) {
        this.id = id;
        this.name = name;
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
        employeeCount++;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getGrossPay() {

        if (hoursWorked <= 40) {
            return hoursWorked * hourlyRate;
        } else {
            double overtime = hoursWorked - 40;
            return (40 * hourlyRate) + (overtime * hourlyRate * 1.5);
        }
    }

    public double getTax() {

        double gross = getGrossPay();

        if (gross <= 10000) {
            return gross * 0.05;
        } else if (gross <= 20000) {
            return gross * 0.10;
        } else {
            return gross * 0.15;
        }
    }

    public double getNetPay() {
        return getGrossPay() - getTax();
    }

    public static int getEmployeeCount() {
        return employeeCount;
    }
}

public class EmployeePayroll {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of employees (1-10): ");
        int n = sc.nextInt();
        sc.nextLine();

        Employee[] employees = new Employee[n];

        for (int i = 0; i < n; i++) {

            System.out.println("\nEmployee " + (i + 1));

            System.out.print("Employee ID: ");
            String id = sc.nextLine();

            System.out.print("Employee Name: ");
            String name = sc.nextLine();

            double hours;

            do {
                System.out.print("Hours Worked: ");
                hours = sc.nextDouble();

                if (hours < 0) {
                    System.out.println("Hours cannot be negative.");
                }

            } while (hours < 0);

            double rate;

            do {
                System.out.print("Hourly Rate: ");
                rate = sc.nextDouble();

                if (rate < 0) {
                    System.out.println("Rate cannot be negative.");
                }

            } while (rate < 0);

            sc.nextLine();

            employees[i] = new Employee(id, name, hours, rate);
        }

        System.out.println("\n===== PAYROLL SUMMARY =====");

        double totalNetPay = 0;

        Employee highestEmployee = employees[0];
        double highestNetPay = employees[0].getNetPay();

        for (int i = 0; i < n; i++) {

            double gross = employees[i].getGrossPay();
            double tax = employees[i].getTax();
            double net = employees[i].getNetPay();

            System.out.printf(
                    "%s | %s | Gross: %.2f | Tax: %.2f | Net: %.2f%n",
                    employees[i].getId(),
                    employees[i].getName(),
                    gross,
                    tax,
                    net
            );

            totalNetPay += net;

            if (net > highestNetPay) {
                highestNetPay = net;
                highestEmployee = employees[i];
            }
        }

        double averageNetPay = totalNetPay / n;

        System.out.println("\nHighest Net Pay:");
        System.out.printf("%s - %.2f%n",
                highestEmployee.getName(),
                highestNetPay);

        System.out.printf("Average Net Pay: %.2f%n",
                averageNetPay);

        System.out.println("Employee Objects Created: "
                + Employee.getEmployeeCount());

        sc.close();
    }
}
