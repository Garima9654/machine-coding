package org.example.service;

import org.example.entity.Book;
import org.example.entity.BookCopy;
import org.example.entity.CopyStatus;
import org.example.repository.BookRepo;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BookService {

    private BookRepo bookRepo;
    public BookService(BookRepo bookRepo){
        this.bookRepo= bookRepo;
    }

    public Boolean Avaialablebooks(String name){
        Book book = bookRepo.findBookByName(name);
        List<BookCopy> bookCopyList= bookRepo.getBookCopy(book);
        if(bookCopyList.isEmpty()){
            System.out.println("Sorry no copies avaialble");
            return false;
        }

        BookCopy bookCopy= bookCopyList.stream().
                filter(bookCopy1 -> bookCopy1.getCopyStatus().equals(CopyStatus.AVAILABLE)).
                findFirst().orElse(null);
        return bookCopy != null;
    }

    public List<Book> getBooksByAuthor(String authorName){
        return bookRepo.findBooksByAuthor(authorName);
    }

    public void addBook(String name, String author, Integer totalCopies) {
        Book book = bookRepo.findBookByNameAndAuthor(name, author);

        if (book != null) {
            System.out.println("Book is already available");
            return;
        }

        if (totalCopies == null || totalCopies <= 0) {
            System.out.println("Add valid copies count");
            return;
        }
        String bookId = UUID.randomUUID().toString();
        Book newBook = new Book(
                name,
                bookId,
                author,
                totalCopies,
                totalCopies
        );

        bookRepo.addBook(newBook);

        for (int i = 1; i <= totalCopies; i++) {

            String copyId = UUID.randomUUID().toString();

            BookCopy bookCopy = new BookCopy(
                    bookId,
                    copyId,
                    CopyStatus.AVAILABLE
            );
            bookRepo.addBookCopy(newBook, bookCopy);
        }

        System.out.println("Book added successfully with " + totalCopies + " copies");
    }

    public void addBookCopies(Book book, Integer copies){
        for (int i = 1; i <= copies; i++) {

            String copyId = UUID.randomUUID().toString();

            BookCopy bookCopy = new BookCopy(
                    book.getBookId(),
                    copyId,
                    CopyStatus.AVAILABLE
            );
            bookRepo.addBookCopy(book, bookCopy);
        }

        book.setTotalCopies(book.getTotalCopies() + copies);
        book.setAvailableCopies(book.getAvailableCopies() + copies);

    }

}
