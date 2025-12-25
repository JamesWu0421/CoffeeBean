package tw.com.james.coffeebean.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tw.com.james.coffeebean.entity.CoffeeBean;
import tw.com.james.coffeebean.entity.Stock;
import tw.com.james.coffeebean.helper.ImportHelper;
import tw.com.james.coffeebean.repository.CoffeeBeanRepository;
import tw.com.james.coffeebean.repository.StockRepository;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import static tw.com.james.coffeebean.helper.ExcelCellHelper.*;

@Service
public class CoffeeImportService {

    private static final Logger log =
            LogManager.getLogger(CoffeeImportService.class);

    @Autowired
    private CoffeeBeanRepository coffeeBeanRepo;

    @Autowired
    private StockRepository stockRepo;

    @Autowired
    private ImportHelper importHelper;

    /**
     * Sheet8：coffee_bean → stock（嚴格兩階段）
     */
    @Transactional
    public void importSheet8(MultipartFile file) {

        // coffeeBeanId -> StockImportData（保留順序）
        Map<CoffeeBean, StockImportData> stockImportMap = new LinkedHashMap<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(is)) {

            FormulaEvaluator evaluator =
                    workbook.getCreationHelper().createFormulaEvaluator();

            Sheet sheet = workbook.getSheetAt(8);

            // =========================
            // Phase 1：coffee_bean
            // =========================
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row == null || isRowEmpty(row)) continue;

                int rowNum = i + 1;

                Integer year  = getInt(row, 1, evaluator);
                String region = getString(row, 9, evaluator);
                String plant  = getString(row, 10, evaluator);

                if (year == null || region == null || plant == null) {
                    log.warn("第 {} 列 coffee_bean unique key 不完整，略過", rowNum);
                    continue;
                }

                CoffeeBean coffeeBean =
                        coffeeBeanRepo.findByUnique(year, region, plant);

                if (coffeeBean != null) {
                    log.info(
                        "第 {} 列 coffee_bean 重複（id={}，year={}，region={}，plant={}）",
                        rowNum,
                        coffeeBean.getId(),
                        year,
                        region,
                        plant
                    );
                } else {
                    Integer newId = importHelper.createCoffeeBean(
                            year,
                            region,
                            plant,
                            getString(row, 11, evaluator), // variety
                            getString(row, 7, evaluator),  // country
                            getString(row, 8, evaluator),  // process
                            getString(row, 12, evaluator)  // merchant
                    );

                    coffeeBean =
                            coffeeBeanRepo.findById(newId);

                    log.info("第 {} 列 新增 coffee_bean id={}", rowNum, newId);
                }

                // 收集 stock（不在 Phase 1 寫 DB）
                StockImportData stockData =
                        readStockData(row, rowNum, evaluator);

                if (stockData != null) {
                    stockImportMap.put(coffeeBean, stockData);
                }
            }

            // =========================
            // Phase 2：stock
            // =========================
            for (Map.Entry<CoffeeBean, StockImportData> entry : stockImportMap.entrySet()) {

                CoffeeBean coffeeBean = entry.getKey();
                StockImportData incoming = entry.getValue();

                Stock existing = stockRepo.findByCoffeeBean(coffeeBean);

                if (existing == null) {
                    importHelper.createStock(
                            coffeeBean,
                            incoming.stockG(),
                            incoming.purchasePrice(),
                            incoming.sellingPrice(),
                            incoming.purchaseDate()
                    );

                    log.info(
                        "新增 stock（coffeeBeanId={}，stockG={}）",
                        coffeeBean.getId(),
                        incoming.stockG()
                    );

                } else {
                    log.info(
                        "stock 重複（coffeeBeanId={}）｜原本 stockG={} purchase={} selling={}",
                        coffeeBean.getId(),
                        existing.getStockG(),
                        existing.getPurchasePrice(),
                        existing.getSellingPrice()
                    );

                    log.info(
                        "stock 匯入資料（coffeeBeanId={}）｜新 stockG={} purchase={} selling={}",
                        coffeeBean.getId(),
                        incoming.stockG(),
                        incoming.purchasePrice(),
                        incoming.sellingPrice()
                    );

                    importHelper.upsertStock(
                            coffeeBean,
                            incoming.stockG(),
                            incoming.purchasePrice(),
                            incoming.sellingPrice(),
                            incoming.purchaseDate()
                    );
                }
            }

            log.info(
                "Sheet8 匯入完成：coffeeBeanRows={}，stockUpsertCount={}",
                sheet.getLastRowNum(),
                stockImportMap.size()
            );

        } catch (Exception e) {
            throw new RuntimeException("Sheet8 匯入失敗", e);
        }
    }

    // =========================
    // 私有工具
    // =========================

    private StockImportData readStockData(
            Row row,
            int rowNum,
            FormulaEvaluator evaluator
    ) {
        Integer stockG = getInt(row, 15, evaluator);
        if (stockG == null || stockG < 0) {
            log.warn("第 {} 列 stock 無效，略過", rowNum);
            return null;
        }

        BigDecimal purchasePrice = getDecimal(row, 13, evaluator);
        BigDecimal sellingPrice  = getDecimal(row, 14, evaluator);

        return new StockImportData(
                stockG,
                purchasePrice,
                sellingPrice,
                LocalDate.now()
        );
    }

    /**
     * 匯入暫存用 DTO
     */
    private record StockImportData(
            Integer stockG,
            BigDecimal purchasePrice,
            BigDecimal sellingPrice,
            LocalDate purchaseDate
    ) {}
}
