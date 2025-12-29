package tw.com.james.coffeebean.controller;

import java.util.List;

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
import tw.com.james.coffeebean.dto.ProcessMethodDto;
import tw.com.james.coffeebean.entity.ProcessMethod;
import tw.com.james.coffeebean.service.ProcessMethodService;
import tw.com.james.coffeebean.vo.ProcessMethodVo;

@Tag(name = "Process Method Controller", description = "處理法增刪改查相關的 API")
@RestController
@RequestMapping("/api/v1/process-method")
@CrossOrigin(origins = "http://localhost:5173")
public class ProcessMethodController {

    private final ProcessMethodService service;

    public ProcessMethodController(ProcessMethodService service) {
        this.service = service;
    }

    // ===== POST =====
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功建立處理法資料"),
            @ApiResponse(responseCode = "400", description = "處理法資料不合理", content = @Content),
    })
    @Operation(summary = "建立處理法", description = "建立一筆處理法資料。")
    @PostMapping(produces = "application/json")
    public ResponseEntity<ProcessMethodVo> create(
            @RequestBody ProcessMethodDto method) {

        return ResponseEntity.ok(service.create(method));
    }

    // ===== DELETE =====
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "成功刪除處理法資料"),
            @ApiResponse(responseCode = "404", description = "找不到處理法", content = @Content),
    })
    @Operation(summary = "刪除處理法", description = "刪除指定 id 的處理法資料。")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id) {

        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ===== PUT =====
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功更新處理法資料"),
            @ApiResponse(responseCode = "400", description = "處理法資料不合理", content = @Content),
            @ApiResponse(responseCode = "404", description = "找不到處理法", content = @Content),
    })
    @Operation(summary = "更新處理法", description = "更新指定處理法資料。")
    @PutMapping
    public ResponseEntity<ProcessMethodVo> update(
            @RequestBody ProcessMethodDto method) {

        ProcessMethodVo updated = service.update(method);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }

    // ===== GET (pageable) =====
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功取得處理法列表"),
    })
    @Operation(summary = "取得處理法列表", description = "根據頁碼以及顯示筆數取得多筆處理法資料。")
    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<ProcessMethodVo>> findAll(
            @PathVariable int page,
            @PathVariable int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<ProcessMethodVo> result = service.findAll(pageable);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/options")
    public ResponseEntity<List<ProcessMethodVo>> getCountryOptions() {
        return ResponseEntity.ok(service.findAllList());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProcessMethod>> search(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String engName
    ) {
        return ResponseEntity.ok(service.search(name, engName));
    }
}
