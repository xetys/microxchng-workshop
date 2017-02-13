package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BookOrder;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BookOrder entity.
 */
@SuppressWarnings("unused")
public interface BookOrderRepository extends JpaRepository<BookOrder,Long> {

}
