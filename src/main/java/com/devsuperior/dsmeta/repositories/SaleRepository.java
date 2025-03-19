package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query("SELECT new com.devsuperior.dsmeta.dto.SaleReportDTO(s.id, s.date, s.amount, s.seller.name) " +
	           "FROM Sale s " +
	           "WHERE (:minDate IS NULL OR s.date >= :minDate) " +
	           "AND (:maxDate IS NULL OR s.date <= :maxDate) " +
	           "AND (:name IS NULL OR UPPER(s.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))) " +
	           "ORDER BY s.date DESC")
	Page<SaleReportDTO> getReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);


}
