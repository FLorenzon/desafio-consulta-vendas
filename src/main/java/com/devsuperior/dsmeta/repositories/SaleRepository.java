package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query("SELECT new com.devsuperior.dsmeta.dto.SaleReportDTO(s.id, s.date, s.amount, s.seller.name) "
			+ "FROM Sale s " + "WHERE (:minDate IS NULL OR s.date >= :minDate) "
			+ "AND (:maxDate IS NULL OR s.date <= :maxDate) "
			+ "AND (:name IS NULL OR UPPER(s.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))) "
			+ "ORDER BY s.date DESC, s.seller.id DESC")
	Page<SaleReportDTO> getReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);

	@Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(s.seller.id, s.seller.name, COALESCE(SUM(s.amount), 0)) "
			+ "FROM Sale s " + "WHERE (:minDate IS NULL OR s.date >= :minDate) "
			+ "AND (:maxDate IS NULL OR s.date <= :maxDate) " + "GROUP BY s.seller.id, s.seller.name "
			+ "ORDER BY SUM(s.amount) DESC")
	List<SaleSummaryDTO> getSummary(LocalDate minDate, LocalDate maxDate);
}