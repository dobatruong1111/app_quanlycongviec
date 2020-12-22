package com.example.myapplication1.Model;

public class Item_Card {
    String Name ;
    String ID_of_listcard;
    String Id_Card;

    public Item_Card(String Name, String ID_of_listcard, String Id_Card) {
        this.Name = Name;
        this.ID_of_listcard = ID_of_listcard;
        this.Id_Card = Id_Card;
    }

    public Item_Card(){

    }

    public void setID_of_listcard(String ID_of_listcard) {
        this.ID_of_listcard = ID_of_listcard;
    }

    public String getID_of_listcard() {
        return ID_of_listcard;
    }



    public void setName(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setId_Card(String id_Card) {
        Id_Card = id_Card;
    }

    public String getId_Card() {
        return Id_Card;
    }
}
