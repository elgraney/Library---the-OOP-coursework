package com.RD;

import java.time.Year;

/**
 * Created on 15/02/2018.
 */
public class Book {
    private String title;
    private String[] authors;
    private String id;
    private int year;
    private int totalQty;
    private int availibleQty;

    public Book(String id, String title, String[] authors, int year, int qty){
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
    public String getId(){
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
    public void setAuthors(String[] authorsList){
        this.authors = authorsList; //unsure if correct
    }

    public void addAdditionalBooks(int qty){
        totalQty +=qty;
        availibleQty+=qty;
    }
}
