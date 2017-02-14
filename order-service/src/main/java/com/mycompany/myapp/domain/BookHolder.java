package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BookHolder.
 */
@Entity
@Table(name = "book_holder")
public class BookHolder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @ManyToMany(mappedBy = "books")
    @JsonIgnore
    private Set<BookOrder> orders = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public BookHolder bookId(Long bookId) {
        this.bookId = bookId;
        return this;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Set<BookOrder> getOrders() {
        return orders;
    }

    public BookHolder orders(Set<BookOrder> bookOrders) {
        this.orders = bookOrders;
        return this;
    }

    public BookHolder addOrder(BookOrder bookOrder) {
        this.orders.add(bookOrder);
        bookOrder.getBooks().add(this);
        return this;
    }

    public BookHolder removeOrder(BookOrder bookOrder) {
        this.orders.remove(bookOrder);
        bookOrder.getBooks().remove(this);
        return this;
    }

    public void setOrders(Set<BookOrder> bookOrders) {
        this.orders = bookOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BookHolder bookHolder = (BookHolder) o;
        if (bookHolder.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, bookHolder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BookHolder{" +
            "id=" + id +
            ", bookId='" + bookId + "'" +
            '}';
    }
}
