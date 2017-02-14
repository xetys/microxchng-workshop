package com.mycompany.myapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.OrderStatus;

/**
 * A BookOrder.
 */
@Entity
@Table(name = "book_order")
public class BookOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "book_order_book",
               joinColumns = @JoinColumn(name="book_orders_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="books_id", referencedColumnName="id"))
    private Set<BookHolder> books = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public BookOrder status(OrderStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public BookOrder customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Set<BookHolder> getBooks() {
        return books;
    }

    public BookOrder books(Set<BookHolder> bookHolders) {
        this.books = bookHolders;
        return this;
    }

    public BookOrder addBook(BookHolder bookHolder) {
        this.books.add(bookHolder);
        bookHolder.getOrders().add(this);
        return this;
    }

    public BookOrder removeBook(BookHolder bookHolder) {
        this.books.remove(bookHolder);
        bookHolder.getOrders().remove(this);
        return this;
    }

    public void setBooks(Set<BookHolder> bookHolders) {
        this.books = bookHolders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BookOrder bookOrder = (BookOrder) o;
        if (bookOrder.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, bookOrder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BookOrder{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", customerId='" + customerId + "'" +
            '}';
    }
}
