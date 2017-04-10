/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.shreysharma.crud.gradebook.serv.model;

/**
 *
 * @author shreyas
 */
public class Tasks {
    private int        Marks;
 private String     TaskType;
    private String     Feedback;

    public void setMarks(int Marks) {
        this.Marks = Marks;
    }

    public void setTaskType(String TaskType) {
        this.TaskType = TaskType;
    }

    public void setFeedback(String Feedback) {
        this.Feedback = Feedback;
    }

    public int getMarks() {
        return Marks;
    }

    public String getTaskType() {
        return TaskType;
    }

    public String getFeedback() {
        return Feedback;
    }
   
    
}
