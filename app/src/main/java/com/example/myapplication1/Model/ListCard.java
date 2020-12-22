package com.example.myapplication1.Model;

import java.util.ArrayList;
import java.util.Objects;

public class ListCard {
    String Name;
    String ID_listcard;
    ArrayList<Item_Card> item_cards;
    String Id_key;


    public void setId_key(String id_key) {
        Id_key = id_key;
    }

    public String getId_key() {
        return Id_key;
    }

    public ListCard(String name, String ID_listcard, ArrayList<Item_Card> item_cards, String Id_key) {
        this.Name = name;
        this.ID_listcard = ID_listcard;
        this.item_cards = item_cards;
        this.Id_key = Id_key;
    }
    public ListCard(){}


    public void setName(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setID_listcard(String ID_listcard) {
        this.ID_listcard = ID_listcard;
    }

    public String getID_listcard() {
        return ID_listcard;
    }

    public void setItem_cards(ArrayList<Item_Card> item_cards) {
        this.item_cards = item_cards;
    }

    public ArrayList<Item_Card> getItem_cards() {
        return item_cards;
    }
//    @Override
////    public boolean equals(Object o) {
////        if (this == o) return true;
////        if (!(o instanceof ListCard)) return false;
////        ListCard listCard1 = (ListCard) o;
////        return getIdKey().equals(listCard1.getIdKey()) &&
////                getName_listCard().equals(listCard1.getName_listCard()) &&
////                Objects.equals(getListCard(), listCard1.getListCard());
////    }
////
////    @Override
////    public int hashCode() {
////        return Objects.hash(getIdKey(), getName_listCard(), getListCard());
////    }
}
