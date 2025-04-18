package com.mycompany.project31;

import java.util.Scanner;

public class TestClass2 {

    public static void main(String[] args) {

        StudentList2 studentList = new StudentList2();
        String filePath = "/Users/sumeyyeyetim/Desktop/names.txt";
        studentList.loadStudentFromFile(filePath);

        Scanner scanner = new Scanner(System.in);

        studentList.randomHubStudentSelector();

        System.out.print("Enter the first message: ");
        String message = scanner.nextLine();

        studentList.playGame2(message);

        System.out.println("\nDisplaying final results:");
        studentList.displayResults();

        scanner.close();
    }

}
