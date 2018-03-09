package com.RD;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.StringJoiner;

/**
 * Member stores all data for each member and handles transitions of that data
 *
 * @version 09/03/2018
 */
public class Member {
    /**
     * the ID of the member
     */
    private int id;
    /**
     * the first name of the member
     */
    private String firstName;
    /**
     * the second name of the member
     */
    private String secondName;
    /**
     * the date the member joined
     */
    private LocalDate dateJoin;
    /**
     * the number of books this member has borrowed
     */
    private int borrowedBooks;


    /**
     * Initialises each instance of Member
     * @param id the ID of the member
     * @param firstName the first name of the member
     * @param secondName the second name of the member
     * @param dateJoin the date the member joined
     */
    public Member(int id, String firstName, String secondName, LocalDate dateJoin){
        this.id = id;
        this.firstName=firstName;
        this.secondName=secondName;
        this.dateJoin = dateJoin;
        this.borrowedBooks=0;
    }

    /**
     * Increases or decreases the number of books this member has borrowed
     * @param qty the number by which to change the total quantity
     */
    public void addBorrowedBooks(int qty){
        this.borrowedBooks = borrowedBooks+qty;
    }

    /**
     * returns the number of books the member has borrowed
     * @return the number of books the member has borrowed
     */
    public int getBorrowedBooks(){
        return borrowedBooks;
    }

    /**
     * returns the first name of the member
     * @return the first name of the member
     */
    public String getFirstName(){
        return firstName;
    }

    /**
     * returns the second name of the member
     * @return the second name of the member
     */
    public String getSecondName(){
        return secondName;
    }

    /**
     * returns the ID of the member
     * @return the ID of the member
     */
    public int getId(){
        return id;
    }

    /**
     * returns the date the member joined
     * @return the date the member joined
     */
    public LocalDate getDateJoin(){
        return dateJoin;
    }

    /**
     * sets both the first and second name of the member separately
     * @param firstName the member's first name
     * @param secondName the member's second name
     */
    public void setName(String firstName, String secondName){
        this.firstName = firstName;
        this.secondName = secondName;
    }
}
