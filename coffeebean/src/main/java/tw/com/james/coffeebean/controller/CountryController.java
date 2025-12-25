package tw.com.james.coffeebean.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.com.james.coffeebean.entity.Country;
import tw.com.james.coffeebean.service.CountryService;

@RestController
@RequestMapping("/api/v1/country")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    // ===== POST =====
    @PostMapping
    public ResponseEntity<Country> create(
            @RequestBody Country country) {

        return ResponseEntity.ok(countryService.create(country));
    }

    // ===== DELETE =====
    @DeleteMapping
    public ResponseEntity<Boolean> delete(
            @RequestBody Country country) {

        return ResponseEntity.ok(
                countryService.delete(country.getId())
        );
    }

    // ===== PUT =====
    @PutMapping
    public ResponseEntity<Country> update(
            @RequestBody Country country) {

        Country updated = countryService.update(country);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }

    // ===== GET (pageable) =====
    @GetMapping
    public ResponseEntity<Page<Country>> findAll(
            @RequestParam(defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 10);
        return ResponseEntity.ok(countryService.findAll(pageable));
    }

}
