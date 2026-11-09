package org.example;

import org.example.entity.*;
import org.example.repository.BookRepo;
import org.example.repository.IssueBookRepo;
import org.example.repository.MemberRepo;
import org.example.service.BookService;
import org.example.service.IssueBookService;
import org.example.service.MemberService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        IssueBookRepo issueBookRepo = new IssueBookRepo();

        // Uses your updated MemberRepo constructor.
        MemberRepo memberRepo = new MemberRepo(issueBookRepo);
        BookRepo bookRepo = new BookRepo();

        MemberService memberService = new MemberService(memberRepo);
        BookService bookService = new BookService(bookRepo);

        IssueBookService issueBookService =
                new IssueBookService(issueBookRepo, memberRepo, bookRepo);

        boolean running = true;

        while (running) {
            System.out.println("\n===== LIBRARY MANAGEMENT =====");
            System.out.println("1. Add member");
            System.out.println("2. Add book");
            System.out.println("3. Search book");
            System.out.println("4. Add book copies");
            System.out.println("5. Issue book");
            System.out.println("6. View member loans");
            System.out.println("7. Return book");
            System.out.println("0. Exit");

            int choice = readInt("Choose an option: ");

            try {
                switch (choice) {

                    case 1 -> {
                        String username = readText("Username: ");
                        String mobile = readText("Mobile number: ");

                        System.out.println("1. Student");
                        System.out.println("2. Faculty");

                        int type = readInt("Member type: ");

                        if (type != 1 && type != 2) {
                            System.out.println("Invalid member type.");
                            break;
                        }

                        MemberType memberType = type == 1
                                ? MemberType.STUDENT
                                : MemberType.FACULTY;

                        memberService.addMember(
                                username, mobile, memberType
                        );
                    }

                    case 2 -> {
                        String name = readText("Book name: ");
                        String author = readText("Author: ");
                        int copies = readPositiveInt("Number of copies: ");

                        bookService.addBook(name, author, copies);
                    }

                    case 3 -> {
                        Book book = selectBook(bookRepo);

                        if (book == null) {
                            break;
                        }

//                        System.out.println("Book ID: " + book.getBookId());
                        System.out.println("Name: " + book.getName());
                        System.out.println("Author: " + book.getAuthorName());
                        System.out.println(
                                "Total copies: " + book.getTotalCopies()
                        );
                        System.out.println(
                                "Available copies: "
                                        + book.getAvailableCopies()
                        );
                    }

                    case 4 -> {
                        Book book = selectBook(bookRepo);

                        if (book == null) {
                            break;
                        }

                        int copies = readPositiveInt("Copies to add: ");
                        bookService.addBookCopies(book, copies);

                        System.out.println(
                                "Available copies: "
                                        + book.getAvailableCopies()
                        );
                    }

                    case 5 -> {
                        Member member = selectMember(memberRepo);

                        if (member == null) {
                            break;
                        }

                        Book book = selectBook(bookRepo);
                        if (book == null) {
                            break;
                        }

                        int before = issueBookRepo
                                .getMemberIssues(member)
                                .size();

                        issueBookService.issueBook(member, book);

                        List<BookLoan> loans =
                                issueBookRepo.getMemberIssues(member);

                        // issueBook() returns void, so check whether
                        // a new loan was actually added.
                        if (loans.size() > before) {
                            BookLoan newLoan = loans.get(loans.size() - 1);

                            System.out.println("Book issued.");
                            System.out.println(
                                    "Loan ID: " + newLoan.getLoanId()
                            );
                            System.out.println(
                                    "Due date: " + newLoan.getDueDate()
                            );
                        }
                    }

                    case 6 -> {
                        Member member = selectMember(memberRepo);

                        if (member == null) {
                            break;
                        }

                        List<BookLoan> loans =
                                issueBookRepo.getMemberIssues(member);

                        if (loans.isEmpty()) {
                            System.out.println("No loans found.");
                            break;
                        }

                        for (BookLoan loan : loans) {
                            System.out.println("--------------------");
                            System.out.println(
                                    "Loan ID: " + loan.getLoanId()
                            );
                            System.out.println(
                                    "Copy ID: " + loan.getCopyId()
                            );
                            System.out.println(
                                    "Status: " + loan.getLoanStatus()
                            );
                            System.out.println(
                                    "Due date: " + loan.getDueDate()
                            );
                        }
                    }

                    case 7 -> {
                        String loanId = readText("Loan ID to return: ");
                        issueBookService.returnBook(loanId);
                    }

                    case 0 -> {
                        running = false;
                        System.out.println("Goodbye!");
                    }

                    default -> System.out.println("Choose a valid option.");
                }

            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Could not complete: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static Member selectMember(MemberRepo memberRepo) {
        System.out.println("choose according what you have");
        System.out.println("1. id");
        System.out.println("2. mobileNo");
        int type = readInt("access type: ");
        if (type != 1 && type != 2) {
            System.out.println("Invalid member type.");
            return null;
        }
        String input = readText("Member ID / lookup key: ");
        Member member = type == 1 ? memberRepo.findMember(input) : memberRepo.findByMobileNo(input);

        if (member == null) {
            System.out.println("Member not found.");
        }

        return member;
    }

    private static Book selectBook(BookRepo bookRepo) {
        String name = readText("Book name: ");
        Book book = bookRepo.findBookByName(name);

        if (book == null) {
            System.out.println("Book not found.");
        }

        return book;
    }

    private static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Input cannot be empty.");
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            String input = readText(prompt);

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid whole number.");
            }
        }
    }

    private static int readPositiveInt(String prompt) {
        while (true) {
            int value = readInt(prompt);

            if (value > 0) {
                return value;
            }

            System.out.println("Enter a number greater than zero.");
        }
    }
}