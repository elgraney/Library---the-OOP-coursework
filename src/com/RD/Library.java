package com.RD;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Creates a simulation of a library containing a list of library books, library
 * members, and current book loans. Additionally contains all methods related to
 * handling books, members and loans in the general case.
 *
 * @version 09/03/2018
 */


public class Library {

    /**
     * The ArrayList containing all books known to the library
     */
    private ArrayList<Book> bookList = new ArrayList<Book>();
    /**
     * The ArrayList containing all members known to the library
     */
    private ArrayList<Member> memberList = new ArrayList<Member>();
    /**
     * The ArrayList containing all loans currently in place
     */
    private ArrayList<Loan> loanList = new ArrayList<Loan>();


    /**
     * Initialises the library with all books, members and loans imported from test files.
     * Following this adds additional data to all loans, books and members, through further function calls.
     *
     * @param  file1  the file containing all books
     * @param  file2  the file containing all members
     * @param  file3  the file containing all loans
     */
    public Library(String file1, String file2, String file3) {
        try {
            //import all 3 files
            importBooksFile(file1);
            importMembersFile(file2);
            importLoansFile(file3);

            nameLoans();//add a name to each loan object
            calculateAvailableCopies();//calculate how many books are available and how many are on loan
            countLoansPerMember(); //count the number of loans for each member

        } catch (IOException e) {//if files fail to load
            throw new RuntimeException("Error in text files: error while loading. Program terminating.");
        }
    }

    /**
     * Checks every loan and totals the number of loans belonging to each known member.
     * saves said total within each relevant Member object
     */
    private void countLoansPerMember(){
        for (Loan loan : loanList) {
            for (Member member : memberList) {
                if (loan.getMemberId() == member.getId()) {
                    member.addBorrowedBooks(1);
                    if (member.getBorrowedBooks()> 5) { //checks if user is borrowing more than 5 books at a time
                        //should never happen; would mean files are saved incorrectly
                        throw new RuntimeException("Error in text files: More borrowed books than should be allowed.");
                    }
                }
            }
        }
    }

    /**
     * Cross references the book ID within a Loan object with the book ID within a Book object.
     * Uses the found Book object to pass the title of the book to the Loan, saving it there.
     */
    private void nameLoans(){
        for (Loan loan : loanList){
            for (Book book : bookList){
                if (loan.getBookId() == book.getId()){
                    loan.setTitle(book.getTitle()); //adds the book title to each loan object
                }
            }
        }
    }

    /**
     * Counts the number of loans of each type of book.
     * Deducts the number of loans from the total available copies of each relevant book.
     */
    private void calculateAvailableCopies() {
        for (Book book : bookList) {
            int available = book.getTotalQty();
            for (Loan loan : loanList) {
                if (loan.getBookId() == book.getId()) {
                    available -= 1;
                    if (available < 0) { //checks if there are more loans than total of a book
                        throw new RuntimeException("Error in text files: More loans of a book than total copies.");
                    }
                }
            }
            book.changeAvailableQty(available); //increase/decrease available count
        }
    }

    /**
     *Handles the reading of the book.txt file.
     *Creates a new Book object for each new book, storing all metadata within it.
     *@throws IOException if file can't be accessed for any reason
     *@param path the path to the txt file
     */
    private void importBooksFile(String path) throws IOException {
        BufferedReader br = null;
        br = new BufferedReader(new FileReader(path));
        String line = null;

        while ((line = br.readLine()) != null) {//read each line in text file
            String[] values = line.split(",");//split words separated by ','
            bookList.add(new Book(Integer.parseInt(values[0]), values[1], seperateAuthors(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4])));
            //create new book object with file data
        }
        br.close();
    }

    /**
     * Authors are stored as a single string separated by ':'
     * This method break that string down into an array of substrings
     * each substring is a full author name
     *
     * @param authorString the single string containing all author names
     * @return String array of author names
     */
    private String[] seperateAuthors(String authorString) {
        String[] authors = authorString.split(":");//separates authors by the ':' between them
        return authors;
    }

    /**
     * Handles the reading of the members.txt file.
     * Creates a new Member object for each new member, storing all metadata within it.
     *@throws IOException if file can't be accessed for any reason
     * @param path the path to the txt file
     */
    private void importMembersFile(String path) throws IOException {
        BufferedReader br = null;
        br = new BufferedReader(new FileReader(path));
        String line = null;

        while ((line = br.readLine()) != null) {//read each line in text file
            String[] values = line.split(",");//split words separated by ','
            memberList.add(new Member(Integer.parseInt(values[0]), values[1], values[2], LocalDate.parse(values[3])));
            //create new member object with file data
        }
        br.close();
    }

    /**
     *Handles the reading of the loans.txt file.
     *Creates a new Loan object for each new loan, storing all metadata within it.
     *@throws IOException if file can't be accessed for any reason
     *@param path the path to the txt file
     */
    private void importLoansFile(String path) throws IOException {
        BufferedReader br = null;
        br = new BufferedReader(new FileReader(path));
        String line = null;

        while ((line = br.readLine()) != null) {//read each line in text file
            String[] values = line.split(",");//split words separated by ','
            loanList.add(new Loan(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]) , LocalDate.parse(values[3])));
            //create new loan object with file data
        }
        br.close();
    }

    /**
     * loops through every book in the library and calls showBook to display its data.
     */
    public void showAllBooks() {
        System.out.println("Displaying all books:");
        for (int i = 0; i < bookList.size(); i++) {
            showBook(bookList.get(i));
        }
    }

    /**
     * takes a book and prints its title, id and authors to console.
     * @param book the book object to display
     */
    private void showBook(Book book) {
        System.out.printf("-Title: %s, ID: %s, Authors: %s\n",
                book.getTitle(), book.getId(), (String.join(", ", (book.getAuthors()))));
    }

    /**
     * loops through every member in the library and calls showMember to display its data.
     */
    public void showAllMembers() {
        System.out.println("Displaying all members:");
        for (int i = 0; i < memberList.size(); i++) {
            showMember(memberList.get(i));
        }
    }

    /**
     * takes a member and prints its name, id and join date to console.
     * @param member the member object to display
     */
    private void showMember(Member member) {
        System.out.printf("-Name: %s, ID: %s, Join Date: %s\n",
                member.getFirstName() + " " + member.getSecondName(), member.getId(), member.getDateJoin());
    }

    /**
     * loops through every loan in the library and calls showBookLoan to display its data.
     */
    public void showAllBookLoans() {
        System.out.println("Displaying all loans:");
        for (int i = 0; i < loanList.size(); i++) {
            showBookLoan(loanList.get(i));
        }
    }

    /**
     * takes a loan and prints the title of the book, loan id, book id, member id,
     * borrow date and due date to console.
     * @param loan the loan object to display
     */
    private void showBookLoan(Loan loan) {
        System.out.printf("-Title: %s, Loan ID: %s, Book ID: %s, Member ID: %s, Borrow Date: %s, Due Date: %s\n",
                loan.getTitle(), loan.getId(),loan.getBookId(), loan.getMemberId(), loan.getBorrowDate(), loan.getReturnDate());
    }

    /**
     * Saves all data within each of the 3 different library ArrayLists to a text file each
     *
     * @param file1 the file path for the books data
     * @param file2 the file path for the members data
     * @param file3 the file path for the loans data
     */
    public void saveChanges(String file1, String file2, String file3) {
        BufferedWriter writer = null;
        try { //writes book data to books.txt
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file1), "utf-8"));
            for (int i = 0; i < bookList.size(); i++) {
                Book book = bookList.get(i);
                //writes in proper format
                writer.write(book.getId() + "," + book.getTitle() + "," + String.join(":", (book.getAuthors())) + "," + book.getYear() + "," + book.getTotalQty());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) { //IO exception means the file couldn't be written to properly
            System.out.println("IOException: Saving books data failed");
        }
        try { //writes member data to members.txt
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2), "utf-8"));
            for (int i = 0; i < memberList.size(); i++) {
                Member member = memberList.get(i);
                writer.write(member.getId() + "," + member.getFirstName() + "," + member.getSecondName() + "," + member.getDateJoin());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("IOException: Saving members data failed");
        }
        try { //writes loan data to loans.txt
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file3), "utf-8"));
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

    /**
     *Gets user input to pass to the alternative searchBook method
     */
    public void searchBook() {
        searchBook(inputString("Please enter the name of the book:"));
    }

    /**
     * gets search results then displays extended data about each result
     * data includes Title, ID, Authors, Totatl copies and Available copies
     * @param name the title of the book
     */
    public void searchBook(String name) {
        //this function specifically prints the search results found in getBookSearchResults
        ArrayList<Book> searchResults = getBookSearchResults(name); //gets books with matching names
        System.out.println("Search Results:");
        if (searchResults.size() > 0) { //if there are search results
            for (int i = 0; i < searchResults.size(); i++) {
                Book book = searchResults.get(i);
                System.out.printf("Title: %s, Id: %s, Authors: %s, Total copies: %s, Available copies: %s\n",
                        book.getTitle(), book.getId(),String.join(", ", (book.getAuthors())) , book.getTotalQty(), book.getAvailableQty());
            }
        } else { //if there are no search results
            System.out.println("No matches found");
        }
    }

    /**
     * Iterates through every book in booklist checking if each of their titles contains the string 'name'.
     * If it does, add that book to an ArrayList of search results.
     * @param name the title of the book
     * @return the ArrayList of matching books
     */
    private ArrayList<Book> getBookSearchResults(String name) {
        ArrayList<Book> searchResults = new ArrayList<Book>();
        for (int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            //if book title contains any substring of 'name' (not case sensitive)
            if (book.getTitle().toLowerCase().contains(name.toLowerCase())) {
                searchResults.add(book);
            }
        }
        return searchResults;
    }

    /**
     *Gets user input to pass to the alternative searchMember method
     */
    public void searchMember() {
        boolean invalid = true;
        String firstName ="";
        String secondName= "";
        do {
            String fullName = inputString("Please input the member's full name");
            String[] names = fullName.split(" ");
            try{//should at least two entries in the names array
                firstName = names[0];
                secondName = names[1];
                invalid = false;
            }
            catch (IndexOutOfBoundsException e){//less than 2 entries in the names array
                //means the user has not entered 2 names separated by a space
                System.out.println("Please be sure to input both first and second names separated by a space.");
            }
        }while(invalid);
        searchMember(firstName, secondName);

    }

    /**
     * Gets search results then displays extended data about each result.
     * Data includes First name, Second name, ID, and Join date.
     * Additionally, the number of loans under the members are found.
     * Data about these loans are then displayed.
     * This includes Book title, Book ID, Loan ID Borrow date and Return date
     * as well as any relevant fine warnings
     * @param firstName the first name of the member
     * @param secondName the second name of the member
     */
    public void searchMember(String firstName, String secondName) {

        ArrayList<Member> searchResults = getMemberSearchResults(firstName, secondName);//finds matching members
        System.out.println("Search Results:");
        if (searchResults.size() > 0) {
            for (int i = 0; i < searchResults.size(); i++) {
                Member member = searchResults.get(i);
                //outputs member data in this format
                System.out.printf("Name: %s %s, ID: %s, Join Date: %s\n",
                        member.getFirstName(), member.getSecondName(), member.getId(), member.getDateJoin());
                System.out.println("Current loans:");
                ArrayList<Loan> loans = new ArrayList<>();
                for (Loan loan : loanList) {
                    if (loan.getMemberId() == member.getId()) { //checks if member ID in loan matches member ID in member
                        loans.add(loan);
                    }
                }
                System.out.printf("You currently have %d borrowed books\n", loans.size());
                for (Loan loan : loans) { //for each loan, output data about it
                    System.out.printf("Title: %s, Book ID: %s, Loan ID: %s, Borrow Date: %s, Return Date: %s\n",
                            loan.getTitle(), loan.getBookId(), loan.getId(), loan.getBorrowDate(), loan.getReturnDate());
                    if (checkfine(loan.getBorrowDate())) { //if the book is overdue
                        System.out.printf("This book is overdue and has accumulated a fine of £%.2f\n",
                                calcFineValue(loan.getBorrowDate())); //work out and output the value of the fine
                    }
                }
            }
        } else {
            System.out.println("No matches found");
        }
    }

    /**
     * Iterates through every member in memberList checking if each of their titles contains a
     * substring of both the first and second name
     * If it does, add that member to an ArrayList of search results.
     * @param firstName the first name of the member
     * @param secondName the second name of the member
     * @return ArrayList of matching members
     */
    public ArrayList<Member> getMemberSearchResults(String firstName, String secondName) {
        ArrayList<Member> searchResults = new ArrayList<Member>();
        for (int i = 0; i < memberList.size(); i++) {
            Member member = memberList.get(i);
            //checks if both member first name and member second name contain a substring of firstName and
            //secondName respectively (not case sensitive
            if (member.getFirstName().toLowerCase().contains(firstName.toLowerCase()) &&
                    member.getSecondName().toLowerCase().contains(secondName.toLowerCase())) {
                searchResults.add(member);
            }
        }
        return searchResults;
    }
    /**
     * Iterates through every loan in loanList checking if its id matches the passed id
     * If it does, return that loan immediately
     * @param id the unique ID of the loan
     * @return a matching loan if a match is found, null if no matching loan is found
     */

    public Loan getLoanSearchResult(int id) {

        for (int i = 0; i < loanList.size(); i++) {
            Loan loan = loanList.get(i);
            if (loan.getId() == id) {
                return loan;
            }
        }
        return null;
    }

    /**
     * Gets user input to try and find an existing member.
     * If no matches are found, allows input to be retried
     * @return ArrayList of matching members
     */

    private ArrayList<Member> inputMemberName() {
        Boolean inputPassed = false;
        Scanner in = new Scanner(System.in);
        ArrayList<Member> member = memberList;
        while (!inputPassed) {
            System.out.println("Please input your name:");
            member = getMemberSearchResults(in.next(), in.next());//input first name and second name
            if (member.size() > 0) {//there are matching members
                inputPassed = true;
            } else { // there are no matching members
                System.out.println("No matching members. Would you like to re-enter your name? (Y/N)");
                Boolean isYesOrNoInput;
                do {//check if the user wants to try again
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

    /**
     * Prompts user for any sort of simple string input (book title, member name, etc).
     * @param message a string that is displayed to the user (eg 'please enter the book title:')
     * @return the string user input
     */
    public String inputString(String message) {
        System.out.println(message);
        Scanner in = new Scanner(System.in);
        String string = in.nextLine();
        return string;
    }
    /**
     * Prompts user for any sort of simple int input (book id, quantity, etc.
     * Checks input is integer
     * @param message a string that is displayed to the user (eg 'please enter the book ID:')
     * @return the int of the user input
     */
    private int inputInt(String message) {
        boolean valid = false;
        int intId = 0;
        do{
            System.out.println(message);
            Scanner in = new Scanner(System.in);
            String id = in.next();

            try {//inputs what should be an integer
                intId = Integer.parseInt(id);
                valid =true;
            }catch(NumberFormatException e){//catches if input is not integer
                System.out.println("The input must be entirely numerical; non numerical characters are not allowed.");
            }
        }while (!valid);
        return intId;
    }

    /**
     * Asks the user to input author names.
     * Allows multiple authors to be entered one after the other
     * @return an array of author names that the user has entered
     */
    private String[] inputAuthors(){
        boolean continueInput = true;
        boolean valid = false;
        ArrayList<String> authors = new ArrayList<String>(); //arraylist because final size is unknown
        do{//loops while more authors are still being added
            Scanner in = new Scanner(System.in);
            do {//makes sure each author input is in the right format
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
            do {//check if the user wants to add more authors
                System.out.println("Would you like to add additional authors?");
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
        String[] authorsArray = authors.toArray(new String[0]); //convert to an array to work with other methods
        return authorsArray;
    }

    /**
     * Prompts the user for inputs to pass to the other borrowBook method
     * including Book name and Author name
     */
    public void borrowBook() {
        String name = inputString("Please input the name of the book:");
        boolean invalid = true;
        String firstName ="";
        String secondName= "";
        do {
            String fullName = inputString("Please input the author's full name");
            String[] names = fullName.split(" ");
            try{//should at least two entries in the names array
                firstName = names[0];
                secondName = names[1];
                invalid = false;
            }
            catch (IndexOutOfBoundsException e){//less than 2 entries in the names array
                //means the user has not entered 2 names separated by a space
                System.out.println("Please be sure to input both first and second names separated by a space.");
            }
        }while(invalid);
        borrowBook(name, firstName, secondName);
    }

    /**
     *  First asks the user to enter the name of a member, the one who is borrowing the book.
     *  Then checks if that member has over 5 loans already; if so cancel, if not continue.
     *  Allows the user to re-enter the member if no matches are found
     *  Finally calls borrowBookOperation to complete the borrowing process
     *
     * @param name the title of the book
     * @param authorFirstName the authors first name
     * @param authorSecondName the authors second name
     */

    public void borrowBook(String name, String authorFirstName, String authorSecondName) {
        boolean continueBorrow = false;
        boolean repeatInput = true;
        boolean isYesOrNoInput;
        Scanner in = new Scanner(System.in);
        ArrayList<Member> member = inputMemberName();
        do {
            if (member.size() == 0) { // user cancelled inputting member name
                System.out.println("Returning to menu");
                return;
            }
            System.out.println("Found the following member");
            showMember(member.get(0));
            System.out.println("Is this You? (Y/N)");
            char inCh = in.next().charAt(0);
            isYesOrNoInput = true;
            if ((inCh == 'y') || (inCh == 'Y')) {
                if (member.get(0).getBorrowedBooks() >=5){ //not allowed to borrow more if member already has 5 loans
                    System.out.println("You have too many borrowed books already. Return some before borrowing more.");
                    break;
                }
                continueBorrow = true;
                repeatInput = false;
            } else if ((inCh == 'n') || (inCh == 'N')) { //offers option to retry
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

        if (continueBorrow) {//if all previous input checks pass, borrow book
            borrowOperation(name, authorFirstName, authorSecondName, member.get(0));
        }
    }

    /**
     *  Searches for the book based on the name and authors.
     *  Creates a new loan with the search result book and the member and saves it to loanList
     *  Allows user to borrow another book if they do not already have 5 or more loans
     *
     * @param name the title of the book
     * @param authorFirstName the authors first name
     * @param authorSecondName the authors second name
     * @param member the Member object that is borrowing the book
     */

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
        if (fullSearchResults.size() > 0) {//if there are matching search results
            Book book = fullSearchResults.get(0);
            System.out.println("Book '" + book.getTitle() + "' selected.");

            System.out.println("Would you like to borrow this book? (Y/N)");
            do {//checks with user if they want to proceed
                char inCh = in.next().charAt(0);
                isYesOrNoInput = true;
                if ((inCh == 'y') || (inCh == 'Y')) {
                    if (fullSearchResults.size() > 0 && fullSearchResults.get(0).getTotalQty() > 0) {
                        int id =getNewBookLoanId();
                        loanList.add(new Loan( book.getId(), member.getId(), id,LocalDate.now()));
                        member.addBorrowedBooks(1);
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
        in = new Scanner(System.in);
        do {
            char inCh = in.next().charAt(0);
            isYesOrNoInput = true;
            if ((inCh == 'y') || (inCh == 'Y')) {
                if(member.getBorrowedBooks() < 5) {//must check again if member has 5 or more loans already
                    borrowOperation(inputString("Please enter the name of the book:"),
                            inputString("Please enter the first name of the author:"),
                            inputString("Please enter the second name of the author:"),
                            member);
                }
                else{ //has 5 or more loans, so is not allowed to borrow more
                    System.out.println("You have too many existing loans to borrow another book.");
                    System.out.println("Returning to menu...");
                }

            } else if ((inCh == 'n') || (inCh == 'N')) {
                System.out.println("Returning to menu...");
            } else {
                System.out.println("******** Wrong input. Try again.");
                isYesOrNoInput = false;
            }
        } while (!isYesOrNoInput);
    }

    /**
     * Prompts the user to input a loan ID then passes this to the other returnBook method
     */
    public void returnBook() {
        returnBook(inputInt("Please input the loan ID:"));
    }


    /**
     * Uses the id to identify a loan.
     * Allows the user to re-enter an id, if it is incorrect
     * Returns the book by deleting the loan from the loanList, changing the number of available copies
     * for that book and by changing the number of borrowed books a member has.
     * @param id
     */
    public void returnBook(int id) {
        Scanner in = new Scanner(System.in);
        Boolean isYesOrNoInput;
        Boolean returnComplete = false;
        do {
            Loan loan = getLoanSearchResult(id);
            if (loan == null) { //no matching search results found
                System.out.println("No matching loans found.");
                System.out.println("Would you like to re-enter the ID? (Y/N)");
                do { //get yes/no input
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
            } else { //valid matching loans exist
                System.out.printf("You are returning '%s', is this correct? (Y/N)\n", loan.getTitle());
                do {
                    char inCh = in.next().charAt(0);
                    isYesOrNoInput = true;
                    if ((inCh == 'y') || (inCh == 'Y')) {
                        handleFine(loan.getBorrowDate());
                        Book book = getBookSearchResults(loan.getTitle()).get(0);
                        Member borrowerMember = null;
                        for (Member member : memberList) {
                            if (loan.getMemberId() == member.getId()) {//finds the member the loan belongs to
                                borrowerMember = member;
                            }
                        }
                        book.changeAvailableQty(1);
                        loanList.remove(loan);
                        borrowerMember.addBorrowedBooks(-1);
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

    /**
     * Prompts the user for the following inputs:
     * Book title, Authors, year of publishing, book quantity.
     * Then call other addNewBook method
     */
    public void addNewBook(){
        String name = inputString("Please input the book name:");
        String[] authors = inputAuthors();
        int year = inputInt("Please enter the year the book was published:");
        int quantity = inputInt("Please enter the number of copies of this book");
        addNewBook(name,authors,year,quantity);
    }

    /**
     * Uses inputs to either add to the quantity of an existing book
     * or to create a new book with new data.
     * @param name the name of the book
     * @param authors the authors of the book
     * @param year the year the book was published
     * @param quantity the number of books to add to the library
     */
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

    /**
     * Prompts the user for the following inputs:
     * Member first name, Member second name.
     * Passes these inputs to the other addNewMember method
     */

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

    /**
     * Uses inputs to create a new instance of the Member class with that data
     * @param firstName the member's first name
     * @param secondName the member's second name
     * @param date the current date
     */
    public void addNewMember(String firstName, String secondName, LocalDate date) {
        memberList.add(new Member(getNewMemberId(), firstName, secondName, date));
    }

    /**
     * Finds a new unique book id by adding 1 to the highest existing id
     * @return the new id
     */
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

    /**
     * Finds a new unique member id by adding 1 to the highest existing id
     * @return the new id
     */
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

    /**
     * Finds a new unique loan id by adding 1 to the highest existing id
     * @return the new id
     */
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

    /**
     * Prompts the user to input an integer by which to change the quantity of a book
     */
    public void changeQuantity(){
        changeQuantity( inputString("Please input the name of the book:"),
                inputInt("Please input the number of copies to add or remove (remove designated by '-' sign)"));
    }

    /**
     * Uses the book name to identify a book, then changes it's quantity
     * @param name the book's title
     * @param quantity the number by which to change the quantity (+ is increase, - is decrease quantity)
     */
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

    /**
     * Works out if a book is overdue, by calculating the days between its borrow date and the current date
     * The book is overdue after 30 days
     * @param borrowDate the date the book was borrowed
     * @return true if book is overdue, false if not
     */
    private boolean checkfine(LocalDate borrowDate) {
        LocalDate currentDate = LocalDate.now();
        int daysBetween = (int) DAYS.between(borrowDate, currentDate);
        boolean overdue = false;
        if (daysBetween > 30) {
            overdue = true;
        }
        return overdue;
    }

    /**
     * Uses a formula to calculate the value of the fine for an overdue loan
     * @param borrowDate the date the book was borrowed
     * @return the value of the fine
     */
    private double calcFineValue(LocalDate borrowDate) {
        LocalDate currentDate = LocalDate.now();
        int daysBetween = (int) DAYS.between(borrowDate, currentDate);
        double fine = (daysBetween - 30) * 0.1;
        return fine;
    }

    /**
     * Allows the user to pay the fine or to not pay the fine.
     * if the fine is paid, the book return completes,
     * if not, the book return fails
     * @param borrowDate the date the book was borrowed
     * @return true if fine is paid, false if fine is not paid
     */
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
        return canReturn;
    }
}
