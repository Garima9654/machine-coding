package org.example.entity;

import java.time.LocalDateTime;

public class BookLoan {
    private final String loanId;
    private final String memberId;
    private final String copyId;
    private final LocalDateTime issueDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;
    private Double fineAmount;
    private LoanStatus loanStatus;

    public BookLoan(String loanId, String memberId, String copyId, LocalDateTime issueDate, LocalDateTime dueDate, LoanStatus loanStatus) {
        this.loanId = loanId;
        this.memberId = memberId;
        this.copyId = copyId;
        this.issueDate = issueDate;
        this.dueDate = dueDate ; //need to add return date according to member
        this.loanStatus= loanStatus;
    }

    public String getLoanId() {
        return loanId;
    }

    public LoanStatus getLoanStatus(){
        return loanStatus;
    }

    public void setLoanStatus(LoanStatus loanStatus){
        this.loanStatus= loanStatus;
    }

    public void setReturnDate(LocalDateTime time){
        this.returnDate= time;
    }

    public String getCopyId(){
        return copyId;
    }

    public String getMemberId() {
        return memberId;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setFineAmount(Double fineAmount) {
        this.fineAmount = fineAmount;
    }

    @Override
    public String toString() {
        return "BookLoan{" +
                "loanId='" + loanId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", copyId='" + copyId + '\'' +
                ", issueDate=" + issueDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                ", fineAmount=" + fineAmount +
                ", loanStatus=" + loanStatus +
                '}';
    }
}
