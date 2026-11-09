package org.example.service;

import org.example.entity.*;
import org.example.repository.BookRepo;
import org.example.repository.IssueBookRepo;
import org.example.repository.MemberRepo;

import java.lang.reflect.Constructor;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class IssueBookService {

    private IssueBookRepo issueBookRepo;
    private MemberRepo memberRepo;
    private BookRepo bookRepo;

    public IssueBookService(IssueBookRepo issueBookRepo, MemberRepo memberRepo, BookRepo bookRepo){
        this.issueBookRepo= issueBookRepo;
        this.memberRepo= memberRepo;
        this.bookRepo = bookRepo;
    }

    public void issueBook(Member member, Book book){
        BookingPolicy bookingPolicy= BookingPolicy.getPolicy(member.getMemberType());
        int issuedCount= Math.toIntExact(issueBookRepo.getActiveIssueCount(member));
        if(issuedCount >= bookingPolicy.getIssueCapacity()){
            System.out.println("Memberhas reached issued limits");
            return;
        }

        if(book.getAvailableCopies()<=0){
            System.out.println("No avaialble copies");
            return;
        }

        BookCopy copy= bookRepo.getAvaialableCopy(book);
        LocalDateTime currentTime = LocalDateTime.now();
        BookLoan bookLoan = new BookLoan(UUID.randomUUID().toString(),
                member.getId(), copy.getCopyId(),
                currentTime, currentTime.plusDays(bookingPolicy.getDaysLimit()), LoanStatus.ACTIVE);
        issueBookRepo.addIssue(member, bookLoan);
        copy.setCopyStatus(CopyStatus.ISSUED);
        book.setAvailableCopies(book.getAvailableCopies()-1);
    }

    public void returnBook(String bookLoanId){
        BookLoan entry = issueBookRepo.getIssueById(bookLoanId);
        if(Objects.isNull(entry)) {
            System.out.println("No entry exist");
            return;
        }
        if(entry.getLoanStatus().equals(LoanStatus.RETURNED)){
            System.out.println("Book has already been returned");
            return;
        }
        Double fetchAmount = fetchAmountToPay(entry);
        entry.setFineAmount(fetchAmount);
        entry.setLoanStatus(LoanStatus.RETURNED);
        BookCopy copy = bookRepo.fetchById(entry.getCopyId());
        System.out.println("this is amount: "+ fetchAmount);
        Book book = bookRepo.bookById(copy.getBookId());
        book.setAvailableCopies(book.getAvailableCopies()+1);
        book.setTotalCopies(book.getTotalCopies()+1);
        copy.setCopyStatus(CopyStatus.AVAILABLE);
    }

    Double fetchAmountToPay(BookLoan bookLoan){
        String memberId= bookLoan.getMemberId();
        Member member= memberRepo.findById(memberId);
        BookingPolicy policy = BookingPolicy.getPolicy(member.getMemberType());

        LocalDateTime issueDate = bookLoan.getIssueDate();
        LocalDateTime dueDate = bookLoan.getDueDate();
        LocalDateTime returnDate = bookLoan.getReturnDate();

        LocalDateTime endDate = returnDate != null
                ? returnDate
                : LocalDateTime.now();

        long totalDays = Math.max(0,
                ChronoUnit.DAYS.between(issueDate.toLocalDate(), endDate.toLocalDate()));

        long overdueDays = Math.max(0,
                ChronoUnit.DAYS.between(dueDate.toLocalDate(), endDate.toLocalDate()));

        long normalAmount = totalDays * policy.getFineAmountPerDay();
        long penaltyAmount = overdueDays * policy.getFinePenaltyAmountPerDay();

        return (double)(normalAmount + penaltyAmount);
    }
}
