package com.mycompany.myapp.service.vm;

import com.mycompany.myapp.domain.Book;
import com.mycompany.myapp.domain.Customer;
import com.mycompany.myapp.domain.Order;
import com.mycompany.myapp.domain.OrderStatus;

import java.util.List;

/**
 * Created by on 14.02.17.
 *
 * @author David Steiman
 */
public class OrderVM {
    private Long id;

    private OrderStatus status;

    private Customer customer;

    private List<Book> books;

    public OrderVM(Order order, Customer customer, List<Book> books) {
        this.id = order.getId();
        this.status = order.getStatus();
        this.customer = customer;
        this.books = books;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }


}
