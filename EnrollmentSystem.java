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

    public Course(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
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
        this.grade = grade;
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

        int studentCount = 0;
        int courseCount = 0;
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

            courses[i] = new Course(code, title);
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

                    if (foundStudent != null && foundCourse != null) {

                        boolean duplicate = false;

                        for (int i = 0; i < enrollmentCount; i++) {
                            if (enrollments[i].getStudent().getId().equalsIgnoreCase(sid)
                                    && enrollments[i].getCourse().getCode().equalsIgnoreCase(ccode)) {

                                duplicate = true;
                            }
                        }

                        if (duplicate) {
                            System.out.println("Duplicate enrollment not allowed.");
                        } else {
                            enrollments[enrollmentCount] =
                                    new Enrollment(foundStudent, foundCourse);

                            enrollmentCount++;

                            System.out.println("Enrollment successful!");
                        }

                    } else {
                        System.out.println("Student or Course not found.");
                    }

                    break;

                case 2:

                    System.out.print("Student ID: ");
                    sid = sc.nextLine();

                    System.out.print("Course Code: ");
                    ccode = sc.nextLine();

                    System.out.print("Grade: ");
                    double grade = sc.nextDouble();
                    sc.nextLine();

                    for (int i = 0; i < enrollmentCount; i++) {

                        if (enrollments[i].getStudent().getId().equalsIgnoreCase(sid)
                                && enrollments[i].getCourse().getCode().equalsIgnoreCase(ccode)) {

                            enrollments[i].setGrade(grade);
                            System.out.println("Grade updated.");
                        }
                    }

                    break;

                case 3:

                    System.out.print("Student ID: ");
                    sid = sc.nextLine();

                    double total = 0;
                    int count = 0;

                    System.out.println("\nSTUDENT REPORT");

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
                                count++;
                            }
                        }
                    }

                    if (count > 0) {
                        System.out.println("Average: " + (total / count));
                    }

                    break;

                case 4:

                    System.out.print("Course Code: ");
                    ccode = sc.nextLine();

                    System.out.println("\nCOURSE ROSTER");

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

                    int graded = 0;

                    for (int i = 0; i < enrollmentCount; i++) {
                        if (enrollments[i].getGrade() != -1) {
                            graded++;
                        }
                    }

                    System.out.println("\nSYSTEM SUMMARY");
                    System.out.println("Total Students: " + studentCount);
                    System.out.println("Total Courses: " + courseCount);
                    System.out.println("Total Enrollments: " + enrollmentCount);
                    System.out.println("Graded Enrollments: " + graded);

                    break;

                case 0:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);

        sc.close();
    }
}