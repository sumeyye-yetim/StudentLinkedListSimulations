package com.mycompany.project31;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class StudentList2 {

    private String message;
    StudentNode2 first;
    StudentNode2 last;
    List<StudentNode2> hubStudents = new ArrayList<>();
    List<String> studentList = new ArrayList<>();

    public void addStudent(String name) {
        StudentNode2 newStudent = new StudentNode2(name);
        if (first == null) {
            first = last = newStudent;
        } else {
            last.next = newStudent;
            newStudent.prev = last;
            last = newStudent;
        }
        studentList.add(name);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void loadStudentFromFile(String filePath) {
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                line = removeLeadingAndTrailingSpaces(line);
                if (!line.isEmpty()) {
                    addStudent(line);  // Add student to list
                }
            }

            bufferedReader.close();
            fileReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("names.txt file not found.");
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }
    }

    private String removeLeadingAndTrailingSpaces(String str) {
        // Removes leading and trailing spaces from a string

        StringBuilder result = new StringBuilder();
        boolean isLeadingSpace = true;

        for (char ch : str.toCharArray()) {
            if (isLeadingSpace && ch != ' ') {
                isLeadingSpace = false;
            }
            if (!isLeadingSpace || ch != ' ') {
                result.append(ch);
            }
        }

        while (result.length() > 0 && result.charAt(result.length() - 1) == ' ') {
            result.setLength(result.length() - 1);
        }

        return result.toString();
    }

    public void randomHubStudentSelector() {
        if (studentList.isEmpty()) {
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of hubs: ");
        int numberOfStudentsToSelect = scanner.nextInt();

        if (numberOfStudentsToSelect > studentList.size() || numberOfStudentsToSelect <= 0) {
            System.out.println("Invalid entry! Please enter a number between 1 and " + studentList.size());
            return;
        }

        boolean[] isSelected = new boolean[studentList.size()];
        List<String> selectedStudents = new ArrayList<>();
        Random random = new Random();

        while (selectedStudents.size() < numberOfStudentsToSelect) {
            int randomIndex = random.nextInt(studentList.size());

            if (!isSelected[randomIndex]) {
                selectedStudents.add(studentList.get(randomIndex));
                isSelected[randomIndex] = true;
            }

        }

        for (int i = 0; i < selectedStudents.size(); i++) {
            String studentName = selectedStudents.get(i);
            hubStudents.add(new StudentNode2(studentName));
        }
    }

    public void playGame2(String message) {
        Scanner scanner = new Scanner(System.in);

        if (studentList.isEmpty()) {
            System.out.println("Student list is empty! Game cannot be started.");
            return;
        }

        // Random direction
        Random random = new Random();
        boolean directionRight = random.nextBoolean();
        String direction;

        if (directionRight) {
            direction = "Forward"; // ->
        } else {
            direction = "Back"; // <-
        }
        System.out.println("Randomly generating a direction: " + direction);

        int startIndex = random.nextInt(studentList.size());
        StudentNode2 current = first;
        int currentIndex = 0;

        while (currentIndex < startIndex) {
            current = current.next;
            currentIndex++;
        }

        System.out.println("Randomly choosing a student: " + current.name);
        int k = random.nextInt(5) + 1;
        System.out.println("Randomly generating the value of k: " + k);

        String lastHubStudentName = "";

        while (!allStudentsVisited()) {
            current.visited = true;
            calculateCommonLetterCountForAllStudents(message);
            displayResults();

            boolean isHubStudent = false;
            for (StudentNode2 hub : hubStudents) {
                if (hub.name.equals(current.name)) {
                    isHubStudent = true;
                    break;
                }
            }

            if (isHubStudent) {
                if (!current.name.equals(lastHubStudentName)) {
                    System.out.println("We have now reached a hub student, " + current.name + "!");
                    System.out.print("Enter a new message: ");
                    String newMessage = scanner.nextLine();

                    message = mergeMessages(message, newMessage);
                    calculateCommonLetterCountForAllStudents(message);

                    lastHubStudentName = current.name;
                    directionRight = !directionRight; // Change direction
                    if (directionRight) {
                        direction = "Forward"; // ->
                    } else {
                        direction = "Back"; // <-
                    }
                    k = random.nextInt(5) + 1;
                    System.out.println("Randomly generating the value of k: " + k);
                }
            }

            // Transfer Messages
            boolean canContinue = true;
            for (int i = 0; i < k && canContinue; i++) {
                if (directionRight) {
                    if (canMove(current, directionRight)) {
                        current = current.next; // Forward
                    } else {
                        directionRight = false;
                        canContinue = false;
                    }
                } else {
                    if (canMove(current, directionRight)) {
                        current = current.prev; // Back
                    } else {
                        directionRight = true;
                        canContinue = false;
                    }
                }
            }
        }

        displayResults();
        scanner.close();
    }

    private String mergeMessages(String oldMessage, String newMessage) {
        // To combine messages
        return oldMessage + " " + newMessage;
    }

    public void calculateCommonLetterCountForAllStudents(String newMessage) {
        StudentNode2 current = first;

        while (current != null) {
            String studentName = current.name;
            current.commonLetterCount = 0;

            for (int i = 0; i < studentName.length(); i++) {
                char studentChar = studentName.charAt(i);

                // If the letter in the student name is in the new message, update the total
                if (countCharInMessage(studentChar, newMessage) > 0) {
                    boolean alreadyCounted = false;
                    for (int j = 0; j < i; j++) {
                        if (studentName.charAt(j) == studentChar) {
                            alreadyCounted = true;
                            break;
                        }
                    }
                    if (!alreadyCounted) {
                        int countInNewMessage = countCharInMessage(studentChar, newMessage); // Number of this letter in new message
                        current.commonLetterCount += countInNewMessage;
                    }
                }
            }

            current = current.next;
        }
    }

    private int countCharInMessage(char ch, String message) {
        // Counts the number of times a given character occurs in a message

        int count = 0;
        if (message == null || message.isEmpty()) {
            return count;
        }

        for (int j = 0; j < message.length(); j++) {
            if (message.charAt(j) == ch) {
                count++;
            }
        }
        return count;
    }

    public boolean canMove(StudentNode2 current, boolean directionRight) {

        if (directionRight) {
            return current.next != null;
        } else {
            return current.prev != null;
        }
    }

    public boolean allStudentsVisited() {
        StudentNode2 current = first;
        while (current != null) {
            if (!current.visited) {
                return false;
            }
            current = current.next;
        }
        return true;
    }

    public void displayResults() {
        StudentNode2 current = first;

        while (current != null) {
            boolean isHub = false;
            for (StudentNode2 hub : hubStudents) {
                if (hub.name.equals(current.name)) {
                    isHub = true;
                    break;
                }
            }

            String hubIndicator = "";
            if (isHub) {
                hubIndicator = "*"; // If hub student
            }

            System.out.print(current.getName() + " " + current.commonLetterCount + hubIndicator + " ↔ ");
            current = current.next;
        }
        System.out.println();
    }
}
