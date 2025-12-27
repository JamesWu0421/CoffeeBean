package tw.com.james.coffeebean.vo;

import lombok.Data;

@Data
public class GreenBeanVo {
    private Integer id;
    
    
    private String country;
    private String processMethod;
    private String beanMerchant;
    
    private String region;
    private String processingPlant;
    private String beanVariety;
    private Integer productionYear;
}