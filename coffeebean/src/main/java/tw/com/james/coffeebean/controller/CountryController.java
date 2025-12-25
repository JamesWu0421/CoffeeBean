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
import tw.com.james.coffeebean.dto.CountryDto;
import tw.com.james.coffeebean.service.CountryService;
import tw.com.james.coffeebean.vo.CountryVo;

@Tag(name = "Country Controller", description = "國家增刪改查相關的 API")
@RestController
@RequestMapping("/api/v1/country")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    // ===== POST =====
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功建立國家資料"),
            @ApiResponse(responseCode = "400", description = "國家資料不合理", content = @Content),
    })
    @Operation(summary = "建立國家", description = "建立一筆國家資料。")
    @PostMapping(
        produces = "application/json"
    )
    public ResponseEntity<CountryVo> create(
            @RequestBody CountryDto dto) {

        return ResponseEntity.ok(countryService.create(dto));
    }

    // ===== DELETE =====
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "成功刪除國家資料"),
            @ApiResponse(responseCode = "404", description = "找不到國家", content = @Content),
    })
    @Operation(summary = "刪除國家", description = "刪除指定 id 的國家資料。")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id) {

        countryService.delete(id);
        return ResponseEntity.noContent().build(); // ⭐ 204
    }

    // ===== PUT =====
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功更新國家資料"),
            @ApiResponse(responseCode = "400", description = "國家資料不合理", content = @Content),
            @ApiResponse(responseCode = "404", description = "找不到國家", content = @Content),
    })
    @Operation(summary = "更新國家", description = "更新指定國家資料。")
    @PutMapping
    public ResponseEntity<CountryVo> update(
            @RequestBody CountryDto dto) {

        CountryVo updated = countryService.update(dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }

    // ===== GET (pageable) =====
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功取得國家列表"),
    })
    @Operation(summary = "取得國家列表", description = "根據頁碼以及顯示筆數取得多筆國家資料。")
    @GetMapping("/{page}/{size}")
    public ResponseEntity<List<CountryVo>> findAll(
            @PathVariable int page,
            @PathVariable int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<CountryVo> result = countryService.findAll(pageable);

        return ResponseEntity.ok(result.getContent());
    }
}
