package com.project.misterauto.model;

import java.io.Serializable;

public class taches implements Serializable {

    private String title;
    private String completed;
    private int id;

    public taches()
    {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
