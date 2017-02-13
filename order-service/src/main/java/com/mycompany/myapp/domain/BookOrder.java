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

    @ManyToMany
    @JoinTable(name = "book_order_order",
               joinColumns = @JoinColumn(name="book_orders_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="orders_id", referencedColumnName="id"))
    private Set<BookHolder> orders = new HashSet<>();

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

    public Set<BookHolder> getOrders() {
        return orders;
    }

    public BookOrder orders(Set<BookHolder> bookHolders) {
        this.orders = bookHolders;
        return this;
    }

    public BookOrder addOrder(BookHolder bookHolder) {
        this.orders.add(bookHolder);
        bookHolder.getBooks().add(this);
        return this;
    }

    public BookOrder removeOrder(BookHolder bookHolder) {
        this.orders.remove(bookHolder);
        bookHolder.getBooks().remove(this);
        return this;
    }

    public void setOrders(Set<BookHolder> bookHolders) {
        this.orders = bookHolders;
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
