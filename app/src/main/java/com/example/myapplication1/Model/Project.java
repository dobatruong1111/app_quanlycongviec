package com.example.myapplication1.Model;


import java.util.ArrayList;
import java.util.Objects;

public class Project {
    private String idKey;
    private String name;
    private String IDUserOwn;
    private ArrayList<String> IDlistCards = new ArrayList<>();

    public Project() {
    }

    public Project(String idKey, String name, String IDuserOwn, ArrayList<String> IDlistCards) {
        this.idKey = idKey;
        this.name = name;
        this.IDUserOwn = IDuserOwn;
        this.IDlistCards = IDlistCards;
    }

    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIDUserOwn() {
        return IDUserOwn;
    }

    public void setIDUserOwn(String IDUserOwn) {
        this.IDUserOwn = IDUserOwn;
    }

    public ArrayList<String> getIDlistCards() {
        return IDlistCards;
    }

    public void setIDlistCards(ArrayList<String> IDlistCards) {
        this.IDlistCards = IDlistCards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        Project project = (Project) o;
        return Objects.equals(getIdKey(), project.getIdKey()) &&
                Objects.equals(getName(), project.getName()) &&
                Objects.equals(getIDUserOwn(), project.getIDUserOwn()) &&
                Objects.equals(getIDlistCards(), project.getIDlistCards());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdKey(), getName(), getIDUserOwn(), getIDlistCards());
    }
}
