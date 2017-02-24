package com.example.ruby.mygetgps.models;

import android.graphics.drawable.Drawable;

public class WorkSource {
    private String name;
    private int image;
    private String color;
    private Drawable icon;

    private boolean selected = false;

    public WorkSource() {
    }

    public WorkSource(String name, Drawable icon, String color) {
        this.name = name;
        this.icon = icon;
        this.color = color;
    }

    public WorkSource(String name, Drawable icon, String color, boolean selected) {
        this.name = name;
        this.icon = icon;
        this.color = color;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
