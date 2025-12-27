package tw.com.james.coffeebean.dto;

import lombok.Data;

@Data
public class GreenBeanUpdateDto {
    private Integer id;
    private Integer countryId;
    private Integer processMethodId;
    private Integer beanMerchantId;
    private String region;
    private String processingPlant;
    private String beanVariety;
    private Integer productionYear;
}