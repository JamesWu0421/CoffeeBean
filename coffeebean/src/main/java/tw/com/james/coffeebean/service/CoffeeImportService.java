package tw.com.james.coffeebean.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tw.com.james.coffeebean.helper.ImportHelper;
import tw.com.james.coffeebean.repository.CoffeeBeanRepository;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;

import static tw.com.james.coffeebean.helper.ExcelCellHelper.*;

@Service
public class CoffeeImportService {

    private static final Logger log =
            LogManager.getLogger(CoffeeImportService.class);

    @Autowired
    private CoffeeBeanRepository coffeeBeanRepo;

    @Autowired
    private ImportHelper importHelper;

    @Transactional
    public void importSheet8(MultipartFile file) {

        try (InputStream is = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(is)) {

            FormulaEvaluator evaluator =
                    workbook.getCreationHelper().createFormulaEvaluator();

            Sheet sheet = workbook.getSheetAt(8);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row == null || isRowEmpty(row)) continue;

                try {
                    importRow(row, i + 1, evaluator);
                } catch (Exception e) {
                    log.error("Sheet8 第 {} 列匯入失敗：{}",
                            i + 1, e.getMessage());
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Sheet8 匯入失敗", e);
        }
    }

    private void importRow(Row row, int rowNum, FormulaEvaluator evaluator) {

        Integer year   = getInt(row, 1, evaluator);
        String region  = getString(row, 9, evaluator);
        String plant   = getString(row, 10, evaluator);

        if (year == null || region == null || plant == null) {
            log.warn("第 {} 列 unique 欄位不足", rowNum);
            return;
        }

        Integer coffeeBeanId =
                coffeeBeanRepo.findIdByUnique(year, region, plant);

        if (coffeeBeanId == null) {
            coffeeBeanId = importHelper.createCoffeeBean(
                year,
                region,
                plant,
                getString(row, 11, evaluator), // variety
                getString(row, 7, evaluator),  // country_name
                getString(row, 8, evaluator),  // process_method
                getString(row, 12, evaluator)  // merchant_name
            );
            log.info("第 {} 列 新增 coffee_bean id={}", rowNum, coffeeBeanId);
        } else {
            log.info("第 {} 列 coffee_bean 已存在 id={}", rowNum, coffeeBeanId);
        }

        Integer stockG = getInt(row, 15, evaluator);
        if (stockG == null || stockG < 0) {
            log.warn("第 {} 列 stock 無效", rowNum);
            return;
        }

        importHelper.createStock(
            coffeeBeanId,
            stockG,
            getDecimal(row, 13, evaluator),
            getDecimal(row, 14, evaluator),
            LocalDate.now()
        );

        log.info("第 {} 列 新增 stock 成功", rowNum);
    }
}
