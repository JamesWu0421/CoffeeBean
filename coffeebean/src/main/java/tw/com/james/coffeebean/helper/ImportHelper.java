package tw.com.james.coffeebean.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tw.com.james.coffeebean.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class ImportHelper {

    @Autowired
    private CountryRepository countryRepo;
    @Autowired
    private ProcessMethodRepository processRepo;
    @Autowired
    private BeanMerchantRepository merchantRepo;
    @Autowired
    private CoffeeBeanRepository coffeeBeanRepo;
    @Autowired
    private StockRepository stockRepo;

    public Integer createCoffeeBean(
            Integer year,
            String region,
            String plant,
            String variety,
            String countryName,
            String processName,
            String merchantName
    ) {
        Integer countryId  = countryRepo.findIdByName(countryName);
        Integer processId  = processRepo.findIdByName(processName);
        Integer merchantId = merchantRepo.findIdByName(merchantName);

        if (countryId == null || processId == null || merchantId == null) {
            throw new IllegalStateException(
                "FK 不存在 country=" + countryName +
                ", process=" + processName +
                ", merchant=" + merchantName
            );
        }

        return coffeeBeanRepo.insert(
            countryId,
            processId,
            merchantId,
            region,
            plant,
            variety,
            year
        );
    }

    public void createStock(
            Integer coffeeBeanId,
            Integer stockG,
            BigDecimal purchasePrice,
            BigDecimal sellingPrice,
            LocalDate purchaseDate
    ) {
        stockRepo.insert(
            coffeeBeanId,
            stockG,
            purchasePrice,
            sellingPrice,
            purchaseDate
        );
    }
}
