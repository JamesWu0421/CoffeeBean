package tw.com.james.coffeebean.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.com.james.coffeebean.dto.GreenBeanCreateDto;
import tw.com.james.coffeebean.dto.GreenBeanUpdateDto;
import tw.com.james.coffeebean.dto.mapper.GreenBeanDtoMapper;
import tw.com.james.coffeebean.entity.CoffeeBean;
import tw.com.james.coffeebean.repository.CoffeeBeanRepository;
import tw.com.james.coffeebean.vo.GreenBeanVo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GreenBeanService {

    private final CoffeeBeanRepository coffeeBeanRepo;
    private final GreenBeanDtoMapper greenBeanDtoMapper;

    public GreenBeanService(
            CoffeeBeanRepository coffeeBeanRepo,
            GreenBeanDtoMapper greenBeanDtoMapper
    ) {
        this.coffeeBeanRepo = coffeeBeanRepo;
        this.greenBeanDtoMapper = greenBeanDtoMapper;
    }

    // ===== CREATE =====
    @Transactional
    public GreenBeanVo create(GreenBeanCreateDto dto) {
        CoffeeBean exist = coffeeBeanRepo.findByUnique(
                dto.getProductionYear(), 
                dto.getRegion(), 
                dto.getProcessingPlant()
        );
        if (exist != null) {
            // 這裡看你要拋 Exception 還是直接回傳既有的
            // throw new RuntimeException("資料已存在"); 
        }

        // 2. 轉換並存檔
        CoffeeBean entity = greenBeanDtoMapper.toEntity(dto);
        
        // 呼叫 Repo 的 save (裡面會走 persist)
        CoffeeBean savedEntity = coffeeBeanRepo.save(entity);
        
        return greenBeanDtoMapper.toVO(savedEntity);
    }

    // ===== UPDATE =====
    @Transactional
    public GreenBeanVo update(GreenBeanUpdateDto dto) {
        CoffeeBean existingEntity = coffeeBeanRepo.findById(dto.getId());
        if (existingEntity == null) {
            return null; 
        }

        greenBeanDtoMapper.updateEntity(dto, existingEntity);


        CoffeeBean updatedEntity = coffeeBeanRepo.save(existingEntity);
        
        return greenBeanDtoMapper.toVO(updatedEntity);
    }

    // ===== DELETE =====
    @Transactional
    public boolean delete(Integer id) {
        CoffeeBean existing = coffeeBeanRepo.findById(id);
        if (existing == null) {
            return false;
        }
        coffeeBeanRepo.deleteById(id);
        return true;
    }

    // ===== READ : Pageable =====
    @Transactional(readOnly = true)
    public Page<GreenBeanVo> findAll(Pageable pageable) {
        List<CoffeeBean> all = coffeeBeanRepo.findAll();
        
        int total = all.size();
        int start = (int) pageable.getOffset();

        if (start >= total) {
            return new PageImpl<>(List.of(), pageable, total);
        }

        int end = Math.min(start + pageable.getPageSize(), total);

        List<GreenBeanVo> content = all.subList(start, end)
                .stream()
                .map(greenBeanDtoMapper::toVO)
                .toList();

        return new PageImpl<>(content, pageable, total);
    }

    // ===== READ : List =====
    @Transactional(readOnly = true)
    public List<GreenBeanVo> findAllList() {
        return coffeeBeanRepo.findAll().stream()
                .map(greenBeanDtoMapper::toVO)
                .collect(Collectors.toList());
    }
}