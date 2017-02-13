package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.OrderServiceApp;

import com.mycompany.myapp.config.SecurityBeanOverrideConfiguration;

import com.mycompany.myapp.domain.BookOrder;
import com.mycompany.myapp.repository.BookOrderRepository;
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

import com.mycompany.myapp.domain.enumeration.OrderStatus;
/**
 * Test class for the BookOrderResource REST controller.
 *
 * @see BookOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OrderServiceApp.class, SecurityBeanOverrideConfiguration.class})
public class BookOrderResourceIntTest {

    private static final OrderStatus DEFAULT_STATUS = OrderStatus.NEW;
    private static final OrderStatus UPDATED_STATUS = OrderStatus.PAYED;

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;

    @Autowired
    private BookOrderRepository bookOrderRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBookOrderMockMvc;

    private BookOrder bookOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            BookOrderResource bookOrderResource = new BookOrderResource(bookOrderRepository);
        this.restBookOrderMockMvc = MockMvcBuilders.standaloneSetup(bookOrderResource)
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
    public static BookOrder createEntity(EntityManager em) {
        BookOrder bookOrder = new BookOrder()
                .status(DEFAULT_STATUS)
                .customerId(DEFAULT_CUSTOMER_ID);
        return bookOrder;
    }

    @Before
    public void initTest() {
        bookOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createBookOrder() throws Exception {
        int databaseSizeBeforeCreate = bookOrderRepository.findAll().size();

        // Create the BookOrder

        restBookOrderMockMvc.perform(post("/api/book-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookOrder)))
            .andExpect(status().isCreated());

        // Validate the BookOrder in the database
        List<BookOrder> bookOrderList = bookOrderRepository.findAll();
        assertThat(bookOrderList).hasSize(databaseSizeBeforeCreate + 1);
        BookOrder testBookOrder = bookOrderList.get(bookOrderList.size() - 1);
        assertThat(testBookOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBookOrder.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    public void createBookOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookOrderRepository.findAll().size();

        // Create the BookOrder with an existing ID
        BookOrder existingBookOrder = new BookOrder();
        existingBookOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookOrderMockMvc.perform(post("/api/book-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingBookOrder)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BookOrder> bookOrderList = bookOrderRepository.findAll();
        assertThat(bookOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookOrderRepository.findAll().size();
        // set the field null
        bookOrder.setStatus(null);

        // Create the BookOrder, which fails.

        restBookOrderMockMvc.perform(post("/api/book-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookOrder)))
            .andExpect(status().isBadRequest());

        List<BookOrder> bookOrderList = bookOrderRepository.findAll();
        assertThat(bookOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookOrderRepository.findAll().size();
        // set the field null
        bookOrder.setCustomerId(null);

        // Create the BookOrder, which fails.

        restBookOrderMockMvc.perform(post("/api/book-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookOrder)))
            .andExpect(status().isBadRequest());

        List<BookOrder> bookOrderList = bookOrderRepository.findAll();
        assertThat(bookOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBookOrders() throws Exception {
        // Initialize the database
        bookOrderRepository.saveAndFlush(bookOrder);

        // Get all the bookOrderList
        restBookOrderMockMvc.perform(get("/api/book-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));
    }

    @Test
    @Transactional
    public void getBookOrder() throws Exception {
        // Initialize the database
        bookOrderRepository.saveAndFlush(bookOrder);

        // Get the bookOrder
        restBookOrderMockMvc.perform(get("/api/book-orders/{id}", bookOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bookOrder.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBookOrder() throws Exception {
        // Get the bookOrder
        restBookOrderMockMvc.perform(get("/api/book-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookOrder() throws Exception {
        // Initialize the database
        bookOrderRepository.saveAndFlush(bookOrder);
        int databaseSizeBeforeUpdate = bookOrderRepository.findAll().size();

        // Update the bookOrder
        BookOrder updatedBookOrder = bookOrderRepository.findOne(bookOrder.getId());
        updatedBookOrder
                .status(UPDATED_STATUS)
                .customerId(UPDATED_CUSTOMER_ID);

        restBookOrderMockMvc.perform(put("/api/book-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBookOrder)))
            .andExpect(status().isOk());

        // Validate the BookOrder in the database
        List<BookOrder> bookOrderList = bookOrderRepository.findAll();
        assertThat(bookOrderList).hasSize(databaseSizeBeforeUpdate);
        BookOrder testBookOrder = bookOrderList.get(bookOrderList.size() - 1);
        assertThat(testBookOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBookOrder.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingBookOrder() throws Exception {
        int databaseSizeBeforeUpdate = bookOrderRepository.findAll().size();

        // Create the BookOrder

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBookOrderMockMvc.perform(put("/api/book-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookOrder)))
            .andExpect(status().isCreated());

        // Validate the BookOrder in the database
        List<BookOrder> bookOrderList = bookOrderRepository.findAll();
        assertThat(bookOrderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBookOrder() throws Exception {
        // Initialize the database
        bookOrderRepository.saveAndFlush(bookOrder);
        int databaseSizeBeforeDelete = bookOrderRepository.findAll().size();

        // Get the bookOrder
        restBookOrderMockMvc.perform(delete("/api/book-orders/{id}", bookOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BookOrder> bookOrderList = bookOrderRepository.findAll();
        assertThat(bookOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookOrder.class);
    }
}
