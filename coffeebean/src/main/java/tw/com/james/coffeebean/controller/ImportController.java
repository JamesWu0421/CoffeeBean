package tw.com.james.coffeebean.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tw.com.james.coffeebean.service.ImportService;

@RestController
@RequestMapping("/api/roast")
public class ImportController {

    private final ImportService importService;

    public ImportController(ImportService roastService) {
        this.importService = roastService;
    }

    @PostMapping("/import")
    public ResponseEntity<String> importExcel(
            @RequestParam("file") MultipartFile file) {

        int roastCount = importService.importExcel(file);
        importService.importSheet7(file);
        return ResponseEntity.ok("匯入完成：Roast " + roastCount + " 筆，Country / ProcessMethod 已同步更新");
    }
}
