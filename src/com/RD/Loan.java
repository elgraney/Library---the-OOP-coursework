package com.RD;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created on 15/02/2018.
 */
public class Loan {
    private String title;
    private LocalDate date;
    private int bookId;
    private int memberId;
    private int bookLoanId;
    private LocalDate borrowDate;

    public Loan( int bookLoanId, int bookId, int memberId, LocalDate borrowDate){
        this.bookId = bookId;
        this.memberId = memberId;
        this.bookLoanId = bookLoanId;
        this.borrowDate = borrowDate;
    }

    public String getTitle(){
        return title;
    }
    public LocalDate getDate(){
        return LocalDate.now();
    }
    public LocalDate getReturnDate(){
        return borrowDate.plusDays(5);
    }
    public int getMemberId(){
        return memberId;
    }
    public LocalDate getBorrowDate(){
        return borrowDate;
    }
    public int getBookId(){
        return bookId;
    }
    public void setTitle(String title) {this.title = title;}
    public int getId(){return bookLoanId;
    }

}
