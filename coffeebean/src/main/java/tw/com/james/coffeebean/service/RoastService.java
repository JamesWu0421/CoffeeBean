package tw.com.james.coffeebean.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.com.james.coffeebean.dto.RoastCreateDto;
import tw.com.james.coffeebean.dto.RoastUpdateDto;
import tw.com.james.coffeebean.dto.mapper.RoastDtoMapper;
import tw.com.james.coffeebean.entity.Roast;
import tw.com.james.coffeebean.repository.RoastRepository;
import tw.com.james.coffeebean.vo.RoastVo;

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
}