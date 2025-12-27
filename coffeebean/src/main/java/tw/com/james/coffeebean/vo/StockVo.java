package tw.com.james.coffeebean.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StockVo {
    private Integer id;
    private Integer coffeeBeanId;
    private String coffeeBeanName;
    private Integer stockG;
    private LocalDate purchaseDate;
    private BigDecimal purchasePrice;
    private BigDecimal sellingPrice;
    private String storageInfo;
    private LocalDateTime createdAt;
}