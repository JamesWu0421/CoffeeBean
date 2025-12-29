package tw.com.james.coffeebean.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.james.coffeebean.dto.CountryDto;
import tw.com.james.coffeebean.dto.mapper.CountryDtoMapper;
import tw.com.james.coffeebean.entity.Country;
import tw.com.james.coffeebean.repository.CountryRepository;
import tw.com.james.coffeebean.vo.CountryVo;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private final CountryRepository countryRepo;
    private final CountryDtoMapper countryDtoMapper;

    public CountryService(
            CountryRepository countryRepo,
            CountryDtoMapper countryDtoMapper
    ) {
        this.countryRepo = countryRepo;
        this.countryDtoMapper = countryDtoMapper;
    }

    // ===== CREATE =====
    @Transactional
    public CountryVo create(CountryDto dto) {
        Country entity = countryDtoMapper.toEntity(dto);
        countryRepo.save(entity);
        return countryDtoMapper.toVO(entity);
    }

    // ===== DELETE =====
    @Transactional
    public boolean delete(Integer id) {

        Country country = countryRepo.findById(id);
        if (country == null) {
            return false;
        }

        countryRepo.deleteById(id);
        return true;
    }

    // ===== UPDATE =====
    @Transactional
    public CountryVo update(CountryDto dto) {

        Country country = countryRepo.findById(dto.getId());
        if (country == null) {
            return null;
        }

        country.setCountryName(dto.getCountryName());
        country.setCountryEngName(dto.getCountryEngName());
        country.setCountryCode(dto.getCountryCode());

        countryRepo.update(country);
        return countryDtoMapper.toVO(country);
    }


    // ===== READ : list + pageable =====
    @Transactional(readOnly = true)
    public Page<CountryVo> findAll(Pageable pageable) {

        List<Country> all = countryRepo.findAll();
        int total = all.size();
        int start = (int) pageable.getOffset();

        if (start >= total) {
            return new PageImpl<>(List.of(), pageable, total);
        }

        int end = Math.min(start + pageable.getPageSize(), total);

        List<CountryVo> content = all.subList(start, end)
                .stream()
                .map(countryDtoMapper::toVO)
                .toList();

        return new PageImpl<>(content, pageable, total);
    }

    public List<CountryVo> search(
            String code,
            String name,
            String engName
    ) {
        List<Country> list =
                countryRepo.search(code, name, engName);

        return list.stream()
                .map(countryDtoMapper::toVO)
                .toList();
    }


    @Transactional(readOnly = true)
    public List<CountryVo> findAllList() {
        return countryRepo.findAll().stream()
                .map(countryDtoMapper::toVO)
                .collect(Collectors.toList());
    }

    
}
