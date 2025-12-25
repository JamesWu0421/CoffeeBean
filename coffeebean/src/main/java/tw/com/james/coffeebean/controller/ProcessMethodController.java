package tw.com.james.coffeebean.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tw.com.james.coffeebean.entity.ProcessMethod;
import tw.com.james.coffeebean.service.ProcessMethodService;

@RestController
@RequestMapping("/api/v1/process-method")
public class ProcessMethodController {

    private final ProcessMethodService service;

    public ProcessMethodController(ProcessMethodService service) {
        this.service = service;
    }

    /* ===== Create ===== */
    @PostMapping
    public ResponseEntity<ProcessMethod> create(
            @RequestBody ProcessMethod method) {

        return ResponseEntity.ok(service.create(method));
    }

    /* ===== Update ===== */
    @PutMapping
    public ResponseEntity<ProcessMethod> update(
            @RequestBody ProcessMethod method) {

        ProcessMethod updated = service.update(method);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    /* ===== Delete ===== */
    @DeleteMapping
    public ResponseEntity<Boolean> delete(
            @RequestBody ProcessMethod method) {

        return ResponseEntity.ok(
                service.delete(method.getId())
        );
    }
    /* ===== Read ===== */
    @GetMapping
    public ResponseEntity<Page<ProcessMethod>> findAll(
            @RequestParam(defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 10);
        return ResponseEntity.ok(service.findAll(pageable));
    }
}
