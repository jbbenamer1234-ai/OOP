package enrollmentsystem;

import java.util.Scanner;

class Student {
    private String id;
    private String name;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

class Course {
    private String code;
    private String title;
    private int capacity;

    public Course(String code, String title, int capacity) {
        this.code = code;
        this.title = title;
        this.capacity = capacity;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public int getCapacity() {
        return capacity;
    }
}

class Enrollment {
    private Student student;
    private Course course;
    private double grade;

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.grade = -1;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        if (grade >= 0 && grade <= 100) {
            this.grade = grade;
        }
    }

    public String getStatus() {
        if (grade == -1) {
            return "NOT YET GRADED";
        } else if (grade >= 75) {
            return "PASSED";
        } else {
            return "FAILED";
        }
    }
}

public class EnrollmentSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Student[] students = new Student[10];
        Course[] courses = new Course[10];
        Enrollment[] enrollments = new Enrollment[20];

        int studentCount;
        int courseCount;
        int enrollmentCount = 0;

        System.out.print("Enter number of students: ");
        studentCount = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < studentCount; i++) {

            System.out.print("Student ID: ");
            String id = sc.nextLine();

            System.out.print("Student Name: ");
            String name = sc.nextLine();

            students[i] = new Student(id, name);
        }

        System.out.print("\nEnter number of courses: ");
        courseCount = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < courseCount; i++) {

            System.out.print("Course Code: ");
            String code = sc.nextLine();

            System.out.print("Course Title: ");
            String title = sc.nextLine();

            System.out.print("Course Capacity: ");
            int capacity = sc.nextInt();
            sc.nextLine();

            courses[i] = new Course(code, title, capacity);
        }

        int choice;

        do {

            System.out.println("\n===== MENU =====");
            System.out.println("1. Enroll Student");
            System.out.println("2. Assign Grade");
            System.out.println("3. Student Report");
            System.out.println("4. Course Roster");
            System.out.println("5. System Summary");
            System.out.println("0. Exit");

            System.out.print("Choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Student ID: ");
                    String sid = sc.nextLine();

                    System.out.print("Course Code: ");
                    String ccode = sc.nextLine();

                    Student foundStudent = null;
                    Course foundCourse = null;

                    for (int i = 0; i < studentCount; i++) {
                        if (students[i].getId().equalsIgnoreCase(sid)) {
                            foundStudent = students[i];
                        }
                    }

                    for (int i = 0; i < courseCount; i++) {
                        if (courses[i].getCode().equalsIgnoreCase(ccode)) {
                            foundCourse = courses[i];
                        }
                    }

                    if (foundStudent == null || foundCourse == null) {
                        System.out.println("Student or Course not found.");
                        break;
                    }

                    boolean duplicate = false;

                    for (int i = 0; i < enrollmentCount; i++) {
                        if (enrollments[i].getStudent().getId().equalsIgnoreCase(sid)
                                && enrollments[i].getCourse().getCode().equalsIgnoreCase(ccode)) {
                            duplicate = true;
                        }
                    }

                    if (duplicate) {
                        System.out.println("Duplicate enrollment not allowed.");
                        break;
                    }

                    int enrolledStudents = 0;

                    for (int i = 0; i < enrollmentCount; i++) {
                        if (enrollments[i].getCourse().getCode().equalsIgnoreCase(ccode)) {
                            enrolledStudents++;
                        }
                    }

                    if (enrolledStudents >= foundCourse.getCapacity()) {
                        System.out.println("Course is FULL.");
                        break;
                    }

                    enrollments[enrollmentCount] =
                            new Enrollment(foundStudent, foundCourse);

                    enrollmentCount++;

                    System.out.println("Enrollment Successful!");
                    break;

                case 2:

                    System.out.print("Student ID: ");
                    sid = sc.nextLine();

                    System.out.print("Course Code: ");
                    ccode = sc.nextLine();

                    System.out.print("Grade: ");
                    double grade = sc.nextDouble();
                    sc.nextLine();

                    boolean updated = false;

                    for (int i = 0; i < enrollmentCount; i++) {

                        if (enrollments[i].getStudent().getId().equalsIgnoreCase(sid)
                                && enrollments[i].getCourse().getCode().equalsIgnoreCase(ccode)) {

                            enrollments[i].setGrade(grade);
                            updated = true;

                            System.out.println("Grade Updated.");
                        }
                    }

                    if (!updated) {
                        System.out.println("Enrollment not found.");
                    }

                    break;

                case 3:

                    System.out.print("Student ID: ");
                    sid = sc.nextLine();

                    double total = 0;
                    int graded = 0;

                    System.out.println("\n===== STUDENT REPORT =====");

                    for (int i = 0; i < enrollmentCount; i++) {

                        if (enrollments[i].getStudent().getId().equalsIgnoreCase(sid)) {

                            System.out.println(
                                    enrollments[i].getCourse().getTitle()
                                    + " | Grade: "
                                    + enrollments[i].getGrade()
                                    + " | "
                                    + enrollments[i].getStatus());

                            if (enrollments[i].getGrade() != -1) {
                                total += enrollments[i].getGrade();
                                graded++;
                            }
                        }
                    }

                    if (graded > 0) {
                        System.out.printf("Average: %.2f%n", total / graded);
                    }

                    break;

                case 4:

                    System.out.print("Course Code: ");
                    ccode = sc.nextLine();

                    System.out.println("\n===== COURSE ROSTER =====");

                    for (int i = 0; i < enrollmentCount; i++) {

                        if (enrollments[i].getCourse().getCode().equalsIgnoreCase(ccode)) {

                            System.out.println(
                                    enrollments[i].getStudent().getName()
                                    + " | Grade: "
                                    + enrollments[i].getGrade()
                                    + " | "
                                    + enrollments[i].getStatus());
                        }
                    }

                    break;

                case 5:

                    int gradedCount = 0;

                    for (int i = 0; i < enrollmentCount; i++) {
                        if (enrollments[i].getGrade() != -1) {
                            gradedCount++;
                        }
                    }

                    System.out.println("\n===== SYSTEM SUMMARY =====");
                    System.out.println("Total Students: " + studentCount);
                    System.out.println("Total Courses: " + courseCount);
                    System.out.println("Total Enrollments: " + enrollmentCount);
                    System.out.println("Graded Enrollments: " + gradedCount);

                    break;

                case 0:
                    System.out.println("Program Ended.");
                    break;

                default:
                    System.out.println("Invalid Choice.");
            }

        } while (choice != 0);

        sc.close();
    }
}
