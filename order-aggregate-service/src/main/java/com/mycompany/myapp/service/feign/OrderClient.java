package com.mycompany.myapp.service.feign;

import com.mycompany.myapp.client.AuthorizedFeignClient;
import com.mycompany.myapp.domain.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by on 14.02.17.
 *
 * @author David Steiman
 */
@AuthorizedFeignClient(name = "orderservice")
public interface OrderClient {
    @RequestMapping(value = "/api/book-orders", method = RequestMethod.GET)
    List<Order> findAll();
}
