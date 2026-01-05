package tw.com.james.coffeebean.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.com.james.coffeebean.dto.StockCreateDto;
import tw.com.james.coffeebean.dto.StockUpdateDto;
import tw.com.james.coffeebean.dto.mapper.StockDtoMapper;
import tw.com.james.coffeebean.entity.Stock;
import tw.com.james.coffeebean.repository.StockRepository;
import tw.com.james.coffeebean.vo.StockVo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {

    private final StockRepository stockRepo;
    private final StockDtoMapper stockMapper;

    public StockService(StockRepository stockRepo, StockDtoMapper stockMapper) {
        this.stockRepo = stockRepo;
        this.stockMapper = stockMapper;
    }

    @Transactional
    public StockVo create(StockCreateDto dto) {
        Stock entity = stockMapper.toEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());
        return stockMapper.toVo(stockRepo.save(entity));
    }

    @Transactional
    public StockVo update(StockUpdateDto dto) {
        Stock entity = stockRepo.findById(dto.getId());
        
        if (entity == null) {
            return null;
        }
        stockMapper.updateEntity(dto, entity);
        return stockMapper.toVo(stockRepo.save(entity));
    }

    @Transactional
    public boolean delete(Integer id) {
        if (!stockRepo.existsById(id)) {
            return false;
        }
        stockRepo.deleteById(id);
        return true;
    }

    @Transactional(readOnly = true)
    public Page<StockVo> findAll(Pageable pageable) {
        List<Stock> all = stockRepo.findAll(); 
        
        int total = all.size();
        int start = (int) pageable.getOffset();

        if (start >= total) {
            return new PageImpl<>(List.of(), pageable, total);
        }

        int end = Math.min(start + pageable.getPageSize(), total);

        List<StockVo> content = all.subList(start, end)
                .stream()
                .map(stockMapper::toVo)
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, total);
    }

    @Transactional(readOnly = true)
    public Page<StockVo> search(
            Integer coffeeBeanId,
            Integer minStockG,
            Integer maxStockG,
            String purchaseDateFrom,
            String purchaseDateTo,
            Pageable pageable
    ) {
        Page<Stock> result = stockRepo.search(
                coffeeBeanId,
                minStockG,
                maxStockG,
                isEmpty(purchaseDateFrom) ? null : purchaseDateFrom,
                isEmpty(purchaseDateTo) ? null : purchaseDateTo,
                pageable
        );

        return result.map(stockMapper::toVo);
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

}