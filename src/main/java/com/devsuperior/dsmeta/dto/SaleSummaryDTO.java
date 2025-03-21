package com.devsuperior.dsmeta.dto;

	public class SaleSummaryDTO {
		private Long id;
		private String sellerName;
		private Double total;
		
	
    public SaleSummaryDTO(Long id, String sellerName, Double total) {
    	this.id = id;
        this.sellerName = sellerName;
        this.total = total;
    }
    
    public SaleSummaryDTO() {
    }
    
    public Long getId() {
        return id;
    }

    public String getSellerName() {
        return sellerName;
    }

    public Double getTotal() {
        return total;
    }
}
