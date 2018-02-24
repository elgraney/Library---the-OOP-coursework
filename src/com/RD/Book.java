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

    public Book(String id, String title, String[] authors, int year, int Qty){}

    public String[] getAuthors(){
        return authors;
    }
    public String getTitle(){
        return title;
    }

    public void setTitle(String newTitle){
        title = newTitle;
    }
    public void setAuthors(String[] authorsList){
        this.authors = authorsList; //unsure if correct
    }
}
