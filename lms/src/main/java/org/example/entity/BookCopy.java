package org.example.entity;

public class BookCopy {
    private final String bookId;
    private final String copyId;;
    private CopyStatus copyStatus;

    public BookCopy(String bookId, String copyId, CopyStatus copyStatus) {
        this.bookId = bookId;
        this.copyId = copyId;
        this.copyStatus = copyStatus;
    }

    public void setCopyStatus(CopyStatus copyStatus){
        this.copyStatus = copyStatus;
    }

    public String getBookId(){
        return this.bookId;
    }

    public String getCopyId(){
        return this.copyId;
    }

    public CopyStatus getCopyStatus() {
        return this.copyStatus;
    }

    @Override
    public String toString() {
        return "Member{" +
                "bookID='" + bookId + '\'' +
                ", copyId='" + copyId + '\'' +
                ", copyStatus='" + copyStatus +
                '}';
    }
}
