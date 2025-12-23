package tw.com.james.coffeebean.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tw.com.james.coffeebean.service.ImportService;
import tw.com.james.coffeebean.service.CoffeeImportService;

@RestController
@RequestMapping("/api/roast")
public class ImportController {


    private final ImportService importService;
    private final CoffeeImportService coffeeImportService;

    public ImportController(
            ImportService importService,
            CoffeeImportService coffeeImportService
    ) {
        this.importService = importService;
        this.coffeeImportService = coffeeImportService;
    }

    @PostMapping("/import")
    public ResponseEntity<String> importExcel(
            @RequestParam("file") MultipartFile file) {

        int roastCount = importService.importExcel(file);
        importService.importSheet7(file);
        return ResponseEntity.ok("匯入完成：Roast " + roastCount + " 筆，Country / ProcessMethod 已同步更新");
    }

    @PostMapping("/import/coffeebean")
    public ResponseEntity<String> importCoffeeBean(
            @RequestParam("file") MultipartFile file) {

        coffeeImportService.importSheet8(file);

        return ResponseEntity.ok("CoffeeBean / Stock 匯入完成");
    }

}
