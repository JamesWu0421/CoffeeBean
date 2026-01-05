package tw.com.james.coffeebean.service;

import jakarta.persistence.criteria.Predicate; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tw.com.james.coffeebean.dto.RoastCreateDto;
import tw.com.james.coffeebean.dto.RoastUpdateDto;
import tw.com.james.coffeebean.dto.mapper.RoastDtoMapper;
import tw.com.james.coffeebean.entity.Roast;
import tw.com.james.coffeebean.repository.RoastRepository;
import tw.com.james.coffeebean.vo.RoastVo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoastService {

    private final RoastRepository roastRepo;
    private final RoastDtoMapper roastMapper;

    public RoastService(RoastRepository roastRepo, RoastDtoMapper roastMapper) {
        this.roastRepo = roastRepo;
        this.roastMapper = roastMapper;
    }

    @Transactional
    public RoastVo create(RoastCreateDto dto) {
        Roast entity = roastMapper.toEntity(dto);
        return roastMapper.toVo(roastRepo.save(entity));
    }

    @Transactional
    public RoastVo update(RoastUpdateDto dto) {
        Roast entity = roastRepo.findById(dto.getRoastId()).orElse(null);
        if (entity == null) {
            return null;
        }
        roastMapper.updateEntity(dto, entity);
        return roastMapper.toVo(roastRepo.save(entity));
    }

    @Transactional
    public boolean delete(Integer id) {
        if (!roastRepo.existsById(id)) {
            return false;
        }
        roastRepo.deleteById(id);
        return true;
    }

    @Transactional(readOnly = true)
    public Page<RoastVo> findAll(Pageable pageable) {
        return roastRepo.findAll(pageable).map(roastMapper::toVo);
    }

    // ===== 新增 Search 方法 =====
    @Transactional(readOnly = true)
    public Page<RoastVo> search(Integer coffeeBeanId,
                                String batchNo,
                                String roastLevel,
                                LocalDate roastDateFrom,
                                LocalDate roastDateTo,
                                Pageable pageable) {

        Specification<Roast> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. 咖啡豆 ID 搜尋
            if (coffeeBeanId != null) {
                // 注意: 這裡假設 CoffeeBean Entity 的主鍵欄位名稱為 "id"
                // 如果是 "beanId"，請改成 root.get("coffeeBean").get("beanId")
                predicates.add(cb.equal(root.get("coffeeBean").get("id"), coffeeBeanId));
            }

            // 2. 批次號 (模糊搜尋)
            if (StringUtils.hasText(batchNo)) {
                predicates.add(cb.like(root.get("batchNo"), "%" + batchNo + "%"));
            }

            // 3. 烘焙度 (模糊搜尋)
            if (StringUtils.hasText(roastLevel)) {
                predicates.add(cb.like(root.get("roastLevel"), "%" + roastLevel + "%"));
            }

            // 4. 日期區間 (起)
            if (roastDateFrom != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("roastDate"), roastDateFrom));
            }

            // 5. 日期區間 (迄)
            if (roastDateTo != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("roastDate"), roastDateTo));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 查詢後轉換為 Vo
        return roastRepo.findAll(spec, pageable).map(roastMapper::toVo);
    }
}