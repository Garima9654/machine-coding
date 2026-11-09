package org.example.repository;

import org.example.entity.BookLoan;
import org.example.entity.LoanStatus;
import org.example.entity.Member;

import java.util.*;

public class IssueBookRepo {
    List<BookLoan> issues;
    Map<String, List<BookLoan>> issuedBooks;

    public IssueBookRepo(){
        issues= new ArrayList<>();
        issuedBooks= new HashMap<>();
    }

    // Add a new loan
    public void addIssue(Member member, BookLoan bookLoan) {
        issues.add(bookLoan);

        issuedBooks.computeIfAbsent(member.getId(), k -> new ArrayList<>())
                .add(bookLoan);
    }

    // Get all loan records
    public List<BookLoan> getAllIssues() {
        return new ArrayList<>(issues);
    }

    // Get all loans of a particular member
    public List<BookLoan> getMemberIssues(Member member) {
        return new ArrayList<>(
                issuedBooks.getOrDefault(member.getId(), Collections.emptyList())
        );
    }

    // Find a loan using loanId
    public BookLoan getIssueById(String loanId) {
        return issues.stream()
                .filter(issue -> issue.getLoanId().equalsIgnoreCase(loanId))
                .findFirst()
                .orElse(null);
    }

    // Count currently active loans of a member
    public long getActiveIssueCount(Member member) {
        return getMemberIssues(member).stream()
                .filter(issue -> issue.getLoanStatus() == LoanStatus.ACTIVE)
                .count();
    }

    // Update loan status
    public boolean updateIssueStatus(String loanId, LoanStatus status) {
        BookLoan issue = getIssueById(loanId);

        if (issue == null) {
            return false;
        }

        issue.setLoanStatus(status);
        return true;
    }

    public void rejectIssues(String memberId){
        issuedBooks.remove(memberId);
    }

}
