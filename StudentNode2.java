package com.mycompany.project31;

import java.util.List;
import java.util.ArrayList;

public class StudentNode2 {

    String name;
    StudentNode2 prev;
    StudentNode2 next;
    boolean visited;
    int commonLetterCount;
    List<String> visitedMessages;

    public StudentNode2(String name) {
        this.name = name;
        this.prev = null;
        this.next = null;
        this.visited = false;
        this.commonLetterCount = 0;
        this.visitedMessages = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getCommonLetterCount() {
        return commonLetterCount;
    }

    public void setCommonLetterCount(int count) {
        this.commonLetterCount = count;
    }

}
