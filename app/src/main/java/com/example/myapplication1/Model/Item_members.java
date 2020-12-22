package com.example.myapplication1.Model;

public class Item_members {
    private int avatar_members;
    private String name_members;
    private String email_members;

    public Item_members(int avatar_members, String name_members, String email_members) {
        this.avatar_members = avatar_members;
        this.name_members = name_members;
        this.email_members = email_members;
    }

    public int getAvatar_members() {
        return avatar_members;
    }

    public void setAvatar_members(int avatar_members) {
        this.avatar_members = avatar_members;
    }

    public String getName_members() {
        return name_members;
    }

    public void setName_members(String name_members) {
        this.name_members = name_members;
    }

    public String getEmail_members() {
        return email_members;
    }

    public void setEmail_members(String email_members) {
        this.email_members = email_members;
    }
}
