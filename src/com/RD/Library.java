package com.RD;

import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;



public class Library {


    private ArrayList<Book> bookList= new ArrayList();
    private ArrayList<Member> memberList= new ArrayList();
    private ArrayList<Loan> loanList= new ArrayList();



    public Library(String file1, String file2, String file3){
        try {
            importBooksFile(file1);
            importMembersFile(file2);
            importLoansFile(file3);


            saveChanges(file1, file2, file3);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void importBooksFile(String path) throws IOException {
        BufferedReader br = null;

        br = new BufferedReader(new FileReader(path));

        String line = null;

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            bookList.add(new Book(values[0], values[1], seperateAuthors(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4])));
            //testing
            System.out.println(values[0]);//id
            System.out.println(values[1]);//title
            System.out.println(values[2]);//authors
            System.out.println(values[3]);//year
            System.out.println(values[4]);//numberCopies
            System.out.println("break");
        }
        br.close();
    }

    private String[] seperateAuthors(String authorString){
        String[] authors= authorString.split(":");
        return authors;
    }


    private void importMembersFile(String path) throws IOException {
        BufferedReader br = null;

        br = new BufferedReader(new FileReader(path));

        String line = null;

        while ((line = br.readLine()) != null) {

            String[] values = line.split(",");
            memberList.add(new Member(values[0], values[1], values[2],LocalDate.parse(values[3])));

            //testing
            System.out.println(values[0]);//id
            System.out.println(values[1]);//fname
            System.out.println(values[2]);//sname
            System.out.println(values[3]);//date
            System.out.println("break");
        }
        br.close();
    }

    private void importLoansFile(String path) throws IOException {
        BufferedReader br = null;

        br = new BufferedReader(new FileReader(path));

        String line = null;

        while ((line = br.readLine()) != null) {

            String[] values = line.split(",");

            loanList.add(new Loan(values[0], values[1], values[2],LocalDate.parse(values[3])));
            //testing
            System.out.println(values[0]);//id
            System.out.println(values[1]);//bookid
            System.out.println(values[2]);//memberid
            System.out.println(values[3]);//date
            System.out.println("break");
        }
        br.close();
    }



    //change void
    public void showAllBooks(){
        for (int i=0; i<bookList.size(); i++){
            System.out.println("Next Book:");
            showBook(bookList.get(i));
        }
    }
    private void showBook(Book book){
        System.out.println("Title: " + book.getTitle());
        System.out.println("ID: "+book.getId());
        System.out.println("Author(s): " + String.join(", ",(book.getAuthors())));

    }
    public void showAllMembers(){
        for (int i=0; i<memberList.size(); i++){
            System.out.println("Next Member:");
            showMember(memberList.get(i));
        }
    }

    private void showMember(Member member){
        System.out.println("Name: " + member.getFirstName()+" " + member.getSecondName());
        System.out.println("ID: "+member.getId());
        //etc
    }
    public void showAllBookLoans(){for (int i=0; i<memberList.size(); i++){
        System.out.println("Next Member:");
        showBookLoan(loanList.get(i));
    }
    }

    private void showBookLoan(Loan loan){
        System.out.println("Title: " + loan.getTitle());
        System.out.println("ID: "+loan.getId());
        //etc
    }


    //needs get methods to work
    public void saveChanges(String file1, String file2, String file3){
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("testBook.txt"), "utf-8"));
            for(int i=0; i<bookList.size(); i++){
                Book book = bookList.get(i);
                writer.write(book.getId()+","+book.getTitle()+","+String.join(":",(book.getAuthors()))+","+book.getYear()+","+book.getTotalQty());
                writer.newLine();
            }
            writer.close();
        }catch (IOException e){
            System.out.println("IOException: Saving books data failed");
        }
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("testMember.txt"), "utf-8"));
            for(int i=0; i<memberList.size(); i++){
                Member member = memberList.get(i);
                writer.write(member.getId()+","+member.getFirstName()+","+member.getSecondName()+","+member.getDateJoin());
                writer.newLine();
            }
            writer.close();
        }catch (IOException e){
            System.out.println("IOException: Saving members data failed");
        }
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("testLoans.txt"), "utf-8"));
            for(int i=0; i<loanList.size(); i++){
                Loan loan = loanList.get(i);
                writer.write(loan.getId()+","+loan.getBookId()+","+loan.getMemberId()+","+loan.getBorrowDate());
                writer.newLine();
            }
            writer.close();
        }catch (IOException e){
            System.out.println("IOException: Saving loans data failed");
        }
    }



    public ArrayList<Book> searchBook(String name){
        ArrayList<Book> searchResults = null;
        for(int i = 0; i<bookList.size(); i++) {
            Book book = bookList.get(i);
            if (book.getTitle().toLowerCase().contains(name.toLowerCase())){
                searchResults.add(book);
            }
        }
        return searchResults;
    }

    public ArrayList<Member> searchMember(String firstName, String secondName){
        ArrayList<Member> searchResults = null;
        for(int i = 0; i<memberList.size(); i++) {
            Member member = memberList.get(i);
            if (member.getFirstName().toLowerCase().contains(firstName.toLowerCase())||
                    member.getSecondName().toLowerCase().contains(secondName.toLowerCase())){
                searchResults.add(member);
            }
        }
        return searchResults;
    }

    

    //change firstName, secondName to just one big name
    public void borrowBook(String name, String authorFirstName, String authorSecondName){
        ArrayList<Book> searchResults = searchBook(name);
        if (searchResults!=null && searchResults.get(0).getTotalQty() >0 ){
            //placeholder
            System.out.println(searchResults.get(0).getTitle());
        }
    }
    public void returnBook(int id){}

    public void addNewBook(String name, String[] authors, int year,int quantity){
        ArrayList<Book> searchResults = searchBook(name);
        if (searchResults!=null && searchResults.size()<2){
            searchResults.get(0).addAdditionalBooks(quantity);
        }
        else if (searchResults == null){
            //add id in place of 999
            bookList.add(new Book("999", name , authors, year, quantity));
        }
        else{
            System.out.println("Search returned multiple entries. Please be more precise.");
        }
    }
    public void addNewMember(String firstName, String secondName, LocalDate date){
        //sort id
        memberList.add(new Member("999",firstName,secondName,date));
    }

    
    public void changeQuantity(String name, int quantity){}
    public void calculateFine(){}



}
