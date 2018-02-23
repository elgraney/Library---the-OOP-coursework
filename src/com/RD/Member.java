package com.RD;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created on 15/02/2018.
 */
public class Member {
    private String id;
    private String name;
    private LocalDate dateJoin;
    private Loan[] loans;

    public Member(String id, String firstName, String secondName, LocalDate dateJoin){}

    public String getName(){
        return name;
    }
    public Loan[] getLoans(){
        return loans;
    }
    public void setName(String newName){
        name = newName;
    }
    public void setLoans(String newLoan){
        loans.add(newLoan); //unsure if correct
    }
}
