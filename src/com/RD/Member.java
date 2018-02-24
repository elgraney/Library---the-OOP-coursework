package com.RD;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Created on 15/02/2018.
 */
public class Member {
    private String id;
    private String name;
    private LocalDate dateJoin;
    private ArrayList<Loan> loans;

    public Member(String id, String firstName, String secondName, LocalDate dateJoin){}

    public String getName(){
        return name;
    }
    public ArrayList<Loan> getLoans(){
        return loans;
    }
    public void setName(String newName){
        name = newName;
    }
    public void setLoans(Loan newLoan){
        loans.add(newLoan); //unsure if correct
    }
}
