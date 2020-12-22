package com.example.myapplication1.Model;

import android.graphics.drawable.Drawable;

public class Item_labels {
    private Drawable color;
    private String labels_name;

    public Item_labels(Drawable color, String labels_name) {
        this.color = color;
        this.labels_name = labels_name;
    }

    public Drawable getColor() {
        return color;
    }

    public void setColor(Drawable color) {
        this.color = color;
    }

    public String getLabels_name() {
        return labels_name;
    }

    public void setLabels_name(String labels_name) {
        this.labels_name = labels_name;
    }
}
