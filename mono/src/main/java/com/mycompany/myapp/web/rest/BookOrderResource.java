package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.BookOrder;

import com.mycompany.myapp.repository.BookOrderRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BookOrder.
 */
@RestController
@RequestMapping("/api")
public class BookOrderResource {

    private final Logger log = LoggerFactory.getLogger(BookOrderResource.class);

    private static final String ENTITY_NAME = "bookOrder";
        
    private final BookOrderRepository bookOrderRepository;

    public BookOrderResource(BookOrderRepository bookOrderRepository) {
        this.bookOrderRepository = bookOrderRepository;
    }

    /**
     * POST  /book-orders : Create a new bookOrder.
     *
     * @param bookOrder the bookOrder to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bookOrder, or with status 400 (Bad Request) if the bookOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/book-orders")
    @Timed
    public ResponseEntity<BookOrder> createBookOrder(@Valid @RequestBody BookOrder bookOrder) throws URISyntaxException {
        log.debug("REST request to save BookOrder : {}", bookOrder);
        if (bookOrder.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bookOrder cannot already have an ID")).body(null);
        }
        BookOrder result = bookOrderRepository.save(bookOrder);
        return ResponseEntity.created(new URI("/api/book-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /book-orders : Updates an existing bookOrder.
     *
     * @param bookOrder the bookOrder to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bookOrder,
     * or with status 400 (Bad Request) if the bookOrder is not valid,
     * or with status 500 (Internal Server Error) if the bookOrder couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/book-orders")
    @Timed
    public ResponseEntity<BookOrder> updateBookOrder(@Valid @RequestBody BookOrder bookOrder) throws URISyntaxException {
        log.debug("REST request to update BookOrder : {}", bookOrder);
        if (bookOrder.getId() == null) {
            return createBookOrder(bookOrder);
        }
        BookOrder result = bookOrderRepository.save(bookOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bookOrder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /book-orders : get all the bookOrders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bookOrders in body
     */
    @GetMapping("/book-orders")
    @Timed
    public List<BookOrder> getAllBookOrders() {
        log.debug("REST request to get all BookOrders");
        List<BookOrder> bookOrders = bookOrderRepository.findAllWithEagerRelationships();
        return bookOrders;
    }

    /**
     * GET  /book-orders/:id : get the "id" bookOrder.
     *
     * @param id the id of the bookOrder to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bookOrder, or with status 404 (Not Found)
     */
    @GetMapping("/book-orders/{id}")
    @Timed
    public ResponseEntity<BookOrder> getBookOrder(@PathVariable Long id) {
        log.debug("REST request to get BookOrder : {}", id);
        BookOrder bookOrder = bookOrderRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bookOrder));
    }

    /**
     * DELETE  /book-orders/:id : delete the "id" bookOrder.
     *
     * @param id the id of the bookOrder to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/book-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteBookOrder(@PathVariable Long id) {
        log.debug("REST request to delete BookOrder : {}", id);
        bookOrderRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
