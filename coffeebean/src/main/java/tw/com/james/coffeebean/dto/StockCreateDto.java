package tw.com.james.coffeebean.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class StockCreateDto {
    private Integer coffeeBeanId;
    private Integer stockG;
    private LocalDate purchaseDate;
    private BigDecimal purchasePrice;
    private BigDecimal sellingPrice;
    private String storageInfo;
}