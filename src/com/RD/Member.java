package com.RD;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.StringJoiner;

/**
 * Created on 15/02/2018.
 */
public class Member {
    private int id;
    private String firstName;
    private String secondName;
    private LocalDate dateJoin;


    public Member(int id, String firstName, String secondName, LocalDate dateJoin){
        this.id = id;
        this.firstName=firstName;
        this.secondName=secondName;
        this.dateJoin = dateJoin;

    }

    public String getFirstName(){
        return firstName;
    }
    public String getSecondName(){
        return secondName;
    }
    public int getId(){
        return id;
    }

    public LocalDate getDateJoin(){
        return dateJoin;
    }
    public void setName(String firstName, String secondName){
       this.firstName = firstName;
        this.secondName = secondName;
    }
}
