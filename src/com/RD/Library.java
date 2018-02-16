package com.RD;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;



public class Library {


    private ArrayList<Book> bookList= new ArrayList();
    private ArrayList<Member> memberList= new ArrayList();
    private ArrayList<Loan> loanList= new ArrayList();



    public Library(String file1, String file2, String file3){
        importBooksFile(file1);

    }

    private void importBooksFile(String path){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line = null;

        try {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                bookList.add(new Book(values[0], values[1], seperateAuthors(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4])));

                System.out.println(values[0]);//id
                System.out.println(values[1]);//title
                System.out.println(values[2]);//authors
                System.out.println(values[3]);//year
                System.out.println(values[4]);//numberCopies
                System.out.println("break");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String[] seperateAuthors(String authorString){
        String[] authors= authorString.split(":");
        return authors;
    }

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
