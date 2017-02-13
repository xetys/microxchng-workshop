package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BookHolder;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BookHolder entity.
 */
@SuppressWarnings("unused")
public interface BookHolderRepository extends JpaRepository<BookHolder,Long> {

}
