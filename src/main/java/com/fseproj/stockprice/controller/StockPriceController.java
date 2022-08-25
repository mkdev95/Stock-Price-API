package com.fseproj.stockprice.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fseproj.stockprice.dto.CompanyCompareRequest;
import com.fseproj.stockprice.dto.StockPriceDto;
import com.fseproj.stockprice.exception.StockPriceNotFoundException;
import com.fseproj.stockprice.service.StockPriceService;

@RestController
@RequestMapping("/stockprice/stockprices")
public class StockPriceController {
	
	@Autowired
	private StockPriceService stockPriceService;
	
	@GetMapping(path = "")
	public ResponseEntity<List<StockPriceDto>> findAll() {
		return ResponseEntity.ok(stockPriceService.findAll());
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<StockPriceDto> findById(@PathVariable String id) throws StockPriceNotFoundException {
		StockPriceDto stockPriceDto = stockPriceService.findById(id);
		if(stockPriceDto == null)
			throw new StockPriceNotFoundException("Stock Price details not found with id: " + id);
		return ResponseEntity.ok(stockPriceDto);
	}
	
	@GetMapping(path = "/compareCompany")
	public ResponseEntity<?> companyComparison(@RequestBody CompanyCompareRequest compareRequest) {
		List<StockPriceDto> stockPriceDtos = null;
		try {
			stockPriceDtos = stockPriceService.getStockPricesForCompanyComparison(compareRequest);
		} catch (ParseException e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body("Invalid Date Format");
		}
		return ResponseEntity.ok(stockPriceDtos);
	}
	
	@PostMapping(path = "")
	public ResponseEntity<?> save(@RequestBody List<StockPriceDto> stockPriceDtos) {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(stockPriceService.save(stockPriceDtos));
	}
	
	@PostMapping(path = "/add")
	public void save(@RequestBody StockPriceDto stockPriceDto) {
		stockPriceService.save(stockPriceDto);
	}
	
	@PutMapping(path = "")
	public ResponseEntity<StockPriceDto> update(@RequestBody StockPriceDto stockPriceDto) throws StockPriceNotFoundException {
		StockPriceDto updatedStockPriceDto = stockPriceService.update(stockPriceDto);
		if(updatedStockPriceDto == null)
			throw new StockPriceNotFoundException("Stock Price for this id is not found : " + stockPriceDto.getId());
		return ResponseEntity.ok(updatedStockPriceDto);
	}
	
	@DeleteMapping(path = "/{id}")
	public void deleteById(@PathVariable String id) {
		stockPriceService.deleteById(id);
	}

}
