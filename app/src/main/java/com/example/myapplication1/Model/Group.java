package com.example.myapplication1.Model;

import java.util.ArrayList;
import java.util.Objects;

public class Group extends Project {

    private ArrayList<String> IDmember;

    public Group(){
        super();
    }

    public Group(String idKey, String name, String userOwn, ArrayList<String> listCards, ArrayList<String> IDmember) {
        super(idKey, name, userOwn, listCards);
        this.IDmember = IDmember;
    }

    public ArrayList<String> getIDMember() {
        return IDmember;
    }

    public void setIDMember(ArrayList<String> IDmember) {
        this.IDmember = IDmember;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        if (!super.equals(o)) return false;
        Group group = (Group) o;
        return Objects.equals(IDmember, group.IDmember);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), IDmember);
    }
}
