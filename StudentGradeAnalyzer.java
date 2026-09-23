package studentgradeanalyzer;

import java.util.Scanner;

class Student {

    private String id;
    private String name;
    private int[] scores;

    private static int studentCount = 0;

    public Student(String id, String name, int[] scores) {
        this.id = id;
        this.name = name;
        this.scores = scores;
        studentCount++;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAverage() {
        int total = 0;

        for (int i = 0; i < scores.length; i++) {
            total += scores[i];
        }

        return total / 3.0;
    }

    public String getStatus() {
        if (getAverage() >= 75) {
            return "PASSED";
        } else {
            return "FAILED";
        }
    }

    public static int getStudentCount() {
        return studentCount;
    }
}

public class StudentGradeAnalyzer {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of students (1-10): ");
        int n = sc.nextInt();
        sc.nextLine();

        Student[] students = new Student[n];

        for (int i = 0; i < n; i++) {

            System.out.println("\nStudent " + (i + 1));

            System.out.print("Student ID: ");
            String id = sc.nextLine();

            System.out.print("Full Name: ");
            String name = sc.nextLine();

            int[] scores = new int[3];

            for (int j = 0; j < 3; j++) {

                do {
                    System.out.print("Score " + (j + 1) + ": ");
                    scores[j] = sc.nextInt();

                    if (scores[j] < 0 || scores[j] > 100) {
                        System.out.println("Invalid score. Enter 0-100 only.");
                    }

                } while (scores[j] < 0 || scores[j] > 100);
            }

            sc.nextLine();

            students[i] = new Student(id, name, scores);
        }

        System.out.println("\n===== STUDENT SUMMARY =====");

        double highestAverage = students[0].getAverage();
        Student topStudent = students[0];

        for (int i = 0; i < n; i++) {

            double average = students[i].getAverage();

            System.out.printf(
                    "%s | %s | Average: %.2f | %s\n",
                    students[i].getId(),
                    students[i].getName(),
                    average,
                    students[i].getStatus()
            );

            if (average > highestAverage) {
                highestAverage = average;
                topStudent = students[i];
            }
        }

        System.out.println("\nHighest Average Student:");
        System.out.printf("%s - %.2f\n",
                topStudent.getName(),
                highestAverage);

        System.out.println("\nTotal Student Objects Created: "
                + Student.getStudentCount());

        sc.close();
    }
}
