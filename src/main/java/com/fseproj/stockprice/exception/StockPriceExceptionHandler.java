package com.fseproj.stockprice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class StockPriceExceptionHandler {
	
	public ResponseEntity<ErrorResponse> handleStockPriceNotFoundException(StockPriceNotFoundException e) {
		
		ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value(), System.currentTimeMillis());
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(errorResponse);
	}

}
