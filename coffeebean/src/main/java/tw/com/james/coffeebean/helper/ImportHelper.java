package tw.com.james.coffeebean.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tw.com.james.coffeebean.entity.*;
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

    /**
     * 建立 CoffeeBean（純 JPA 關聯寫法）
     *
     * @return 新建立的 coffeeBean.id
     */
    public Integer createCoffeeBean(
            Integer year,
            String region,
            String plant,
            String variety,
            String countryName,
            String processName,
            String merchantName
    ) {

        // ===== 1. FK name -> id =====
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

        // ===== 2. id -> Entity（⭐ JPA 核心）=====
        Country country = countryRepo.findById(countryId);
        ProcessMethod processMethod = processRepo.findById(processId);
        BeanMerchant merchant = merchantRepo.findById(merchantId);

        // ===== 3. 組 CoffeeBean Entity =====
        CoffeeBean bean = new CoffeeBean();
        bean.setCountry(country);
        bean.setProcessMethod(processMethod);
        bean.setBeanMerchant(merchant);
        bean.setRegion(region);
        bean.setProcessingPlant(plant);
        bean.setBeanVariety(variety);
        bean.setProductionYear(year);

        // createdAt 若 DB 有 default，可以不設
        // bean.setCreatedAt(LocalDateTime.now());

        // ===== 4. 存檔 =====
        coffeeBeanRepo.save(bean);

        // ⭐ persist 後 id 自動回填
        return bean.getId();
    }

    /**
     * 建立 stock（coffeeBean 為 unique key）
     */
    public void createStock(
            CoffeeBean coffeeBean,
            Integer stockG,
            BigDecimal purchasePrice,
            BigDecimal sellingPrice,
            LocalDate purchaseDate
    ) {
        Stock stock = new Stock();
        stock.setCoffeeBean(coffeeBean);
        stock.setStockG(stockG);
        stock.setPurchasePrice(purchasePrice);
        stock.setSellingPrice(sellingPrice);
        stock.setPurchaseDate(purchaseDate);

        stockRepo.save(stock);
    }


    public void upsertStock(
            CoffeeBean coffeeBean,
            Integer stockG,
            BigDecimal purchasePrice,
            BigDecimal sellingPrice,
            LocalDate purchaseDate
    ) {
        Stock stock = stockRepo.findByCoffeeBean(coffeeBean);

        if (stock == null) {
            stock = new Stock();
            stock.setCoffeeBean(coffeeBean);
        }

        stock.setStockG(stockG);
        stock.setPurchasePrice(purchasePrice);
        stock.setSellingPrice(sellingPrice);

        // purchaseDate 通常只在首次設定，有需要可判斷
        if (stock.getPurchaseDate() == null) {
            stock.setPurchaseDate(purchaseDate);
        }

        stockRepo.save(stock);
    }
}
