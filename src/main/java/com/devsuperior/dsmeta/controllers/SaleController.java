package com.devsuperior.dsmeta.controllers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.services.SaleService;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	

	  @GetMapping(value = "/report")
	    public ResponseEntity<Page<SaleReportDTO>> getReport(
	            @RequestParam(value = "name", defaultValue = "") String name, 
	            @RequestParam(value = "minDate", required = false) String minDate,
	            @RequestParam(value = "maxDate", required = false) String maxDate,
	            Pageable pageable) {

	       
	        LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
	        LocalDate finalMinDate = (minDate == null || minDate.isEmpty()) ? today.minusYears(1) : LocalDate.parse(minDate);
	        LocalDate finalMaxDate = (maxDate == null || maxDate.isEmpty()) ? today : LocalDate.parse(maxDate);

	        
	        Page<SaleReportDTO> result = service.getReport(finalMinDate.toString(), finalMaxDate.toString(), name, pageable);
	        
	        return ResponseEntity.ok(result);
	    }

	  @GetMapping(value = "/summary")
	  public ResponseEntity<?> getSummary(
	          @RequestParam(value = "minDate", required = false) String minDate,
	          @RequestParam(value = "maxDate", required = false) String maxDate) {

	      return ResponseEntity.ok(service.getSummary(minDate, maxDate));
	  }
}
