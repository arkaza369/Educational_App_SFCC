package com.example.android.sfcc.model;

import java.util.ArrayList;
import java.util.List;

public class Test {
    private String title;
    private List<MCQ> mcqes;

    public Test(String title, List<MCQ> mcqes) {
        this.title = title;
        this.mcqes = mcqes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MCQ> getMcqes() {
        return mcqes;
    }

    public void setMcqes(List<MCQ> mcqes) {
        this.mcqes = mcqes;
    }
}
