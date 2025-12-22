package tw.com.james.coffeebean.helper;

import java.math.BigDecimal;
import java.time.LocalDate;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

public class ExcelCellHelper {
    private ExcelCellHelper() {}

    public static boolean isRowEmpty(Row row) {
        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    public static String getString(Row row, int index, FormulaEvaluator evaluator) {
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


    public static Integer getInt(Row row, int index, FormulaEvaluator evaluator) {
        String val = getString(row, index, evaluator);
        if (val == null || val.isBlank()) return null;
        return Integer.parseInt(val.split("\\.")[0]);
    }

    public static BigDecimal getDecimal(Row row, int index, FormulaEvaluator evaluator) {
        String val = getString(row, index, evaluator);
        if (val == null || val.isBlank()) return null;
        return new BigDecimal(val);
    }


    public static LocalDate getDate(Row row, int index) {
        Cell cell = row.getCell(index);
        if (cell == null) return null;

        if (cell.getCellType() == CellType.NUMERIC &&
            DateUtil.isCellDateFormatted(cell)) {
            return cell.getLocalDateTimeCellValue().toLocalDate();
        }
        return null;
    }
}
