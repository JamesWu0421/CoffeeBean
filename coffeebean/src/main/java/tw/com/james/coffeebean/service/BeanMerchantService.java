package tw.com.james.coffeebean.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.com.james.coffeebean.dto.BeanMerchantDto;
import tw.com.james.coffeebean.entity.BeanMerchant;
import tw.com.james.coffeebean.dto.mapper.BeanMerchantMapper;
import tw.com.james.coffeebean.repository.BeanMerchantRepository;
import tw.com.james.coffeebean.vo.BeanMerchantVo;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class BeanMerchantService {


    private final BeanMerchantRepository repo;
    private final BeanMerchantMapper mapper;

    public BeanMerchantService(
            BeanMerchantRepository repo,
            BeanMerchantMapper mapper
    ) {
        this.repo = repo;
        this.mapper = mapper;
        
    }

    // ===== CREATE =====
    @Transactional
    public BeanMerchantVo create(BeanMerchantDto dto) {

        BeanMerchant entity = mapper.toEntity(dto);
        repo.save(entity);

        return mapper.toVo(entity);
    }

    // ===== DELETE =====
    @Transactional
    public boolean delete(Integer id) {

        BeanMerchant entity = repo.findById(id);
        if (entity == null) {
            return false;
        }

        repo.delete(id);
        return true;
    }

    // ===== UPDATE =====
    @Transactional
    public BeanMerchantVo update(BeanMerchantDto dto) {

        BeanMerchant entity = repo.findById(dto.getId());
        if (entity == null) {
            return null;
        }

        entity.setMerchantName(dto.getMerchantName());
        repo.update(entity);

        return mapper.toVo(entity);
    }

    // ===== READ : list + pageable =====
    @Transactional(readOnly = true)
    public Page<BeanMerchantVo> findAll(Pageable pageable) {

        List<BeanMerchant> all = repo.findAll();
        int total = all.size();
        int start = (int) pageable.getOffset();

        if (start >= total) {
            return new PageImpl<>(List.of(), pageable, total);
        }

        int end = Math.min(start + pageable.getPageSize(), total);

        List<BeanMerchantVo> content = all.subList(start, end)
                .stream()
                .map(mapper::toVo)
                .toList();

        return new PageImpl<>(content, pageable, total);
    }

    @Transactional(readOnly = true)
    public List<BeanMerchantVo> findAllList() {
        return repo.findAll().stream()
                .map(mapper::toVo)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BeanMerchant> search(String name) {
        return repo.search(name);
    }
}
