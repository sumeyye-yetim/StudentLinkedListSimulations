package com.mycompany.project31;

public class LinkedList {

    Node first;
    Node last;

    public LinkedList() {
        this.first = null;
        this.last = null;
    }

    public void interestFirst(char letter) {
        Node newNode = new Node(letter);
        newNode.next = first;
        if (last == null) {
            last = newNode;
        }
        first = newNode;

    }

    public void interestLast(char letter) {
        Node newNode = new Node(letter);
        if (last == null) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
    }

    public void insertafter(char letter, Node prev) {
        Node newNode = new Node(letter);
        newNode.next = prev.next;
        prev.next = newNode;
    }

    public Node search(char x) {
        Node tmp = first;
        while (tmp != null) {
            if (tmp.letter == x) {
                return tmp;
            }
            tmp = tmp.next;
        }
        return null;
    }

    public void removeFirst() {
        if (first == null) {
            System.out.println("This is already empty");
            return;
        }
        first = first.next;
    }

    public void removeLast() {

        if (first == null) {
            System.out.println("This list is already empty");
            return;
        }

        // If there is only one element
        if (first.next == null) {
            first = null;
            last = null;
            return;
        }

        Node tmp = first;
        Node prev = null;

        // Go to the end of the list
        while (tmp.next != null) {
            prev = tmp;
            tmp = tmp.next;
        }

        prev.next = null;
        last = prev;
    }

}
