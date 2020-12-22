package com.example.myapplication1.Model;

public class Item_Date {
    String Date;
    String Id_card;

    public void setId_card(String id_card) {
        Id_card = id_card;
    }

    public String getId_card() {
        return Id_card;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Item_Date(String date,String Id_card) {
        Date = date;
        this.Id_card = Id_card;
    }
    public Item_Date (){

    }
}
