/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shreysharma.github.crud.jaxb.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement
@XmlType(propOrder={
    "id",
    "finalgrade",
    "priority",
    "assignMark",
    "assignFeed",
    "midtermMark",
    "midtermFeed",
    "quizMark",
    "quizFeed",
    "inclasslabsMark",
    "inclasslabsFeed",
    "finalMark",
    "finalFeed"})

public class Gradebook {
    
    private static final Logger LOG = LoggerFactory.getLogger(Gradebook.class);
    
    private int        id;
    private String      finalgrade;
    private int         priority;
    
  //  private int        assignment; //check
    
    private int        assignMark;
    private String     assignFeed;
    private int        midtermMark;
    private String     midtermFeed;
    private int        quizMark;
    private String     quizFeed;
    private int        inclasslabsMark;
    private String     inclasslabsFeed;
    private int        finalMark;
    private String     finalFeed;

    public int getAssignMark() {
        return assignMark;
    }
    @XmlElement
    public void setAssignMark(int assignMark) {
        this.assignMark = assignMark;
    }

    public String getAssignFeed() {
        return assignFeed;
    }
    @XmlElement
    public void setAssignFeed(String assignFeed) {
        this.assignFeed = assignFeed;
    }

    public String getMidtermFeed() {
        return midtermFeed;
    }
    @XmlElement
    public void setMidtermFeed(String midtermFeed) {
        this.midtermFeed = midtermFeed;
    }

    public String getQuizFeed() {
        return quizFeed;
    }
    @XmlElement
    public void setQuizFeed(String quizFeed) {
        this.quizFeed = quizFeed;
    }

    public String getInclasslabsFeed() {
        return inclasslabsFeed;
    }
    @XmlElement
    public void setInclasslabsFeed(String inclasslabsFeed) {
        this.inclasslabsFeed = inclasslabsFeed;
    }

    public int getFinalMark() {
        return finalMark;
    }
    @XmlElement
    public void setFinalMark(int finalMark) {
        this.finalMark = finalMark;
    }

    public String getFinalFeed() {
        return finalFeed;
    }

    @XmlElement
    public void setFinalFeed(String finalFeed) {
        this.finalFeed = finalFeed;
    }

    
   /* public int getAssignment() {
        return assignment;
    }

    @XmlElement
    public void setAssignment(int assignment) {
        this.assignment = assignment;
         LOG.debug("The updated gradebook = {}", this);
    }*/

    public int getMidtermMark() {
        return midtermMark;
    }

    @XmlElement
    public void setMidtermMark(int midtermMark) {
        this.midtermMark = midtermMark;
    }

    public int getQuizMark() {
        return quizMark;
    }

    @XmlElement
    public void setQuizMark(int quizMark) {
        this.quizMark = quizMark;
    }

    public int getInclasslabsMark() {
        return inclasslabsMark;
    }

    @XmlElement
    public void setInclasslabsMark(int inclasslabsMark) {
        this.inclasslabsMark = inclasslabsMark;
    }
   

    public Gradebook() {
        LOG.info("Creating an Gradebook object");
    }

    public String getFinalgrade() {
        return finalgrade;
    }

    @XmlElement
    public void setFinalgrade(String finalgrade) {
        LOG.info("Setting the final grade to {}", finalgrade);
        
        this.finalgrade = finalgrade;
        
        LOG.debug("The updated gradebook = {}", this);
    }

    public int getPriority() {
        return priority;
    }

    @XmlElement
    public void setPriority(int priority) {
        LOG.info("Setting the priority to {}", priority);
        
        this.priority = priority;
        
        LOG.debug("The updated gradebook = {}", this);
    }

    public int getId() {
        return id;
    }

    @XmlAttribute
    public void setId(int id) {
        LOG.info("Setting the id to {}", id);
        
        this.id = id;
        
        LOG.debug("The updated gradebook = {}", this);
    }

    @Override
    public String toString() {
        return "Gradebook{" + "id="+id+ ", finalgrade=" + finalgrade + ", priority=" + priority + 
                ", assignMark=" + assignMark + ", assignFeed=" + assignFeed + ", midtermMark=" + midtermMark + ", midtermFeed=" + midtermFeed +
                ", quizMark=" + quizMark + ", quizFeed=" + quizFeed + ", inclasslabsMark="+ inclasslabsMark + ", inclasslabsFeed="+ inclasslabsFeed + 
                ", finalMark=" + finalMark +", finalFeed=" + finalFeed +'}';
    }
    
}
