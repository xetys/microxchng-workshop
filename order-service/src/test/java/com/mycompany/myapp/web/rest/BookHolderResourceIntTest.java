package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.OrderServiceApp;

import com.mycompany.myapp.config.SecurityBeanOverrideConfiguration;

import com.mycompany.myapp.domain.BookHolder;
import com.mycompany.myapp.repository.BookHolderRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BookHolderResource REST controller.
 *
 * @see BookHolderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OrderServiceApp.class, SecurityBeanOverrideConfiguration.class})
public class BookHolderResourceIntTest {

    private static final Long DEFAULT_BOOK_ID = 1L;
    private static final Long UPDATED_BOOK_ID = 2L;

    @Autowired
    private BookHolderRepository bookHolderRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBookHolderMockMvc;

    private BookHolder bookHolder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            BookHolderResource bookHolderResource = new BookHolderResource(bookHolderRepository);
        this.restBookHolderMockMvc = MockMvcBuilders.standaloneSetup(bookHolderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookHolder createEntity(EntityManager em) {
        BookHolder bookHolder = new BookHolder()
                .bookId(DEFAULT_BOOK_ID);
        return bookHolder;
    }

    @Before
    public void initTest() {
        bookHolder = createEntity(em);
    }

    @Test
    @Transactional
    public void createBookHolder() throws Exception {
        int databaseSizeBeforeCreate = bookHolderRepository.findAll().size();

        // Create the BookHolder

        restBookHolderMockMvc.perform(post("/api/book-holders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookHolder)))
            .andExpect(status().isCreated());

        // Validate the BookHolder in the database
        List<BookHolder> bookHolderList = bookHolderRepository.findAll();
        assertThat(bookHolderList).hasSize(databaseSizeBeforeCreate + 1);
        BookHolder testBookHolder = bookHolderList.get(bookHolderList.size() - 1);
        assertThat(testBookHolder.getBookId()).isEqualTo(DEFAULT_BOOK_ID);
    }

    @Test
    @Transactional
    public void createBookHolderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookHolderRepository.findAll().size();

        // Create the BookHolder with an existing ID
        BookHolder existingBookHolder = new BookHolder();
        existingBookHolder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookHolderMockMvc.perform(post("/api/book-holders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingBookHolder)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BookHolder> bookHolderList = bookHolderRepository.findAll();
        assertThat(bookHolderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkBookIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookHolderRepository.findAll().size();
        // set the field null
        bookHolder.setBookId(null);

        // Create the BookHolder, which fails.

        restBookHolderMockMvc.perform(post("/api/book-holders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookHolder)))
            .andExpect(status().isBadRequest());

        List<BookHolder> bookHolderList = bookHolderRepository.findAll();
        assertThat(bookHolderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBookHolders() throws Exception {
        // Initialize the database
        bookHolderRepository.saveAndFlush(bookHolder);

        // Get all the bookHolderList
        restBookHolderMockMvc.perform(get("/api/book-holders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookHolder.getId().intValue())))
            .andExpect(jsonPath("$.[*].bookId").value(hasItem(DEFAULT_BOOK_ID.intValue())));
    }

    @Test
    @Transactional
    public void getBookHolder() throws Exception {
        // Initialize the database
        bookHolderRepository.saveAndFlush(bookHolder);

        // Get the bookHolder
        restBookHolderMockMvc.perform(get("/api/book-holders/{id}", bookHolder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bookHolder.getId().intValue()))
            .andExpect(jsonPath("$.bookId").value(DEFAULT_BOOK_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBookHolder() throws Exception {
        // Get the bookHolder
        restBookHolderMockMvc.perform(get("/api/book-holders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookHolder() throws Exception {
        // Initialize the database
        bookHolderRepository.saveAndFlush(bookHolder);
        int databaseSizeBeforeUpdate = bookHolderRepository.findAll().size();

        // Update the bookHolder
        BookHolder updatedBookHolder = bookHolderRepository.findOne(bookHolder.getId());
        updatedBookHolder
                .bookId(UPDATED_BOOK_ID);

        restBookHolderMockMvc.perform(put("/api/book-holders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBookHolder)))
            .andExpect(status().isOk());

        // Validate the BookHolder in the database
        List<BookHolder> bookHolderList = bookHolderRepository.findAll();
        assertThat(bookHolderList).hasSize(databaseSizeBeforeUpdate);
        BookHolder testBookHolder = bookHolderList.get(bookHolderList.size() - 1);
        assertThat(testBookHolder.getBookId()).isEqualTo(UPDATED_BOOK_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingBookHolder() throws Exception {
        int databaseSizeBeforeUpdate = bookHolderRepository.findAll().size();

        // Create the BookHolder

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBookHolderMockMvc.perform(put("/api/book-holders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookHolder)))
            .andExpect(status().isCreated());

        // Validate the BookHolder in the database
        List<BookHolder> bookHolderList = bookHolderRepository.findAll();
        assertThat(bookHolderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBookHolder() throws Exception {
        // Initialize the database
        bookHolderRepository.saveAndFlush(bookHolder);
        int databaseSizeBeforeDelete = bookHolderRepository.findAll().size();

        // Get the bookHolder
        restBookHolderMockMvc.perform(delete("/api/book-holders/{id}", bookHolder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BookHolder> bookHolderList = bookHolderRepository.findAll();
        assertThat(bookHolderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookHolder.class);
    }
}
