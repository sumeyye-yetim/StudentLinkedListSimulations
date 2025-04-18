package com.mycompany.project31;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TestClass1 {

    // 1. Reading file
    public static void main(String[] args) {
        String filepath = "/Users/sumeyyeyetim/Desktop/names.txt";
        File file = new File(filepath);
        StudentNode studentList = new StudentNode(null);

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String name = removeLeadingAndTrailingSpaces(scanner.nextLine());
                if (!name.isEmpty()) {
                    studentList.add(name);  // Add each name to circular linked list
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("names.txt file not found.");
            return;
        }

        // 2. Print whole list
        System.out.println("Students participating in the game:");
        studentList.display();

        // 3. Start game
        System.out.println("\nThe game begins...");
        studentList.playGame();
    }

    private static String removeLeadingAndTrailingSpaces(String str) {
        StringBuilder result = new StringBuilder();
        boolean isLeadingSpace = true;

        // Remove leading spaces
        for (char ch : str.toCharArray()) {
            if (isLeadingSpace && ch != ' ') {
                isLeadingSpace = false;
            }
            if (!isLeadingSpace) {
                result.append(ch);
            }
        }

        // Remove trailing spaces
        while (result.length() > 0 && result.charAt(result.length() - 1) == ' ') {
            result.setLength(result.length() - 1);
        }

        return result.toString();
    }
}
