package com.showroommanagement.repository;

import com.showroommanagement.entity.SaleDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface SaleDetailRepository extends JpaRepository<SaleDetail, Integer> {
    @Query("SELECT sd FROM SaleDetail sd " +
            "JOIN sd.product p " +
            "JOIN sd.customer c " +
            "JOIN p.employee e " +
            "JOIN p.brand br " +
            "JOIN e.department d " +
            "JOIN e.branch b " +
            "JOIN b.showroom srm " +
            "WHERE (:keyword IS NULL OR " +
            "(p.model) LIKE (CONCAT('%', :keyword, '%')) OR " +
            "(br.brand) LIKE (CONCAT('%', :keyword, '%')))")
    Page<SaleDetail> searchProducts(@Param("keyword") String keyword, Pageable pageable);


}
