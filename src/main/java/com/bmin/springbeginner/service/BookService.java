package com.bmin.springbeginner.service;

import com.bmin.springbeginner.entity.Book;
import com.bmin.springbeginner.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@Slf4j
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public BookService() {

    } // end BookService


    /**
     * 1. A method that finds books by title
     */
    public Book findByTitle(String title) {
        //final List<Book> findAllBook = (ArrayList)bookRepository.findAll();

        // finds the price of the book

        return bookRepository.findByTitle(title)
                .stream()
                .findFirst()
                .orElse(new Book());

        // using for loop from the list of books

//        Book book = new Book();
//
//        for (int i = 0; i < findAllBook.size(); i++) {
//            if (findAllBook.get(i).getTitle().equals(title)) {
//                DecimalFormat numberFormat = new DecimalFormat("#.00");
//                book = findAllBook.get(i);
//                log.info( "price is {}.", numberFormat.format(findAllBook.get(i).getPrice()) );
//            }
//        }
//        return book;
    }


    /**
     * 2. (a) Finds the first 100 books in the list.
     * URL "" /book/get/front100
     */
    public List<Book> findFirstHundred() {
        final List<Book> findAllBook = (ArrayList)bookRepository.findAll();

        // a new list that with size of 100
        List<Book> newList = new ArrayList<>(100);
        int listSize = 100;

        if (listSize > findAllBook.size()) {
            listSize = findAllBook.size();
        }
        // finds the first 100 books from the list
        for (int i = 0; i < listSize; i++) {
            newList.add(findAllBook.get(i));
            log.info(findAllBook.get(i).getTitle());
        }

        return newList;
    }

    /**
     * 2. (b) Finds 100 books per page
     */
    // displays books (0 ~ 99)
    public List<Book> listByHundred(int pageNumber) {
        final List<Book> findAllBook = (ArrayList)bookRepository.findAll();

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
        countPages = (findAllBook.size() / 100) + 1;

        // case #2: when the last page has 100 books
        if (findAllBook.size() % 100 == 0) {
            countPages = findAllBook.size() / 100;
        }


        // checks if given page number is correct
        if (pageNumber < 1 || pageNumber > countPages) {
            return new ArrayList<Book>();
        }

        // checks if given page is the last page
        if (findAllBook.size() % 100 != 0 && pageNumber == countPages) {
            end = findAllBook.size() - 1;
        }

        // a new list that with size of 100
        List<Book> newList = new ArrayList<>();

        for (int i = start; i <= end; i++) {
            newList.add(findAllBook.get(i));
            log.info(findAllBook.get(i).getTitle());
        }

        return newList;
    }


    /**
     * 3. Get title, author, and price from the client
     *    Make Book object and register the object
     * Use both GET and POST
     *  URL -> http://localhost:8080/book/add
     */
    public List<Book> bookAdd(String title, String author, double price) {
        final List<Book> findAllBook = (ArrayList)bookRepository.findAll();

        if (hasSameBook(title, author, price)) {
            return findAllBook;
        }

        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setAuthor(author);
        newBook.setPrice(price);

        findAllBook.add(newBook);

        List<Book> newList = new ArrayList<Book>();

        for (int i = findAllBook.size() - 1; i >= 0; i--) {
            newList.add(findAllBook.get(i));
        }

        log.info(newList.get(newList.size() - 1).getTitle());

        bookRepository.save(newBook);

        return newList;
    }


    /**
     * 4. Get a book that matches title and author of a book in the book list
     *    Change the price of the book
     * Use POST
     *  URL -> http://localhost:8080/book/update/price
     */
    public Book bookChange(String title, String author, double price) {
        final List<Book> findAllBook = (ArrayList)bookRepository.findAll();

        Book book = new Book();
        boolean hasSame = false;

        for (int i = 0; i < findAllBook.size(); i++) {
            if (findAllBook.get(i).getTitle().equals(title)
                    && findAllBook.get(i).getAuthor().equals(author)) {

                findAllBook.get(i).setPrice(price);
                book = findAllBook.get(i);
                hasSame = true;
                log.info("new price :: " + findAllBook.get(i).getPrice());
            }
        } // end for

        if (hasSame == false) {
            book.setTitle("N/A");
            book.setAuthor("N/A");
            book.setPrice(-1);
        } else {
            bookRepository.save(book);
        }

        return book;
    }


    /**
     * 5. Get book title and author
     *    Remove the book from the book list
     * use POST
     *  URL -> http://localhost:8080/book/delete
     */
    public List<Book> bookDelete(String title, String author) {
        final List<Book> findAllBook = (ArrayList)bookRepository.findAll();
        Book book = new Book();

        for (int i = 0; i < findAllBook.size(); i++) {
            if (findAllBook.get(i).getTitle().equals(title) &&
                    findAllBook.get(i).getAuthor().equals(author)) {
                book = findAllBook.get(i);
                findAllBook.remove(i);
//                bookList.remove(i);

            }
        } // end for

        bookRepository.delete(book);

        return findAllBook;
    }


    // helper methods
    private boolean hasSameBook(String title, String author, double price) {
        final List<Book> findAllBook = (ArrayList)bookRepository.findAll();

        for (int i = 0; i < findAllBook.size(); i++) {
            if (findAllBook.get(i).getTitle().equals(title)
                    && findAllBook.get(i).getAuthor().equals(author)) {
                return true;
            }
        } // end for
        return false;
    }

} // end BookService
