package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Book;
import com.mycompany.myapp.service.OrderService;
import com.mycompany.myapp.service.feign.BookClient;
import com.mycompany.myapp.service.vm.OrderVM;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by on 14.02.17.
 *
 * @author David Steiman
 */
@RestController
@RequestMapping("/api/")
public class AggregateResource {

    private OrderService orderService;

    public AggregateResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderVM>> getOrders() {
        return ResponseEntity.ok(orderService.loadOrders());
    }
}
