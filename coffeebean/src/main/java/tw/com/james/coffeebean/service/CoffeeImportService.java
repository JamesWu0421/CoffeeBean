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
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static tw.com.james.coffeebean.helper.ExcelCellHelper.*;

@Service
public class CoffeeImportService {

    private static final Logger log =
            LogManager.getLogger(CoffeeImportService.class);

    @Autowired
    private CoffeeBeanRepository coffeeBeanRepo;

    @Autowired
    private ImportHelper importHelper;

    /**
     * Sheet8：coffee_bean + stock
     */
    @Transactional
    public void importSheet8(MultipartFile file) {

    
        Set<Integer> stockHandledBeanIds = new HashSet<>();

        try (InputStream is = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(is)) {

            FormulaEvaluator evaluator =
                    workbook.getCreationHelper().createFormulaEvaluator();

            Sheet sheet = workbook.getSheetAt(8);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row == null || isRowEmpty(row)) continue;

                try {
                    importRow(row, i + 1, evaluator, stockHandledBeanIds);
                } catch (Exception e) {
                    log.error(
                        "Sheet8 第 {} 列匯入失敗：{}",
                        i + 1,
                        e.getMessage(),
                        e
                    );
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Sheet8 匯入失敗", e);
        }
    }

    private void importRow(
            Row row,
            int rowNum,
            FormulaEvaluator evaluator,
            Set<Integer> stockHandledBeanIds
    ) {

        // ===== unique key =====
        Integer year   = getInt(row, 1, evaluator);   
        String region  = getString(row, 9, evaluator);
        String plant   = getString(row, 10, evaluator);

        if (year == null || region == null || plant == null) {
            log.warn("第 {} 列 unique key 不完整", rowNum);
            return;
        }

        // ===== coffee_bean =====
        Integer coffeeBeanId =
                coffeeBeanRepo.findIdByUnique(year, region, plant);

        boolean isNewCoffeeBean = false;

        if (coffeeBeanId == null) {
            coffeeBeanId = importHelper.createCoffeeBean(
                    year,
                    region,
                    plant,
                    getString(row, 11, evaluator), // variety
                    getString(row, 7, evaluator),  // country
                    getString(row, 8, evaluator),  // process
                    getString(row, 12, evaluator)  // merchant
            );
            isNewCoffeeBean = true;

            log.info("第 {} 列 新增 coffee_bean id={}", rowNum, coffeeBeanId);
        }


        if (stockHandledBeanIds.contains(coffeeBeanId)) {
            log.info(
                "第 {} 列 coffee_bean id={} stock 本次已處理，略過",
                rowNum,
                coffeeBeanId
            );
            return;
        }

        Integer stockG = getInt(row, 15, evaluator);
        if (stockG == null || stockG < 0) {
            log.warn("第 {} 列 stock 無效", rowNum);
            return;
        }

        if (isNewCoffeeBean) {
            importHelper.createStock(
                    coffeeBeanId,
                    stockG,
                    getDecimal(row, 13, evaluator),
                    getDecimal(row, 14, evaluator),
                    LocalDate.now()
            );
            log.info("第 {} 列 新增 stock（beanId={}）", rowNum, coffeeBeanId);

        } else {
            importHelper.updateStock(
                    coffeeBeanId,
                    stockG,
                    getDecimal(row, 13, evaluator),
                    getDecimal(row, 14, evaluator)
            );
            log.info("第 {} 列 更新 stock（beanId={}）", rowNum, coffeeBeanId);
        }

        
        stockHandledBeanIds.add(coffeeBeanId);
    }
}
