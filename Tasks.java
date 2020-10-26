package com.example.taskactivity;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Tasks {
    private ArrayList<String> headings;
    public Tasks(ArrayList<String>headings) {
        this.headings=headings;
    }
    public ArrayList<String> add(String text) {
        headings.add(text);
        return headings;
    }

}
