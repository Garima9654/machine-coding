package org.example.entity;

public class Book {
    public String name;
    public String bookId;
    public String authorName;
    private Integer totalCopies;
    private Integer availableCopies;

    public Book(String name, String bookId, String authorName, Integer totalCopies, Integer availableCopies){
        this.name= name;
        this.authorName= authorName;
        this.bookId = bookId;
        this.totalCopies= totalCopies;
        this.availableCopies= availableCopies;
    }

    public Integer getAvailableCopies() {
        return availableCopies;
    }

    public Integer getTotalCopies(){
        return totalCopies;
    }

    public void setAvailableCopies(Integer availableCopies){
        this.availableCopies= availableCopies;
    }

    public void setTotalCopies(Integer totalCopies){
        this.totalCopies= totalCopies;
    }

    public String getName(){ return this.name; }

    public String getAuthorName(){ return this.authorName; }

    public String getBookId(){ return this.bookId; }

    @Override
    public String toString() {
        return "Member{" +
                "id='" + bookId + '\'' +
                ", book name='" + name + '\'' +
                ", author name='" + authorName + '\'' +
                ", totalCopies=" + totalCopies +
                ", available copies=" + availableCopies +
                '}';
    }
}
