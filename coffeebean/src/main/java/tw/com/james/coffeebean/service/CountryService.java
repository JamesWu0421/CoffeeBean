package tw.com.james.coffeebean.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.com.james.coffeebean.entity.Country;
import tw.com.james.coffeebean.repository.CountryRepository;

import java.util.List;

@Service
public class CountryService {

    private final CountryRepository countryRepo;

    public CountryService(CountryRepository countryRepo) {
        this.countryRepo = countryRepo;
    }

    // ===== CREATE =====
    @Transactional
    public Country create(Country country) {
        countryRepo.save(country);
        return country;
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
    public Country update(Country req) {

        Country country = countryRepo.findById(req.getId());
        if (country == null) {
            return null;
        }

        country.setCountryName(req.getCountryName());
        country.setCountryEngName(req.getCountryEngName());
        country.setCountryCode(req.getCountryCode());

        countryRepo.update(country);
        return country;
    }

    // ===== READ : list + pageable =====
    @Transactional(readOnly = true)
    public Page<Country> findAll(Pageable pageable) {

        List<Country> all = countryRepo.findAll();
        int total = all.size();
        int start = (int) pageable.getOffset();

        if (start >= total) {
            return new PageImpl<>(
                    List.of(),        
                    pageable,
                    total
            );
        }

        int end = Math.min(start + pageable.getPageSize(), total);

        return new PageImpl<>(
                all.subList(start, end),
                pageable,
                total
        );
    }
}
