package com.RD;

import java.time.Year;

/**
 * Created on 15/02/2018.
 */
public class Book {
    private String title;
    private String[] authors;
    private int id;
    private int year;
    private int totalQty;
    private int availibleQty;

    public Book(int id, String title, String[] authors, int year, int qty){
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.year = year;
        this.totalQty = qty;
    }

    public String[] getAuthors(){
        return authors;
    }
    public String getTitle(){
        return title;
    }
    public int getId(){
        return id;
    }
    public int getYear(){
        return year;
    }
    public int getTotalQty(){
        return totalQty;
    }
    public void setTitle(String newTitle){
        title = newTitle;
    }


    public void addAdditionalBooks(int qty){
        totalQty +=qty;
        availibleQty+=qty;
    }
    public void changeAvailableQty(int qty){
        availibleQty += qty;
    }
    public int getAvailableQty(){
        return availibleQty;
    }
}
