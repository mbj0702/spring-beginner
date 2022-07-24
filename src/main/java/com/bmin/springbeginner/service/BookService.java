package com.bmin.springbeginner.service;

import com.bmin.springbeginner.vo.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@Slf4j
public class BookService {
    private static List<Book> bookList = new ArrayList<>();

    public BookService() {
        IntStream.range(0, 1000)
                .forEach(
                        i -> {
                            Book book = new Book();
                            book.setAuthor("Author" + i);
                            book.setPrice(i + 100.20);
                            book.setTitle("Java" + i + "InAction");
                            bookList.add(book);
                        });
    } // end BookService


    /**
     * 3. A method that finds books by title
     */
    public Book findByTitle(String title) {
        // finds the price of the book
        Book book = new Book();

        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getTitle().equals(title)) {
                DecimalFormat numberFormat = new DecimalFormat("#.00");
                book = bookList.get(i);
                log.info( "price is {}.", numberFormat.format(bookList.get(i).getPrice()) );
            }
        }
        return book;
    }


    /**
     * 4. (a) Finds the first 100 books in the list.
     * URL "" /book/get/front100
     */
    public List<Book> findFirstHundred() {
        // a new list that with size of 100
        List<Book> newList = new ArrayList<>(100);

        // finds the first 100 books from the list
        for (int i = 0; i < 100; i++) {
            newList.add(bookList.get(i));
            log.info(bookList.get(i).getTitle());
        }

        return newList;
    }

    /**
     * 4. (b) Finds 100 books per page
     */
    // displays books (0 ~ 99)
    public List<Book> listByHundred(int pageNumber) {
        int start = (pageNumber - 1) * 100;
        int end = pageNumber * 100 - 1;

        log.info("From book" + start + " to book" + end);

        // gets maximum available page
//        int countPages = 1;
//        int lastPageSize = bookList.size();
//
//        while (lastPageSize > 100) {
//            lastPageSize = lastPageSize - 100;
//            countPages++;
//        }

        int countPages = 0;

        // case #1: when the last page has less than 100 books
        countPages = (bookList.size() / 100) + 1;

        // case #2: when the last page has 100 books
        if (bookList.size() % 100 == 0) {
            countPages = bookList.size() / 100;
        }


        // checks if given page number is correct
        if (pageNumber < 1 || pageNumber > countPages) {
            return new ArrayList<Book>();
        }

        // checks if given page is the last page
        if (bookList.size() % 100 != 0 && pageNumber == countPages) {
            end = bookList.size() - 1;
        }

        // a new list that with size of 100
        List<Book> newList = new ArrayList<>();

        for (int i = start; i <= end; i++) {
            newList.add(bookList.get(i));
            log.info(bookList.get(i).getTitle());
        }

        return newList;
    }

    /**
     * 5. Get title, author, and price from the client
     *    Make Book object and register the object
     * Use both GET and POST
     *  URL -> http://localhost:8080/book/add
     */
    public List<Book> bookAdd(String title, String author, int price) {
        if (hasSameBook(title, author, price)) {
            return bookList;
        }

        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setAuthor(author);
        newBook.setPrice(price);

        bookList.add(newBook);

        List<Book> newList = new ArrayList<Book>();

        for (int i = bookList.size() - 1; i >= 0; i--) {
            newList.add(bookList.get(i));
        }

        log.info(newList.get(newList.size() - 1).getTitle());

        return newList;
    }





    /**
     * 6. Get a book that matches title and author of a book in the book list
     *    Change the price of the book
     * Use POST
     *  URL -> http://localhost:8080/book/update/price
     */
    public Book bookChange(String title, String author, int price) {
        Book book = new Book();
        boolean hasSame = false;

        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getTitle().equals(title)
                    && bookList.get(i).getAuthor().equals(author)) {

                bookList.get(i).setPrice(price);
                book = bookList.get(i);
                hasSame = true;
                log.info("new price :: " + bookList.get(i).getPrice());
            }
        } // end for

        if (hasSame == false) {
            book.setTitle("N/A");
            book.setAuthor("N/A");
            book.setPrice(-1);
        }

        return book;
    }


    /**
     * 7. Get book title and author
     *    Remove the book from the book list
     * use POST
     *  URL -> http://localhost:8080/book/delete
     */
    public List<Book> bookDelete(String title, String author) {

        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getTitle().equals(title) &&
                    bookList.get(i).getAuthor().equals(author)) {
                bookList.remove(bookList.get(i));
            }
        } // end for

        return bookList;
    }


    // helper methods
    private boolean hasSameBook(String title, String author, int price) {
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getTitle().equals(title)
                    && bookList.get(i).getAuthor().equals(author)) {
                return true;
            }
        } // end for
        return false;
    }

} // end BookService
