package com.RD;

import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.DAYS;


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
        ArrayList<Book> searchResults = new ArrayList<Book>();
        for(int i = 0; i<bookList.size(); i++) {
            Book book = bookList.get(i);
            if (book.getTitle().toLowerCase().contains(name.toLowerCase())){
                searchResults.add(book);
            }
        }
        return searchResults;
    }

    public ArrayList<Member> searchMember(String firstName, String secondName){
        ArrayList<Member> searchResults = new ArrayList<Member>();
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
        if (searchResults.size()>0){
            Book book = searchResults.get(0);
            System.out.println("Book '"+book.getTitle()+"' selected for loan.");
            System.out.println("***Should do a check to make sure this is the book they want***");
            if (searchResults.size()>0 && searchResults.get(0).getTotalQty() >0 ){

                loanList.add(new Loan(book.getTitle(), book.getId(), "MemberID Placeholder", LocalDate.now() ));
                System.out.println("***MemberID Placeholder in use***");

                }
            else if (searchResults.get(0).getTotalQty() < 1){
                System.out.println("There are no available copies of the book '"+book.getTitle()+"'.");
            }
        }
        else{
            System.out.println("No search results found.");
        }
        System.out.println("Would you like to select another book?");
        //do yes no
        Scanner in = new Scanner(System.in);
        boolean isYesOrNoInput;
        do {
            char inCh = in.next().charAt(0);
            isYesOrNoInput = true;
            if ((inCh == 'y') || (inCh == 'Y')){
                //ADD BORROW BOOK
                System.out.println("***REPEAT BORROW BOOK (BUT FIRST WORK OUT HOW INPUTS WILL WORK)");
            }
            else if ((inCh == 'n') || (inCh == 'N')){
                System.out.println("Returning to menu...");
            }
            else {
                System.out.println("******** Wrong input. Try again.");
                isYesOrNoInput = false;
            }

        } while (!isYesOrNoInput);
    }

    public void returnBook(int id){

    }

    public void addNewBook(String name, String[] authors, int year,int quantity){
        ArrayList<Book> searchResults = searchBook(name);
        if (searchResults.size() != 0 && searchResults.size()<2){
            searchResults.get(0).addAdditionalBooks(quantity);
        }
        else if (searchResults.size() == 0){
            //add id in place of 999
            bookList.add(new Book(getNewBookId(), name , authors, year, quantity));
        }
        else{
            System.out.println("Search returned multiple entries. Please be more precise.");
        }
    }
    public void addNewMember(String firstName, String secondName, LocalDate date){
        memberList.add(new Member(getNewMemberId(),firstName,secondName,date));
    }
    public String getNewBookId(){
        int highest = 0;

        for (Book book: bookList){
            int currentId = Integer.parseInt(book.getId());
            if (currentId> highest){
                highest = currentId;
            }
        }
        String newId = Integer.toString(highest + 1);
        return newId;
    }
    public String getNewMemberId(){
        int highest = 0;

        for (Member member : memberList){
            int currentId = Integer.parseInt(member.getId());
            if (currentId> highest){
                highest = currentId;
            }
        }
        String newId = Integer.toString(highest + 1);
        return newId;
    }

    
    public void changeQuantity(String name, int quantity){
        Book book = searchBook(name).get(0);
        if( (book.getAvailibleQty() +quantity) >= 0){
            book.changeAvailibleQty(quantity);
        }
        else{
            System.out.println("There are not enough available books of that name to take that many.");
        }

    }
    public Boolean calculateFine(LocalDate borrowDate){
        LocalDate currentDate = LocalDate.now();
        int daysBetween = (int) DAYS.between(borrowDate, currentDate);
        //test
        System.out.println(daysBetween);
        Boolean canReturn = false;
        if (daysBetween>30){
            double fine = (daysBetween - 30) * 0.1;
            System.out.println("This book is overdue and has accumulated a fine of "+fine);

            Scanner in = new Scanner(System.in);
            boolean isYesOrNoInput;
            do {
                System.out.println("Would you like to pay this fine now?");
                char inCh = in.next().charAt(0);
                isYesOrNoInput = true;
                if ((inCh == 'y') || (inCh == 'Y')){
                    System.out.println("Fine paid.");
                    canReturn = true;
                }
                else if ((inCh == 'n') || (inCh == 'N')){
                    System.out.println("Fine not paid, book return unsuccessful");
                    System.out.println("Please try again when you can pay the fine.");

                } else {
                    System.out.println("******** Wrong input. Try again.");
                    isYesOrNoInput = false;
                }

            } while (!isYesOrNoInput);

        }
        return canReturn;
    }



}
