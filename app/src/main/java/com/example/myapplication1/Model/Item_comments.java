package com.example.myapplication1.Model;

public class Item_comments {
    private String name_members;
    private String comments;

    public Item_comments(String name_members, String comments) {
        this.name_members = name_members;
        this.comments = comments;
    }

    public String getName_members() {
        return name_members;
    }

    public void setName_members(String name_members) {
        this.name_members = name_members;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}

