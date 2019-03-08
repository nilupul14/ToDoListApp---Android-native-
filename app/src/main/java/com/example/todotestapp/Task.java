package com.example.todotestapp;

public class Task {

    private String taskName;
    private String taskPriority;

    public Task() {

    }

    public Task(String s, String s1) {
        this.taskName = s;
        this.taskPriority = s1;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }
}
