package com.fseproj.stockprice.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fseproj.stockprice.dao.StockPriceRepository;
import com.fseproj.stockprice.dto.CompanyCompareRequest;
import com.fseproj.stockprice.dto.StockPriceDto;
import com.fseproj.stockprice.mapper.StockPriceMapper;
import com.fseproj.stockprice.model.StockPrice;
import com.fseproj.stockprice.service.StockPriceService;

@Service
public class StockPriceServiceImpl implements StockPriceService {
	
	@Autowired
	private StockPriceRepository stockPriceRepository;
	
	@Autowired
	private StockPriceMapper stockPriceMapper;

	@Override
	public List<StockPriceDto> findAll() {
		// TODO Auto-generated method stub
		List<StockPrice> stockPrices = stockPriceRepository.findAll();
		return stockPriceMapper.toStockPriceDtos(stockPrices);
	}

	@Override
	public StockPriceDto findById(String id) {
		// TODO Auto-generated method stub
		Optional<StockPrice> stockPrice = stockPriceRepository.findById(id);
		if(!stockPrice.isPresent())
			return null;
		return stockPriceMapper.toStockPriceDto(stockPrice.get());
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		stockPriceRepository.deleteById(id);

	}

	@Override
	public List<StockPriceDto> save(List<StockPriceDto> stockPriceDtos) {
		// TODO Auto-generated method stub
		List<StockPrice> stockPrices = stockPriceMapper.toStockPrices(stockPriceDtos);
		stockPrices = stockPriceRepository.saveAll(stockPrices);
		return stockPriceMapper.toStockPriceDtos(stockPrices);
	}

	@Override
	public StockPriceDto update(StockPriceDto stockPriceDto) {
		// TODO Auto-generated method stub
		StockPriceDto checkStockPriceDto = findById(stockPriceDto.getId());
		if(checkStockPriceDto == null)
			return null;
		StockPrice stockPrice = stockPriceRepository.save(stockPriceMapper.toStockPrice(checkStockPriceDto));
		return stockPriceMapper.toStockPriceDto(stockPrice);
	}

	@Override
	public List<StockPriceDto> getStockPricesForCompanyComparison(CompanyCompareRequest compareRequest)
			throws ParseException {
		// TODO Auto-generated method stub
		Date fromDate = new SimpleDateFormat("dd-MM-yyyy").parse(compareRequest.getFromPeriod());
		Date toDate = new SimpleDateFormat("dd-MM-yyyy").parse(compareRequest.getToPeriod());
		List<StockPrice> stockPrices = stockPriceRepository.findByCompanyCodeAndStockExchangeName(compareRequest.getCompanyCode(), compareRequest.getStockExchangeName());
		List<StockPrice> filteredList = stockPrices.stream()
				.filter(stockPrice -> {
					Date date = null;
					try {
						date = new SimpleDateFormat("dd-MM-yyyy").parse(stockPrice.getDate());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return date.after(fromDate) && date.before(toDate);
				})
				.collect(Collectors.toList());
		return stockPriceMapper.toStockPriceDtos(filteredList);
	}

	@Override
	public void save(StockPriceDto stockPriceDto) {
		// TODO Auto-generated method stub
		stockPriceRepository.save(stockPriceMapper.toStockPrice(stockPriceDto));
	}

}
