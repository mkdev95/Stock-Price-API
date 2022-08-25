package com.fseproj.stockprice.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="stockprice")
public class StockPrice {
	
	private String id;
	private String companyCode;
	private String stockExchangeName;
	private double price;
	private String date;
	private String time;

}
