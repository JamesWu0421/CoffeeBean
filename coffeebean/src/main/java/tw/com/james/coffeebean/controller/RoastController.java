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
import tw.com.james.coffeebean.dto.RoastCreateDto;
import tw.com.james.coffeebean.dto.RoastUpdateDto;
import tw.com.james.coffeebean.service.RoastService;
import tw.com.james.coffeebean.vo.RoastVo;

@Tag(name = "Roast Controller", description = "烘焙紀錄增刪改查相關的 API")
@RestController
@RequestMapping("/api/v1/roast")
public class RoastController {

    private final RoastService roastService;

    public RoastController(RoastService roastService) {
        this.roastService = roastService;
    }

    // ===== POST =====
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "成功建立烘焙紀錄"),
            @ApiResponse(responseCode = "400", description = "輸入資料不合法", content = @Content),
            @ApiResponse(responseCode = "404", description = "找不到網頁", content = @Content),
    })
    @Operation(summary = "新增烘焙紀錄", description = "建立一筆新的烘焙紀錄")
    @PostMapping(produces = "application/json")
    public ResponseEntity<RoastVo> create(@RequestBody RoastCreateDto dto) {
        return ResponseEntity.status(201).body(roastService.create(dto));
    }

    // ===== PUT =====
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功更新烘焙紀錄"),
            @ApiResponse(responseCode = "400", description = "輸入資料不合法", content = @Content),
            @ApiResponse(responseCode = "404", description = "找不到烘焙紀錄", content = @Content),
    })
    @Operation(summary = "更新烘焙紀錄", description = "依 ID 更新烘焙紀錄資料")
    @PutMapping
    public ResponseEntity<RoastVo> update(@RequestBody RoastUpdateDto dto) {
        RoastVo updated = roastService.update(dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // ===== DELETE =====
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "成功刪除烘焙紀錄"),
            @ApiResponse(responseCode = "404", description = "找不到烘焙紀錄", content = @Content),
    })
    @Operation(summary = "刪除烘焙紀錄", description = "依 ID 刪除指定烘焙紀錄")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean deleted = roastService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    // ===== GET Page =====
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功取得烘焙紀錄列表"),
    })
    @Operation(summary = "取得烘焙紀錄分頁列表", description = "依頁碼與筆數取得烘焙紀錄資料")
    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<RoastVo>> getRoastPage(
            @PathVariable int page,
            @PathVariable int size
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("roastId").descending()
        );
        return ResponseEntity.ok(roastService.findAll(pageable));
    }
}
