package tw.com.james.coffeebean.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.com.james.coffeebean.dto.StockCreateDto;
import tw.com.james.coffeebean.dto.StockUpdateDto;
import tw.com.james.coffeebean.service.StockService;
import tw.com.james.coffeebean.vo.StockVo;

@Tag(name = "Stock Controller", description = "庫存管理增刪改查相關的 API")
@RestController
@RequestMapping("/api/v1/stock")
@CrossOrigin(origins = "http://localhost:5173")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    // ===== POST =====
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "成功建立庫存"),
            @ApiResponse(responseCode = "400", description = "輸入資料不合法", content = @Content),
            @ApiResponse(responseCode = "404", description = "找不到網頁", content = @Content),
    })
    @Operation(summary = "新增庫存", description = "建立一筆咖啡豆庫存資料")
    @PostMapping(produces = "application/json")
    public ResponseEntity<StockVo> create(@RequestBody StockCreateDto dto) {
        return ResponseEntity.status(201).body(stockService.create(dto));
    }

    // ===== PUT =====
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功更新庫存"),
            @ApiResponse(responseCode = "400", description = "輸入資料不合法", content = @Content),
            @ApiResponse(responseCode = "404", description = "找不到庫存資料", content = @Content),
    })
    @Operation(summary = "更新庫存", description = "依 ID 更新庫存資料")
    @PutMapping
    public ResponseEntity<StockVo> update(@RequestBody StockUpdateDto dto) {
        StockVo updated = stockService.update(dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // ===== DELETE =====
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "成功刪除庫存"),
            @ApiResponse(responseCode = "404", description = "找不到庫存資料", content = @Content),
    })
    @Operation(summary = "刪除庫存", description = "依 ID 刪除指定庫存資料")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean deleted = stockService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    // ===== GET Page =====
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功取得庫存列表"),
    })
    @Operation(summary = "取得庫存分頁列表", description = "依頁碼與筆數取得庫存資料")
    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<StockVo>> getStockPage(
            @PathVariable int page,
            @PathVariable int size
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("id").descending()
        );
        return ResponseEntity.ok(stockService.findAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<StockVo>> search(
            @RequestParam(required = false) Integer coffeeBeanId,
            @RequestParam(required = false) Integer minStockG,
            @RequestParam(required = false) Integer maxStockG,
            @RequestParam(required = false) String purchaseDateFrom,
            @RequestParam(required = false) String purchaseDateTo
    ) {

        Pageable pageable = PageRequest.of(
                0,
                10,
                Sort.by("id").descending()
        );

        return ResponseEntity.ok(
                stockService.search(
                        coffeeBeanId,
                        minStockG,
                        maxStockG,
                        purchaseDateFrom,
                        purchaseDateTo,
                        pageable
                )
        );
    }

}
