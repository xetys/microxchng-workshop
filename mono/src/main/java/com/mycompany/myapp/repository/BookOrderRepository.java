package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BookOrder;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the BookOrder entity.
 */
@SuppressWarnings("unused")
public interface BookOrderRepository extends JpaRepository<BookOrder,Long> {

    @Query("select distinct bookOrder from BookOrder bookOrder left join fetch bookOrder.books")
    List<BookOrder> findAllWithEagerRelationships();

    @Query("select bookOrder from BookOrder bookOrder left join fetch bookOrder.books where bookOrder.id =:id")
    BookOrder findOneWithEagerRelationships(@Param("id") Long id);

}
