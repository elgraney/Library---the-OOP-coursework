package com.RD;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created on 15/02/2018.
 */
public class Loan {
    private String title;
    private LocalDate date;
    private String bookId;
    private String memberId;
    private LocalDate borrowDate;

    public Loan(String title, String bookId, String memberId, LocalDate borrowDate){
        this.title = title;
        this.bookId = bookId;
        this.memberId = memberId;
        this.borrowDate = borrowDate;
    }

    public void getTitle(){}
    public void getDate(){}
    public void getReturnDate(){}
    public void getID(){}
}
