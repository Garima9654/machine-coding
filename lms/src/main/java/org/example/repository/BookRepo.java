package org.example.repository;

import org.example.entity.Book;
import org.example.entity.BookCopy;
import org.example.entity.CopyStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookRepo {
    public static Integer bookID=0;
    public static Integer copyID=0;
    Map<String, List<BookCopy>> bookCopyMap;
    List<Book> listOfBooks;
    List<BookCopy> copyList;

    public BookRepo(){
        bookCopyMap= new HashMap<>();
        listOfBooks= new ArrayList<>();
        copyList= new ArrayList<>();
    }

    public List<BookCopy> fetchAllCopies(){
        return copyList;
    }

    public BookCopy fetchById(String id){
        return copyList.stream().filter(c-> c.getCopyId().equals(id)).findAny().orElse(null);
    }
    public void addBookCopy(Book book, BookCopy bookCopy){
        bookCopyMap.computeIfAbsent(book.getBookId(), k-> new ArrayList<>()).add(bookCopy);
        copyList.add(bookCopy);
    }

    public List<BookCopy> getBookCopy(Book book){
        return bookCopyMap.getOrDefault(book.getBookId(), new ArrayList<>());
    }

    public BookCopy bookCopyById(String id){
        return (BookCopy) bookCopyMap.getOrDefault(id, null);
    }

    public Book bookById(String id){
        return listOfBooks.stream().filter(b->b.getBookId().equals(id)).findAny().orElse(null);
    }

    public BookCopy getAvaialableCopy(Book book){
        List<BookCopy> copies= getBookCopy(book);
        return copies.stream().
                filter(b-> CopyStatus.AVAILABLE.equals(b.getCopyStatus())).
                findFirst().
                orElse(null);
    }

    public Book findBookByName(String bookName){
        return listOfBooks.stream().filter(b-> bookName.equalsIgnoreCase(b.getName())).findFirst().orElse(null);
    }

    public List<Book> findBooksByAuthor(String author){
        return listOfBooks.stream().filter(b-> author.equalsIgnoreCase(b.getAuthorName())).toList();
    }

    public Book findBookByNameAndAuthor(String bookName, String author){
        return listOfBooks.stream().
                filter(b-> bookName.equalsIgnoreCase(b.getName())).
                filter(b->author.equalsIgnoreCase(b.authorName)).
                findFirst().orElse(null);
    }

    public void addBook(Book book) {
        listOfBooks.add(book);
    }

    public boolean deleteBook(Book book){
        boolean removed = listOfBooks.remove(book);

        if (removed) {
            bookCopyMap.remove(book.getBookId());
        }

        return removed;
    }

    public boolean deleteBookCopy(BookCopy bookCopy) {
        String bookId = bookCopy.getBookId();

        Book book = listOfBooks.stream()
                .filter(b -> b.bookId.equalsIgnoreCase(bookId))
                .findFirst()
                .orElse(null);

        if (book == null) {
            return false;
        }

        List<BookCopy> copies = bookCopyMap.get(book.getBookId());

        if (copies == null) {
            return false;
        }

        return copies.removeIf(copy ->
                copy.getCopyId().equalsIgnoreCase(bookCopy.getCopyId())
        );
    }
}