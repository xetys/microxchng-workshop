package com.mycompany.myapp.service.feign;

import com.mycompany.myapp.client.AuthorizedFeignClient;
import com.mycompany.myapp.domain.Book;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by on 14.02.17.
 *
 * @author David Steiman
 */
@AuthorizedFeignClient(name = "bookservice")
public interface BookClient {

    @RequestMapping(value = "/api/books", method = RequestMethod.GET)
    List<Book> findAll();

    @RequestMapping(value = "/api/books/{id}", method = RequestMethod.GET)
    Book findById(@PathVariable("id") Long id);
}
