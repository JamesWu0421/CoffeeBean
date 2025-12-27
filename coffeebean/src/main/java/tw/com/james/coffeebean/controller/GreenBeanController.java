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
import tw.com.james.coffeebean.dto.GreenBeanCreateDto;
import tw.com.james.coffeebean.dto.GreenBeanUpdateDto;
import tw.com.james.coffeebean.service.GreenBeanService;
import tw.com.james.coffeebean.vo.GreenBeanVo;

import java.util.List;

@Tag(name = "Coffee Bean Controller", description = "咖啡豆增刪改查相關的 API")
@RestController
@RequestMapping("/api/v1/green-bean")
public class GreenBeanController {

    private final GreenBeanService greenBeanService;

    public GreenBeanController(GreenBeanService greenBeanService) {
        this.greenBeanService = greenBeanService;
    }

    // ===== POST =====
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "成功建立咖啡豆"),
            @ApiResponse(responseCode = "400", description = "輸入資料不合法", content = @Content),
            @ApiResponse(responseCode = "404", description = "找不到網頁", content = @Content),
    })
    @Operation(summary = "新增咖啡豆", description = "建立一筆咖啡豆基本資料")
    @PostMapping(produces = "application/json")
    public ResponseEntity<GreenBeanVo> create(@RequestBody GreenBeanCreateDto dto) {
        return ResponseEntity.status(201).body(greenBeanService.create(dto));
    }

    // ===== PUT =====
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功更新咖啡豆"),
            @ApiResponse(responseCode = "400", description = "輸入資料不合法", content = @Content),
            @ApiResponse(responseCode = "404", description = "找不到網頁", content = @Content),
    })
    @Operation(summary = "更新咖啡豆", description = "依 ID 更新咖啡豆資料")
    @PutMapping
    public ResponseEntity<GreenBeanVo> update(@RequestBody GreenBeanUpdateDto dto) {
        GreenBeanVo updated = greenBeanService.update(dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // ===== DELETE =====
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "成功刪除咖啡豆"),
            @ApiResponse(responseCode = "404", description = "找不到咖啡豆", content = @Content),
    })
    @Operation(summary = "刪除咖啡豆", description = "依 ID 刪除指定咖啡豆")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean deleted = greenBeanService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    // ===== GET Page =====
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功取得咖啡豆列表"),
    })
    @Operation(summary = "取得咖啡豆分頁列表", description = "依頁碼與筆數取得咖啡豆資料")
    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<GreenBeanVo>> getGreenBeanPage(
            @PathVariable int page,
            @PathVariable int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return ResponseEntity.ok(greenBeanService.findAll(pageable));
    }

    // ===== GET Options =====
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功取得下拉選單資料"),
    })
    @Operation(summary = "取得咖啡豆下拉選單資料", description = "取得所有咖啡豆，供下拉式選單使用")
    @GetMapping("/options")
    public ResponseEntity<List<GreenBeanVo>> getGreenBeanOptions() {
        return ResponseEntity.ok(greenBeanService.findAllList());
    }
}