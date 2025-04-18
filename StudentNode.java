package com.mycompany.project31;

import java.util.Random;
import java.util.Scanner;

public class StudentNode {

    String name;
    LinkedList nameList;
    StudentNode next;
    StudentNode first;
    int size;
    String originalName;

    public StudentNode(String name) {
        this.name = name;
        this.nameList = new LinkedList();
        this.originalName = name;

        if (name != null) {

            for (int i = 0; i < name.length(); i++) {
                char c = name.charAt(i);
                nameList.interestLast(c); // Add each letter to linked list
            }
        }

        this.next = null;
    }

    public void add(String name) {
        StudentNode newNode = new StudentNode(name);
        if (first == null) {
            first = newNode;
            first.next = first;
        } else {
            StudentNode temp = first;
            while (temp.next != first) {
                temp = temp.next;
            }
            temp.next = newNode;
            newNode.next = first; // Make new node circular
        }
        size++;
    }

    public void removeNode(StudentNode nodeToRemove) {

        if (nodeToRemove == null || first == null) {
            return;
        }

        if (first == nodeToRemove && first.next == first) {
            first = null; // List becomes empty
        } else if (first == nodeToRemove) {
            StudentNode temp = first;
            // Find the last node
            while (temp.next != first) {
                temp = temp.next; // Traverse to the last node
            }
            temp.next = first.next; // Update the last node's next to the second node
            first = first.next; // Move the first pointer to the next node
        } else {
            StudentNode current = first;
            // Traverse the list to find the node before the one to remove
            while (current.next != first && current.next != nodeToRemove) {
                current = current.next;
            }
            // If we found the node to remove
            if (current.next == nodeToRemove) {
                current.next = nodeToRemove.next; // Bypass the node to remove
            }
        }
        size--;

    }

    public String getNameFromList(StudentNode node) {
        Node currentChar = node.nameList.first;
        String name = "";

        while (currentChar != null) {
            name += currentChar.letter; // Add character
            currentChar = currentChar.next;
        }

        return name;

    }

    public void removeLastCharacter(StudentNode node) {

        if (node.nameList.first == null) {
            System.out.println("Name list is already empty");
            return;
        }

        // If there is only one character
        if (node.nameList.first.next == null) {
            node.nameList.first = null;
            node.nameList.last = null;
            return;
        }

        Node tmp = node.nameList.first;
        Node prev = null;

        // Go to the end of the list
        while (tmp.next != null) {
            prev = tmp;
            tmp = tmp.next;
        }

        prev.next = null;
        node.nameList.last = prev; // Update last character

    }

    public void display() {
        if (first == null) {
            return;
        }

        StudentNode temp = first;

        do {
            System.out.print(getNameFromList(temp) + " -> ");
            temp = temp.next;
        } while (temp != first);

        System.out.println();

    }

    public void playGame() {
        while (size > 1) {
            int k = (int) (Math.random() * size) + 1;
            System.out.println("The chosen k: " + k);
            StudentNode current = first;

            for (int i = 0; i < k; i++) {
                current = current.next;
            }

            System.out.println("The selected student: " + getNameFromList(current));
            removeLastCharacter(current);
            System.out.println(getNameFromList(current) + " <3");

            if (getNameFromList(current).length() == 0) {
                removeNode(current);
            }

            display();
        }

        System.out.println("Winner: " + first.originalName);
    }
}
