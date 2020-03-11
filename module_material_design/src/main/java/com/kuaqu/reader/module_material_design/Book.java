package com.kuaqu.reader.module_material_design;

import java.io.Serializable;

public class Book implements Serializable{
    private String title;
    private String author;
    private String subtitle;
    private String date;
    private String price;
    private int img;

    public Book(String title, String author, String subtitle, String date, String price, int img) {
        this.title = title;
        this.author = author;
        this.subtitle = subtitle;
        this.date = date;
        this.price = price;
        this.img = img;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
