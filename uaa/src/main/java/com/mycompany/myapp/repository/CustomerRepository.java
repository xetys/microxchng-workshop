package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Customer;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Customer entity.
 */
@SuppressWarnings("unused")
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Optional<Customer> findByUserId(Long id);
}
