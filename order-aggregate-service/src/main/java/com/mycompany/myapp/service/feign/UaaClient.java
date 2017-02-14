package com.mycompany.myapp.service.feign;

import com.mycompany.myapp.client.AuthorizedFeignClient;
import com.mycompany.myapp.domain.Customer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by on 14.02.17.
 *
 * @author David Steiman
 */
@AuthorizedFeignClient(name = "uaa")
public interface UaaClient {

    @RequestMapping(value = "/api/customers/{customerId}")
    Customer findById(@PathVariable("customerId") Long customerId);
}
