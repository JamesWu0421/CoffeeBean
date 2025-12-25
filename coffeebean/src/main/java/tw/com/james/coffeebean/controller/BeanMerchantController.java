package tw.com.james.coffeebean.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import tw.com.james.coffeebean.dto.BeanMerchantDto;
import tw.com.james.coffeebean.service.BeanMerchantService;
import tw.com.james.coffeebean.vo.BeanMerchantVo;

@Tag(name = "Bean Merchant Controller", description = "豆商增刪改查相關的 API")
@RestController
@RequestMapping("/api/v1/bean-merchant")
public class BeanMerchantController {

    private final BeanMerchantService service;

    public BeanMerchantController(BeanMerchantService service) {
        this.service = service;
    }

    // ===== POST =====
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功建立豆商資料"),
            @ApiResponse(responseCode = "400", description = "豆商資料不合理", content = @Content),
    })
    @Operation(summary = "建立豆商", description = "建立一筆豆商資料。")
    @PostMapping(produces = "application/json")
    public ResponseEntity<BeanMerchantVo> create(
        @RequestBody BeanMerchantDto dto) {

    return ResponseEntity.ok(service.create(dto));
    }

    // ===== DELETE =====
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "成功刪除豆商資料"),
            @ApiResponse(responseCode = "404", description = "找不到豆商", content = @Content),
    })
    @Operation(summary = "刪除豆商", description = "刪除指定 id 的豆商資料。")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id) {

        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ===== PUT =====
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功更新豆商資料"),
            @ApiResponse(responseCode = "400", description = "豆商資料不合理", content = @Content),
            @ApiResponse(responseCode = "404", description = "找不到豆商", content = @Content),
    })
    @Operation(summary = "更新豆商", description = "更新指定豆商資料。")
    @PutMapping
    public ResponseEntity<BeanMerchantVo> update(
            @RequestBody BeanMerchantDto dto) {

        BeanMerchantVo updated = service.update(dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }

    // ===== GET (pageable) =====
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功取得豆商列表"),
    })
    @Operation(summary = "取得豆商列表", description = "根據頁碼以及顯示筆數取得多筆豆商資料。")
    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<BeanMerchantVo>> findAll(
            @PathVariable int page,
            @PathVariable int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<BeanMerchantVo> result = service.findAll(pageable);

        return ResponseEntity.ok(result);
    }
}
