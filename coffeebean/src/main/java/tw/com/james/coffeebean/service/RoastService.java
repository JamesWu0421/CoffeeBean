package tw.com.james.coffeebean.service;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tw.com.james.coffeebean.entity.Roast;
import tw.com.james.coffeebean.repository.RoastRepository;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RoastService {

    private final RoastRepository roastRepository;

    public RoastService(RoastRepository roastRepository) {
        this.roastRepository = roastRepository;
    }

    public int importExcel(MultipartFile file) {

        List<Roast> list = new ArrayList<>();

        try (InputStream is = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(is)) {

            FormulaEvaluator evaluator =
            workbook.getCreationHelper().createFormulaEvaluator();

            

            Sheet sheet = workbook.getSheetAt(0);

            
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row == null || isRowEmpty(row)) continue;

                Roast roast = new Roast();

                roast.setBatchNo(getString(row, 0, evaluator));
                roast.setGreenBeanWeight(getDecimal(row, 1, evaluator));
                roast.setRating(getInt(row, 2, evaluator));

                roast.setGreenBeanCode(getInt(row, 3, evaluator));
                roast.setCountry(getString(row, 4, evaluator));
                roast.setCountryEng(getString(row, 5, evaluator));
                roast.setBeanCode(getString(row, 6, evaluator));

                roast.setProcessMethod(getString(row, 7, evaluator));

                roast.setChargeTemp(getInt(row, 8, evaluator));
                roast.setInitialHeat(getInt(row, 9, evaluator));
                roast.setAirflow(getInt(row, 10, evaluator));
                roast.setDrumRpm(getInt(row, 11, evaluator));

                roast.setTurningPointTime(getString(row, 12, evaluator));
                roast.setTurningPointTemp(getDecimal(row, 13, evaluator));
                roast.setdehydrationRor(getDecimal(row, 14, evaluator));

                roast.setFirstCrackTime(getString(row, 15, evaluator));
                roast.setFirstCrackTemp(getDecimal(row, 16, evaluator));

                roast.setDropTime(getString(row, 17, evaluator));
                roast.setDropTemp(getDecimal(row, 18, evaluator));

                roast.setDevelopmentTempRise(getDecimal(row, 19, evaluator));
                roast.setDevelopmentSeconds(getInt(row, 20, evaluator));
                roast.setDevelopmentRor(getDecimal(row, 21, evaluator));
                roast.setDevelopmentRatio(getDecimal(row, 22, evaluator));

                roast.setRoastDate(getDate(row, 23));

                roast.setRoastedBeanWeight(getDecimal(row, 24, evaluator));
                roast.setStockG(getInt(row, 25, evaluator));
                roast.setOrderG(getInt(row, 26, evaluator));
                roast.setWeightLoss(getDecimal(row, 27, evaluator));

                roast.setBeanSurface(getDecimal(row, 28, evaluator));
                roast.setBeanPowder(getDecimal(row, 29, evaluator));

                roast.setRd(getInt(row, 30, evaluator));
                roast.setScaaScore(getString(row, 31, evaluator));
                roast.setRoastLevel(getString(row, 32, evaluator));
                roast.setDropPoint(getString(row, 33, evaluator));

                roast.setOriginFactory(getString(row, 34, evaluator));
                roast.setRemarks(getString(row, 35, evaluator));
                roast.setSourceFile(file.getOriginalFilename());

                list.add(roast);
            }

            roastRepository.saveAll(list);
            return list.size();

        } catch (Exception e) {
            throw new RuntimeException("Excel 匯入失敗", e);
        }
    }


    private boolean isRowEmpty(Row row) {
        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    private String getString(Row row, int index, FormulaEvaluator evaluator) {
        Cell cell = row.getCell(index);
        if (cell == null) return null;

        String result;

        switch (cell.getCellType()) {

            case STRING:
                result = cell.getStringCellValue();
                break;

            case NUMERIC:
                double d = cell.getNumericCellValue();
                if (d == Math.floor(d)) {
                    result = String.valueOf((long) d);
                } else {
                    result = String.valueOf(d);
                }
                break;

            

            case FORMULA:
                switch (cell.getCachedFormulaResultType()) {

                    case STRING:
                        result = cell.getStringCellValue();
                        break;

                    case NUMERIC:
                        double fd = cell.getNumericCellValue();
                        if (fd == Math.floor(fd)) {
                            result = String.valueOf((long) fd);
                        } else {
                            result = String.valueOf(fd);
                        }
                        break;


                    default:
                        result = null;
                        break;
                }
                break;

            default:
                result = null;
                break;
        }

        return result;
    }


    private Integer getInt(Row row, int index, FormulaEvaluator evaluator) {
        String val = getString(row, index, evaluator);
        if (val == null || val.isBlank()) return null;
        return Integer.parseInt(val.split("\\.")[0]);
    }

    private BigDecimal getDecimal(Row row, int index, FormulaEvaluator evaluator) {
        String val = getString(row, index, evaluator);
        if (val == null || val.isBlank()) return null;
        return new BigDecimal(val);
    }


    private LocalDate getDate(Row row, int index) {
        Cell cell = row.getCell(index);
        if (cell == null) return null;

        if (cell.getCellType() == CellType.NUMERIC &&
            DateUtil.isCellDateFormatted(cell)) {
            return cell.getLocalDateTimeCellValue().toLocalDate();
        }
        return null;
    }
}
