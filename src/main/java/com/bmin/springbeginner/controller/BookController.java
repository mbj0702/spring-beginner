package com.bmin.springbeginner.controller;

import com.bmin.springbeginner.service.BookService;
import com.bmin.springbeginner.vo.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@Slf4j
public class BookController {

//    private static List<Book> bookList = new ArrayList<>();

    @Autowired
    BookService bookService;

    public BookController() {
//        IntStream.range(0, 1000)
//                .forEach(
//                        i -> {
//                            Book book = new Book();
//                            book.setAuthor("Author" + i);
//                            book.setPrice(i + 100.20);
//                            book.setTitle("Java" + i + "InAction");
//                            bookList.add(book);
//                        });
    }


    // http://localhost:8080/book/get/title/Java2InAction
    /**
     * A method that finds books by title
     */
    @RequestMapping("/book/get/title/{title}")
    public String findByTitle(@PathVariable String title, Model model) {
        log.info("title is {}.", title);

        // Sends book information to the front end
//        model.addAttribute("book", bookList.get(count));
        model.addAttribute("book", bookService.findByTitle(title));
        return "bookInfo";
    }


    /**
     * 4. Finds the first 100 books in the list.
     * URL "" /book/get/front100
     */
    @RequestMapping("/book/get/frontHundred")
    public String findFirstHundred(Model model) {
        log.info("First 100 books are: ");

        // Sends book information to the front end
//        model.addAttribute("newList", newList);
        model.addAttribute("newList", bookService.findFirstHundred());

        return "frontHundred";
    }


    /**
     * 5. Get title, author, and price from the client
     *    Make Book object and register the object
     * Use both GET and POST
     *  URL -> http://localhost:8080/book/add
     */
    @RequestMapping("/book/add")
    public String bookAdd(@RequestParam String title,
                          @RequestParam String author,
                          @RequestParam int price,
                          Model model) {
        log.info("TITLE :: " + title + "\nAUTHOR :: " + author + "\nPRICE :: " + price);

        // Sends book information to the front end
        model.addAttribute("newList", bookService.bookAdd(title, author, price));

        return "bookList";
    }


    @RequestMapping("/book/toAddPage")
    public String toAddPage() {
        log.info("On book adding page");

        return "addBook";
    }


    // displays books (0 ~ 99)
    @RequestMapping("/book/get/list/{pageNumber}")
    public String listByHundred(@PathVariable int pageNumber, Model model) {

        // Sends book information to the front end
        model.addAttribute("newList", bookService.listByHundred(pageNumber));

        return "frontHundred";
    }


    @RequestMapping("/book/find/java")
    public String bookFind() {
        System.out.println("bookFind");
        return "bookFind";
    }


    /**
     * 6. Get a book that matches title and author in the book list
     *    Change the price of the book
     * Use POST
     *  URL -> http://localhost:8080/book/update/price
     */
    @RequestMapping("/book/toChangePage")
    public String toChangeBook() {
        log.info("On changing book info page");
        return "changeBook";
    }


    @RequestMapping("/book/change")
    public String bookChange(@RequestParam String title,
                             @RequestParam String author,
                             @RequestParam int price,
                             Model model) {

        Book book = bookService.bookChange(title, author, price);
        if (book.getTitle().equals("N/A") && book.getAuthor().equals("N/A")) {
            return "bookList";
        }

        // Sends book information to the front end
        model.addAttribute("book", book);

        return "bookInfo";
    }


    /**
     * 7. Get book title and author
     *    Remove the book from the book list
     * use POST
     *  URL -> http://localhost:8080/book/delete
     */
    @RequestMapping("/book/delete")
    public String bookDelete(@RequestParam String title,
                      @RequestParam String author,
                      Model model) {

//        for (int i = 0; i < bookList.size(); i++) {
//            if (bookList.get(i).getTitle().equals(title) &&
//                bookList.get(i).getAuthor().equals(author)) {
//                bookList.remove(bookList.get(i));
//            }
//        } // end for

        // Sends book information to the front end
        model.addAttribute("newList", bookService.bookDelete(title, author));

        return "bookList";
    } // end bookDelete


    @RequestMapping("/book/toDeletePage")
    public String toDeleteBook() {
        log.info("On deleting page");
        return "deleteBook";
    }

} // end BookController
