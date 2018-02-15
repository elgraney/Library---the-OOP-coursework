package com.RD;

import java.time.LocalDate;
import java.time.LocalTime;


public class Library {

    public Library(String file1, String file2, String file3){}

    //change void
    public void showAllBooks(){}
    public void showAllMembers(){}
    public void showAllBookLoans(){}
    public void saveChanges(String file1, String file2, String file3){}
    public void searchBook(String name){}
    //change firstName, secondName to just one big name
    public void searchMember(String firstName, String secondName1){}
    //change firstName, secondName to just one big name
    public void borrowBook(String name, String authorFirstName, String authorSecondName){}
    public void returnBook(int id){}
    public void addNewBook(String name, String[] authors, int year,int quantity){}
    public void addNewMember(String firstName, String secondName, LocalDate date){}
    public void changeQuantity(String name, int quantity){}
    public void calculateFine(){}

}
