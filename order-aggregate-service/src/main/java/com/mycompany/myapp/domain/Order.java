package com.mycompany.myapp.domain;

/**
 * Created by on 14.02.17.
 *
 * @author David Steiman
 */
public class Order {
    private Long id;

    private OrderStatus status;

    private Long customerId;

    public Order() {
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
