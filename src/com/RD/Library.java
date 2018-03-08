package com.RD;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.DAYS;
import static jdk.nashorn.internal.objects.NativeMath.round;


public class Library {


    private ArrayList<Book> bookList = new ArrayList();
    private ArrayList<Member> memberList = new ArrayList();
    private ArrayList<Loan> loanList = new ArrayList();


    public Library(String file1, String file2, String file3) {
        try {
            importBooksFile(file1);
            importMembersFile(file2);
            importLoansFile(file3);
            nameLoans();
            calculateAvailableCopies();
            countLoansPerMember();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void countLoansPerMember(){
        for (Loan loan : loanList) {
            for (Member member : memberList) {
                if (loan.getMemberId() == member.getId()) {
                    member.addBorrowedBooks(1);
                    if (member.getBorrowedBooks()> 5) {
                        throw new RuntimeException("Error in text files: More borrowed books than should be allowed.");
                    }
                }
            }
        }
    }

    private void nameLoans(){
        for (Loan loan : loanList){
            for (Book book : bookList){
                if (loan.getBookId() == book.getId()){
                    loan.setTitle(book.getTitle());
                }
            }
        }
    }
    private void calculateAvailableCopies() {
        for (Book book : bookList) {
            int available = book.getTotalQty();
            for (Loan loan : loanList) {
                if (loan.getBookId() == book.getId()) {
                    available -= 1;
                    if (available < 0) {
                        throw new RuntimeException("Error in text files: More loans of a book than total copies.");
                    }
                }
            }
            book.changeAvailableQty(available);

        }
    }

    private void importBooksFile(String path) throws IOException {
        BufferedReader br = null;

        br = new BufferedReader(new FileReader(path));

        String line = null;

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            bookList.add(new Book(Integer.parseInt(values[0]), values[1], seperateAuthors(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4])));
        }
        br.close();
    }

    private String[] seperateAuthors(String authorString) {
        String[] authors = authorString.split(":");
        return authors;
    }


    private void importMembersFile(String path) throws IOException {
        BufferedReader br = null;

        br = new BufferedReader(new FileReader(path));

        String line = null;

        while ((line = br.readLine()) != null) {

            String[] values = line.split(",");
            memberList.add(new Member(Integer.parseInt(values[0]), values[1], values[2], LocalDate.parse(values[3])));
        }
        br.close();
    }

    private void importLoansFile(String path) throws IOException {
        BufferedReader br = null;

        br = new BufferedReader(new FileReader(path));

        String line = null;

        while ((line = br.readLine()) != null) {

            String[] values = line.split(",");
            loanList.add(new Loan(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]) , LocalDate.parse(values[3])));
        }
        br.close();
    }



    public void showAllBooks() {
        System.out.println("Displaying all books:");
        for (int i = 0; i < bookList.size(); i++) {
            showBook(bookList.get(i));
        }
    }

    private void showBook(Book book) {
        System.out.printf("-Title: %s, ID: %s, Authors: %s\n",
                book.getTitle(), book.getId(), (String.join(", ", (book.getAuthors()))));
    }

    public void showAllMembers() {
        System.out.println("Displaying all members:");
        for (int i = 0; i < memberList.size(); i++) {
            showMember(memberList.get(i));
        }
    }

    private void showMember(Member member) {
        System.out.printf("-Name: %s, ID: %s, Join Date: %s\n",
                member.getFirstName() + " " + member.getSecondName(), member.getId(), member.getDateJoin());
    }

    public void showAllBookLoans() {
        System.out.println("Displaying all loans:");
        for (int i = 0; i < loanList.size(); i++) {
            showBookLoan(loanList.get(i));
        }
    }

    private void showBookLoan(Loan loan) {
        System.out.printf("-Title: %s, Loan ID: %s, Book ID: %s, Member ID: %s, Borrow Date: %s, Due Date: %s\n",
                loan.getTitle(), loan.getId(),loan.getBookId(), loan.getMemberId(), loan.getBorrowDate(), loan.getReturnDate());
    }

    //REPLACE PATHS WITH FILE1, 2, 3
    public void saveChanges(String file1, String file2, String file3) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("testBook.txt"), "utf-8"));
            for (int i = 0; i < bookList.size(); i++) {
                Book book = bookList.get(i);
                writer.write(book.getId() + "," + book.getTitle() + "," + String.join(":", (book.getAuthors())) + "," + book.getYear() + "," + book.getTotalQty());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("IOException: Saving books data failed");
        }
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("testMember.txt"), "utf-8"));
            for (int i = 0; i < memberList.size(); i++) {
                Member member = memberList.get(i);
                writer.write(member.getId() + "," + member.getFirstName() + "," + member.getSecondName() + "," + member.getDateJoin());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("IOException: Saving members data failed");
        }
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("testLoans.txt"), "utf-8"));
            for (int i = 0; i < loanList.size(); i++) {
                Loan loan = loanList.get(i);
                writer.write(loan.getId() + "," + loan.getBookId() + "," + loan.getMemberId() + "," + loan.getBorrowDate());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("IOException: Saving loans data failed");
        }
    }


    public void searchBook() {
        searchBook(inputString("Please enter the name of the book:"));
    }

    public void searchBook(String name) {
        //this function specifically prints the search results found in getBookSearchResults
        ArrayList<Book> searchResults = getBookSearchResults(name);
        System.out.println("Search Results:");
        if (searchResults.size() > 0) {
            for (int i = 0; i < searchResults.size(); i++) {
                Book book = searchResults.get(i);
                System.out.printf("Title: %s, Id: %s, Authors: %s, Total copies: %s, Available copies: %s\n",
                        book.getTitle(), book.getId(),String.join(", ", (book.getAuthors())) , book.getTotalQty(), book.getAvailableQty());
            }
        } else {
            System.out.println("No matches found");
        }
    }

    private ArrayList<Book> getBookSearchResults(String name) {
        //actual seach function here. returns arrayList of search results
        ArrayList<Book> searchResults = new ArrayList<Book>();
        for (int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            if (book.getTitle().toLowerCase().contains(name.toLowerCase())) {
                searchResults.add(book);
            }
        }
        return searchResults;
    }


    public void searchMember() {
        boolean invalid = true;
        String firstName ="";
        String secondName= "";
        do {
            String fullName = inputString("Please input the member's full name");
            String[] names = fullName.split(" ");
            try{
                firstName = names[0];
                secondName = names[1];
                invalid = false;
            }
            catch (IndexOutOfBoundsException e){
                System.out.println("Please be sure to input both first and second names separated by a space.");
            }
        }while(invalid);
        searchMember(firstName, secondName);

    }

    public void searchMember(String firstName, String secondName) {
        //this function specifically prints the search results found in getBookSearchResults
        ArrayList<Member> searchResults = getMemberSearchResults(firstName, secondName);
        System.out.println("Search Results:");
        if (searchResults.size() > 0) {
            for (int i = 0; i < searchResults.size(); i++) {
                Member member = searchResults.get(i);
                System.out.printf("Name: %s %s, ID: %s, Join Date: %s\n",
                        member.getFirstName(), member.getSecondName(), member.getId(), member.getDateJoin());
                System.out.println("Current loans:");
                ArrayList<Loan> loans = new ArrayList<>();
                for (Loan loan : loanList) {
                    if (loan.getMemberId() == member.getId()) {
                        loans.add(loan);
                    }
                }
                System.out.printf("You currently have %d borrowed books\n", loans.size());
                for (Loan loan : loans) {
                    System.out.printf("Title: %s, Book Id: %s, Borrow Date: %s, Return Date: %s\n",
                            loan.getTitle(), loan.getBookId(), loan.getBorrowDate(), loan.getReturnDate());
                    if (checkfine(loan.getBorrowDate())) {
                        System.out.printf("This book is overdue and has accumulated a fine of £%.2f\n",
                                calcFineValue(loan.getBorrowDate()));
                    }
                }
            }
        } else {
            System.out.println("No matches found");
        }
    }

    public ArrayList<Member> getMemberSearchResults(String firstName, String secondName) {
        ArrayList<Member> searchResults = new ArrayList<Member>();
        for (int i = 0; i < memberList.size(); i++) {
            Member member = memberList.get(i);
            if (member.getFirstName().toLowerCase().contains(firstName.toLowerCase()) &&
                    member.getSecondName().toLowerCase().contains(secondName.toLowerCase())) {
                searchResults.add(member);
            }
        }
        return searchResults;
    }

    public Loan getLoanSearchResult(int id) {

        for (int i = 0; i < loanList.size(); i++) {
            Loan loan = loanList.get(i);
            if (loan.getId() == id) {
                return loan;
            }
        }
        return null;
    }


    private ArrayList<Member> inputMemberName() {
        Boolean inputPassed = false;
        Scanner in = new Scanner(System.in);
        ArrayList<Member> member = memberList;
        while (!inputPassed) {
            System.out.println("Please input your name:");


            member = getMemberSearchResults(in.next(), in.next());
            if (member.size() > 0) {
                inputPassed = true;
            } else {
                System.out.println("No matching members. Would you like to re-enter your name? (Y/N)");
                Boolean isYesOrNoInput;
                do {
                    char inCh = in.next().charAt(0);
                    isYesOrNoInput = true;
                    if ((inCh == 'y') || (inCh == 'Y')) {

                    } else if ((inCh == 'n') || (inCh == 'N')) {

                        System.out.println("Returning to menu...");
                        return new ArrayList<>();
                    } else {
                        System.out.println("******** Wrong input. Try again.");
                        isYesOrNoInput = false;
                    }
                } while (!isYesOrNoInput);
            }
        }
        return member;
    }

    public String inputString(String message) {
        System.out.println(message);
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        return name;
    }

    private int inputInt(String message) {
        boolean valid = false;
        int intId = 0;
        do{
            System.out.println(message);
            Scanner in = new Scanner(System.in);
            String id = in.next();

            try {
                intId = Integer.parseInt(id);
                valid =true;
            }catch(NumberFormatException e){
                System.out.println("The input must be entirely numerical; non numerical characters are not allowed.");
            }
        }while (!valid);
        return intId;
    }

    private String[] inputAuthors(){
        boolean continueInput = true;
        boolean valid = false;
        Scanner in = new Scanner(System.in);
        ArrayList<String> authors = new ArrayList<String>();
        do{
            do {
                System.out.println("Please enter the name of the author");
                String author = in.nextLine();
                if (author.contains(",")) {
                    System.out.println("Please input one author at a time");
                } else {
                    authors.add(author);
                    valid = true;
                }
            }while (!valid);
            boolean isYesOrNoInput;
            do {
                char inCh = in.next().charAt(0);
                isYesOrNoInput = true;
                if ((inCh == 'y') || (inCh == 'Y')) {
                    continueInput = true;
                } else if ((inCh == 'n') || (inCh == 'N')) {
                    continueInput = false;
                    System.out.println("Moving on...");
                } else {
                    System.out.println("******** Wrong input. Try again.");
                    isYesOrNoInput = false;
                }
            } while (!isYesOrNoInput);
        }while (continueInput);
        String[] authorsArray = authors.toArray(new String[0]);
        return authorsArray;
    }

    public void borrowBook() {
        //get inputs, then pass other borrowBook()
        //inputs being book title, author first name, author second name
        //use input methods below
        String name = inputString("Please input the name of the book:");
        boolean invalid = true;
        String firstName ="";
        String secondName= "";
        do {
            String fullName = inputString("Please input the author's full name");
            String[] names = fullName.split(" ");
            try{
                firstName = names[0];
                secondName = names[1];
                invalid = false;
            }
            catch (IndexOutOfBoundsException e){
                System.out.println("Please be sure to input both first and second names separated by a space.");
            }
        }while(invalid);
        borrowBook(name, firstName, secondName);
    }


    public void borrowBook(String name, String authorFirstName, String authorSecondName) {

        //getting additional inputs

        boolean continueBorrow = false;
        boolean repeatInput = true;
        boolean isYesOrNoInput;
        Scanner in = new Scanner(System.in);
        ArrayList<Member> member = inputMemberName();
        do {
            if (member.size() == 0) {
                break;
            }

            System.out.println("Found the following member");
            showMember(member.get(0));
            System.out.println("Is this You? (Y/N)");
            char inCh = in.next().charAt(0);
            isYesOrNoInput = true;
            if ((inCh == 'y') || (inCh == 'Y')) {
                if (member.get(0).getBorrowedBooks() >=5){
                    System.out.println("You have too many borrowed books already. Return some before borrowing more.");
                    break;
                }
                continueBorrow = true;
                repeatInput = false;
            } else if ((inCh == 'n') || (inCh == 'N')) {
                System.out.println("Would you like to re-enter your name? (Y/N)");
                do {
                    inCh = in.next().charAt(0);
                    isYesOrNoInput = true;
                    if ((inCh == 'y') || (inCh == 'Y')) {
                        member = inputMemberName();
                    } else if ((inCh == 'n') || (inCh == 'N')) {
                        System.out.println("Returning to menu...");
                        repeatInput = false;
                    } else {
                        System.out.println("******** Wrong input. Try again.");
                        isYesOrNoInput = false;
                    }
                } while (!isYesOrNoInput);
            } else {
                System.out.println("******** Wrong input. Try again.");
                isYesOrNoInput = false;
            }
        } while (!isYesOrNoInput || repeatInput);

        if (continueBorrow) {
            borrowOperation(name, authorFirstName, authorSecondName, member.get(0));
        }
    }

    public void borrowOperation(String name, String authorFirstName, String authorSecondName, Member member) {
        Scanner in = new Scanner(System.in);
        Boolean isYesOrNoInput;
        ArrayList<Book> titleSearchResults = getBookSearchResults(name);
        ArrayList<Book> fullSearchResults = new ArrayList<>();
        for (Book book : titleSearchResults) {
            for (String author : book.getAuthors()) {
                //Only returns results if both first name and second name are partial matches
                if (author.toLowerCase().contains(authorFirstName.toLowerCase()) &&
                        author.toLowerCase().contains(authorSecondName.toLowerCase())) {
                    fullSearchResults.add(book);
                }
            }
        }
        if (fullSearchResults.size() > 0) {
            Book book = fullSearchResults.get(0);
            System.out.println("Book '" + book.getTitle() + "' selected.");


            System.out.println("Would you like to borrow this book? (Y/N)");
            do {
                char inCh = in.next().charAt(0);
                isYesOrNoInput = true;
                if ((inCh == 'y') || (inCh == 'Y')) {
                    if (fullSearchResults.size() > 0 && fullSearchResults.get(0).getTotalQty() > 0) {
                        int id =getNewBookLoanId();
                        loanList.add(new Loan( book.getId(), member.getId(), id,LocalDate.now()));
                        System.out.printf("Created new loan with Id: %s",id);

                    } else if (fullSearchResults.get(0).getTotalQty() < 1) {
                        System.out.println("There are no available copies of the book '" + book.getTitle() + "'.");
                    }

                } else if ((inCh == 'n') || (inCh == 'N')) {

                    System.out.println("Returning to menu...");

                } else {
                    System.out.println("******** Wrong input. Try again.");
                    isYesOrNoInput = false;
                }
            } while (!isYesOrNoInput);


        } else {//no search results
            System.out.println("No search results found.");
        }

        System.out.println("Would you like to select another book? (Y/N)");
        //do yes no
        in = new Scanner(System.in);
        do {
            char inCh = in.next().charAt(0);
            isYesOrNoInput = true;
            if ((inCh == 'y') || (inCh == 'Y')) {

                borrowOperation(inputString("Please enter the name of the book:"),
                        inputString("Please enter the first name of the author:" ),
                        inputString("Please enter the second name of the author:"),
                        member);

            } else if ((inCh == 'n') || (inCh == 'N')) {
                System.out.println("Returning to menu...");
            } else {
                System.out.println("******** Wrong input. Try again.");
                isYesOrNoInput = false;
            }
        } while (!isYesOrNoInput);
    }

    public void returnBook() {
        returnBook(inputInt("Please input the loan ID:"));
    }



    public void returnBook(int id) {
        Scanner in = new Scanner(System.in);
        Boolean isYesOrNoInput;
        Boolean returnComplete = false;
        do {
            Loan loan = getLoanSearchResult(id);
            if (loan == null) {
                System.out.println("No matching loans found.");
                System.out.println("Would you like to re-enter the ID? (Y/N)");
                do {
                    char inCh = in.next().charAt(0);
                    isYesOrNoInput = true;
                    if ((inCh == 'y') || (inCh == 'Y')) {
                        id = inputInt("Please input the loan ID:");
                    } else if ((inCh == 'n') || (inCh == 'N')) {
                        System.out.println("Returning to menu...");
                        return;
                    } else {
                        System.out.println("******** Wrong input. Try again.");
                        isYesOrNoInput = false;
                    }
                } while (!isYesOrNoInput);
            } else {
                System.out.printf("You are returning '%s', is this correct? (Y/N)\n", loan.getTitle());
                do {
                    char inCh = in.next().charAt(0);
                    isYesOrNoInput = true;
                    if ((inCh == 'y') || (inCh == 'Y')) {
                        handleFine(loan.getBorrowDate());
                        Book book = getBookSearchResults(loan.getTitle()).get(0);
                        book.changeAvailableQty(1);
                        loanList.remove(loan);
                        returnComplete = true;
                    } else if ((inCh == 'n') || (inCh == 'N')) {
                        System.out.println("Cancelling and returning to menu...");
                        return;
                    } else {
                        System.out.println("******** Wrong input. Try again.");
                        isYesOrNoInput = false;
                    }
                } while (!isYesOrNoInput);
            }
        }while(!returnComplete);
    }

    public void addNewBook(){
        String name = inputString("Please input the book name:");
        String[] authors = inputAuthors();
        int year = inputInt("Please enter the year the book was published:");
        int quantity = inputInt("Please enter the number of copies of this book");
        addNewBook(name,authors,year,quantity);
    }

    public void addNewBook(String name, String[] authors, int year, int quantity) {
        ArrayList<Book> searchResults = getBookSearchResults(name);
        if (searchResults.size() != 0 && searchResults.size() < 2) {
            searchResults.get(0).addAdditionalBooks(quantity);
        } else if (searchResults.size() == 0) {

            bookList.add(new Book(getNewBookId(), name, authors, year, quantity));
        } else {
            System.out.println("Search returned multiple entries. Please be more precise.");
        }
    }

    public void addNewMember(){
        boolean invalid = true;
        String firstName ="";
        String secondName= "";
        do {
            String fullName = inputString("Please input the new member's full name");
            String[] names = fullName.split(" ");
            try{
                firstName = names[0];
                secondName = names[1];
                invalid = false;
            }
            catch (IndexOutOfBoundsException e){
                System.out.println("Please be sure to input both first and second names separated by a space.");
            }
        }while(invalid);

        addNewMember(firstName, secondName, LocalDate.now());
    }


    public void addNewMember(String firstName, String secondName, LocalDate date) {
        memberList.add(new Member(getNewMemberId(), firstName, secondName, date));
    }

    public int getNewBookId() {
        int highest = 0;

        for (Book book : bookList) {
            int currentId = book.getId();
            if (currentId > highest) {
                highest = currentId;
            }
        }
        int newId = highest + 1;
        return newId;
    }

    public int getNewMemberId() {
        int highest = 0;

        for (Member member : memberList) {
            int currentId = member.getId();
            if (currentId > highest) {
                highest = currentId;
            }
        }
        int newId = highest + 1;
        return newId;
    }
    public int getNewBookLoanId() {
        int highest = 0;

        for (Loan loan : loanList) {
            int currentId = loan.getId();
            if (currentId > highest) {
                highest = currentId;
            }
        }
        int newId = highest + 1;
        return newId;
    }

    public void changeQuantity(){
        changeQuantity( inputString("Please input the name of the book:"),
                inputInt("Please input the number of copies to add or remove (remove designated by '-' sign)"));
    }
    public void changeQuantity(String name, int quantity) {

        Book book;
        try {
            book = getBookSearchResults(name).get(0);
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("Search returned no matches, returning to menu...");
            return;
        }

        if ((book.getAvailableQty() + quantity) >= 0) {
            book.changeAvailableQty(quantity);
        } else {
            System.out.println("There are not enough available books of that name to take that many.");
        }
    }


    private boolean checkfine(LocalDate borrowDate) {
        LocalDate currentDate = LocalDate.now();
        int daysBetween = (int) DAYS.between(borrowDate, currentDate);
        boolean overdue = false;
        if (daysBetween > 30) {
            overdue = true;
        }
        return overdue;
    }


    private double calcFineValue(LocalDate borrowDate) {
        LocalDate currentDate = LocalDate.now();
        int daysBetween = (int) DAYS.between(borrowDate, currentDate);
        double fine = (daysBetween - 30) * 0.1;
        return fine;
    }


    public Boolean handleFine(LocalDate borrowDate) {
        boolean canReturn = true;
        if (checkfine(borrowDate)) {
            System.out.println("This book is overdue and has accumulated a fine of " + calcFineValue(borrowDate));

            Scanner in = new Scanner(System.in);
            boolean isYesOrNoInput;

            do {
                System.out.println("Would you like to pay this fine now? (Y/N)");
                char inCh = in.next().charAt(0);
                isYesOrNoInput = true;
                if ((inCh == 'y') || (inCh == 'Y')) {
                    System.out.println("Fine paid.");
                    canReturn = true;
                } else if ((inCh == 'n') || (inCh == 'N')) {
                    System.out.println("Fine not paid, book return unsuccessful");
                    System.out.println("Please try again when you can pay the fine.");
                    canReturn = false;

                } else {
                    System.out.println("******** Wrong input. Try again.");
                    isYesOrNoInput = false;
                }

            } while (!isYesOrNoInput);

        }
        //return to returnbook()
        return canReturn;
    }


}
