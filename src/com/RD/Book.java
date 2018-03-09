package com.RD;

import java.time.Year;

/**
 * Book stores all data for each book and handles transitions of that data
 *
 * @version 09/03/2018
 */
public class Book {
    /**
     * the title of the book
     */
    private String title;
    /**
     * the author(s) of the book
     */
    private String[] authors;
    /**
     * the ID of the book
     */
    private int id;
    /**
     * the year the book was published
     */
    private int year;
    /**
     * the total copies of the book in the library
     */
    private int totalQty;
    /**
     * the available copies of the book in the library
     */
    private int availibleQty;

    /**
     * initialises a book with basic date
     * @param id the ID of the book
     * @param title the title of the book
     * @param authors the authors of the book
     * @param year the year the book was published
     * @param qty the total quantity of the book
     */
    public Book(int id, String title, String[] authors, int year, int qty){
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.year = year;
        this.totalQty = qty;
    }

    /**
     * returns the authors of the book
     * @return the authors of the book
     */
    public String[] getAuthors(){
        return authors;
    }

    /**
     * returns the title of the book
     * @return the title of the book
     */
    public String getTitle(){
        return title;
    }

    /**
     * returns the ID of the book
     * @return the ID of the book
     */
    public int getId(){
        return id;
    }

    /**
     * returns the year the book was published
     * @return the year the book was published
     */
    public int getYear(){
        return year;
    }

    /**
     * returns the total copies of the book in the library
     * @return the total copies of the book in the library
     */
    public int getTotalQty(){
        return totalQty;
    }

    /**
     * increases or decreases the total quantity and available quantity of a book equally
     * @param qty the number by which to change the quantities
     */
    public void addAdditionalBooks(int qty){
        totalQty +=qty;
        availibleQty+=qty;
    }

    /**
     * increases or decreases the available quantity
     * @param qty the number by which to change the quantities
     */
    public void changeAvailableQty(int qty){
        availibleQty += qty;
    }

    /**
     * returns the number of available copies
     * @return the number of available copies
     */
    public int getAvailableQty(){
        return availibleQty;
    }
}
