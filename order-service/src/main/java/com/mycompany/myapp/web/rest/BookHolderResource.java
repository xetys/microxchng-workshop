package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.BookHolder;

import com.mycompany.myapp.repository.BookHolderRepository;
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
 * REST controller for managing BookHolder.
 */
@RestController
@RequestMapping("/api")
public class BookHolderResource {

    private final Logger log = LoggerFactory.getLogger(BookHolderResource.class);

    private static final String ENTITY_NAME = "bookHolder";
        
    private final BookHolderRepository bookHolderRepository;

    public BookHolderResource(BookHolderRepository bookHolderRepository) {
        this.bookHolderRepository = bookHolderRepository;
    }

    /**
     * POST  /book-holders : Create a new bookHolder.
     *
     * @param bookHolder the bookHolder to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bookHolder, or with status 400 (Bad Request) if the bookHolder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/book-holders")
    @Timed
    public ResponseEntity<BookHolder> createBookHolder(@Valid @RequestBody BookHolder bookHolder) throws URISyntaxException {
        log.debug("REST request to save BookHolder : {}", bookHolder);
        if (bookHolder.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bookHolder cannot already have an ID")).body(null);
        }
        BookHolder result = bookHolderRepository.save(bookHolder);
        return ResponseEntity.created(new URI("/api/book-holders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /book-holders : Updates an existing bookHolder.
     *
     * @param bookHolder the bookHolder to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bookHolder,
     * or with status 400 (Bad Request) if the bookHolder is not valid,
     * or with status 500 (Internal Server Error) if the bookHolder couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/book-holders")
    @Timed
    public ResponseEntity<BookHolder> updateBookHolder(@Valid @RequestBody BookHolder bookHolder) throws URISyntaxException {
        log.debug("REST request to update BookHolder : {}", bookHolder);
        if (bookHolder.getId() == null) {
            return createBookHolder(bookHolder);
        }
        BookHolder result = bookHolderRepository.save(bookHolder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bookHolder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /book-holders : get all the bookHolders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bookHolders in body
     */
    @GetMapping("/book-holders")
    @Timed
    public List<BookHolder> getAllBookHolders() {
        log.debug("REST request to get all BookHolders");
        List<BookHolder> bookHolders = bookHolderRepository.findAll();
        return bookHolders;
    }

    /**
     * GET  /book-holders/:id : get the "id" bookHolder.
     *
     * @param id the id of the bookHolder to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bookHolder, or with status 404 (Not Found)
     */
    @GetMapping("/book-holders/{id}")
    @Timed
    public ResponseEntity<BookHolder> getBookHolder(@PathVariable Long id) {
        log.debug("REST request to get BookHolder : {}", id);
        BookHolder bookHolder = bookHolderRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bookHolder));
    }

    /**
     * DELETE  /book-holders/:id : delete the "id" bookHolder.
     *
     * @param id the id of the bookHolder to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/book-holders/{id}")
    @Timed
    public ResponseEntity<Void> deleteBookHolder(@PathVariable Long id) {
        log.debug("REST request to delete BookHolder : {}", id);
        bookHolderRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
