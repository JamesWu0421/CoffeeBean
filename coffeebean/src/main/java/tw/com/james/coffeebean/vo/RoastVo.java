package tw.com.james.coffeebean.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RoastVo {
    private Integer roastId;
    private Integer coffeeBeanId;
    private String coffeeBeanName; 
    private String batchNo;
    private BigDecimal greenBeanWeight;
    private Integer rating;
    private Integer chargeTemp;
    private Integer initialHeat;
    private Integer airflow;
    private Integer drumRpm;
    private String turningPointTime;
    private BigDecimal turningPointTemp;
    private String firstCrackTime;
    private BigDecimal firstCrackTemp;
    private String dropTime;
    private BigDecimal dropTemp;
    private LocalDate roastDate;
    private BigDecimal roastedBeanWeight;
    private BigDecimal beanSurface;
    private BigDecimal beanPowder;
    private String roastLevel;
    private String dropPoint;
}