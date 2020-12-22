package com.example.myapplication1.Model;

import java.util.ArrayList;

public class Card {
    private String the_name_of_job;
    private String list_name;
    private String edit_the_name_of_job;
    private Item_labels labels;
    private String members;
    private String end_date;
    private ArrayList<Item_list_job> list_jobs;
    private ArrayList<Item_attachments> attachments;
    private ArrayList<Item_comments> comments;

    public Card(){

    }

    public Card(String the_name_of_job, String list_name, String edit_the_name_of_job, Item_labels labels,
                String members, String end_date, ArrayList<Item_list_job> list_jobs, ArrayList<Item_attachments> attachments,
                ArrayList<Item_comments> comments) {
        this.the_name_of_job = the_name_of_job;
        this.list_name = list_name;
        this.edit_the_name_of_job = edit_the_name_of_job;
        this.labels = labels;
        this.members = members;
        this.end_date = end_date;
        this.list_jobs = list_jobs;
        this.attachments = attachments;
        this.comments = comments;
    }

    public String getThe_name_of_job() {
        return the_name_of_job;
    }

    public void setThe_name_of_job(String the_name_of_job) {
        this.the_name_of_job = the_name_of_job;
    }

    public String getList_name() {
        return list_name;
    }

    public void setList_name(String list_name) {
        this.list_name = list_name;
    }

    public String getEdit_the_name_of_job() {
        return edit_the_name_of_job;
    }

    public void setEdit_the_name_of_job(String edit_the_name_of_job) {
        this.edit_the_name_of_job = edit_the_name_of_job;
    }

    public Item_labels getLabels() {
        return labels;
    }

    public void setLabels(Item_labels labels) {
        this.labels = labels;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public ArrayList<Item_list_job> getList_jobs() {
        return list_jobs;
    }

    public void setList_jobs(ArrayList<Item_list_job> list_jobs) {
        this.list_jobs = list_jobs;
    }

    public ArrayList<Item_attachments> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<Item_attachments> attachments) {
        this.attachments = attachments;
    }

    public ArrayList<Item_comments> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Item_comments> comments) {
        this.comments = comments;
    }
}