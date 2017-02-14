package com.mycompany.myapp.service;

import com.mycompany.myapp.service.feign.BookClient;
import com.mycompany.myapp.service.feign.OrderClient;
import com.mycompany.myapp.service.feign.UaaClient;
import com.mycompany.myapp.service.vm.OrderVM;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by on 14.02.17.
 *
 * @author David Steiman
 */
@Service
public class OrderService {

    private BookClient bookClient;
    private OrderClient orderClient;
    private UaaClient uaaClient;

    public OrderService(BookClient bookClient, OrderClient orderClient, UaaClient uaaClient) {
        this.bookClient = bookClient;
        this.orderClient = orderClient;
        this.uaaClient = uaaClient;
    }

    public List<OrderVM> loadOrders() {
        return orderClient.findAll().stream()
            .map(order -> new OrderVM(
                order,
                uaaClient.findById(order.getCustomerId()),
                order.getBooks().stream()
                    .map(bookHolder -> bookClient.findById(bookHolder.getBookId()))
                    .collect(Collectors.toList())
            ))
            .collect(Collectors.toList());
    }
}
