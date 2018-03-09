package com.RD;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Loan stores all data for each loan and handles transitions of that data
 *
 * @version 09/03/2018
 */
public class Loan {
    /**
     * title of the book
     */
    private String title;
    /**
     * ID of the loaned book
     */
    private int bookId;
    /**
     * Id of the member taking out the loan
     */
    private int memberId;
    /**
     * Id of the loan itself
     */
    private int bookLoanId;
    /**
     * the date the book was borrowed
     */
    private LocalDate borrowDate;


    /**
     * initialises a loan with basic date
     * @param bookLoanId the ID of the loan
     * @param bookId the ID of the book
     * @param memberId the ID of the member
     * @param borrowDate the date the book was borrowed
     */
    public Loan( int bookLoanId, int bookId, int memberId, LocalDate borrowDate){
        this.bookId = bookId;
        this.memberId = memberId;
        this.bookLoanId = bookLoanId;
        this.borrowDate = borrowDate;
    }

    /**
     * returns the title of the Book
     * @return the title of the Book
     */
    public String getTitle(){
        return title;
    }

    /**
     * returns the due date of the loan (30 days from the borrow date)
     * @return the due date of the loan (30 days from the borrow date)
     */
    public LocalDate getReturnDate(){return borrowDate.plusDays(30);}

    /**
     * returns the ID of the member
     * @return the ID of the member
     */
    public int getMemberId(){
        return memberId;
    }

    /**
     * returns the date the loan was taken out
     * @return the date the loan was taken out
     */
    public LocalDate getBorrowDate(){
        return borrowDate;
    }

    /**
     * returns The ID of the book
     * @return The ID of the book
     */
    public int getBookId(){
        return bookId;
    }

    /**
     * sets the title of the book
     * @param title the title of the book
     */
    public void setTitle(String title) {this.title = title;}

    /**
     * returns the ID of the loan
     * @return the ID of the loan
     */
    public int getId(){return bookLoanId;
    }

}
