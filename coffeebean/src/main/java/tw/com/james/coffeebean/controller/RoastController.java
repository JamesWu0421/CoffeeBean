package tw.com.james.coffeebean.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tw.com.james.coffeebean.service.RoastService;

@RestController
@RequestMapping("/api/roast")
public class RoastController {

    private final RoastService roastService;

    public RoastController(RoastService roastService) {
        this.roastService = roastService;
    }

    @PostMapping("/import")
    public ResponseEntity<String> importExcel(
            @RequestParam("file") MultipartFile file) {

        int count = roastService.importExcel(file);
        return ResponseEntity.ok("成功匯入 " + count + " 筆");
    }
}
