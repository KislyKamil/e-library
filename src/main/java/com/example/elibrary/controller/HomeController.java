package com.example.elibrary.controller;
import com.example.elibrary.entity.Book;
import com.example.elibrary.entity.Order;
import com.example.elibrary.repository.OrderRepository;
import com.example.elibrary.service.BookService;
import com.example.elibrary.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
public class HomeController {


    private final BookService bookService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;


    public HomeController(BookService bookService, OrderService orderService, OrderRepository orderRepository) {

        this.bookService = bookService;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    public int roundUp(int value) {


        float size = (float)value / 5;

        int sizeInt = value / 5;


        if (size % sizeInt != 0) {
            sizeInt = sizeInt + 1;
        }

        return sizeInt;
    }

    @RequestMapping(path = "/")
    @Autowired
    public ModelAndView index(HttpSession session) {



        int size;
        int pageNumbers;

        List<Book> bookList = new ArrayList<>(bookService.listBooks().subList(0, 5));


        size = bookService.listBooks().size();
        pageNumbers = roundUp(size);


        ModelAndView model = new ModelAndView("index");
        model.addObject("books", bookList);
        model.addObject("length", pageNumbers);
        model.addObject("orders", orderService.listOrders());

        return model;


    }

    @RequestMapping(path = "/{pageId}")
    @ResponseBody
    public List<Book> getBooks(@PathVariable("pageId") int pageId, ModelMap model) {

        ArrayList<Integer> ids = new ArrayList<>();

        ArrayList<Book> allBooks = new ArrayList<>(bookService.listBooks());

        int index = (pageId * 5) - 4;

        if (pageId <= roundUp(allBooks.size())) {

            while (index <= (pageId * 5)) {
                ids.add(index);
                index++;
            }

        }  // return "error";

        ArrayList<Book> pageList = new ArrayList<>(bookService.ListById(ids));

        model.addAttribute("pageId", pageId);


        return pageList;
    }


}
