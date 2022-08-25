package com.fseproj.stockprice.service;

import java.text.ParseException;
import java.util.List;

import com.fseproj.stockprice.dto.CompanyCompareRequest;
import com.fseproj.stockprice.dto.StockPriceDto;

public interface StockPriceService {
	
	public List<StockPriceDto> findAll();
	public StockPriceDto findById(String id);
	public void deleteById(String id);
	public List<StockPriceDto> save(List<StockPriceDto> stockPriceDtos);
	public StockPriceDto update(StockPriceDto stockPriceDto);
	public List<StockPriceDto> getStockPricesForCompanyComparison(CompanyCompareRequest compareRequest) throws ParseException;
	public void save(StockPriceDto stockPriceDto);

}
