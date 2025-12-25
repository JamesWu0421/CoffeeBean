package tw.com.james.coffeebean.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.com.james.coffeebean.entity.BeanMerchant;
import tw.com.james.coffeebean.service.BeanMerchantService;

@RestController
@RequestMapping("/api/v1/bean-merchant")
public class BeanMerchantController {

    private final BeanMerchantService service;

    public BeanMerchantController(BeanMerchantService service) {
        this.service = service;
    }

    /* ===== Create ===== */
    @PostMapping
    public ResponseEntity<BeanMerchant> create(
            @RequestBody BeanMerchant merchant) {

        return ResponseEntity.ok(service.create(merchant));
    }

    /* ===== Update ===== */
    @PutMapping
    public ResponseEntity<BeanMerchant> update(
            @RequestBody BeanMerchant merchant) {

        BeanMerchant updated = service.update(merchant);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    /* ===== Delete ===== */
    @DeleteMapping
    public ResponseEntity<Boolean> delete(
            @RequestBody BeanMerchant merchan) {

        return ResponseEntity.ok(
                service.delete(merchan.getId())
        );
    }

    /* ===== Read ===== */
    @GetMapping
    public ResponseEntity<Page<BeanMerchant>> findAll(
            @RequestParam(defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 10);
        return ResponseEntity.ok(service.findAll(pageable));
    }
}
