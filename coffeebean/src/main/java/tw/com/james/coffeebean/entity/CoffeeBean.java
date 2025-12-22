package tw.com.james.coffeebean.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "coffee_bean")
public class CoffeeBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @ManyToOne
    @JoinColumn(name = "process_method_id", nullable = false)
    private ProcessMethod processMethod;

    @ManyToOne
    @JoinColumn(name = "bean_merchant_id", nullable = false)
    private BeanMerchant beanMerchant;

    @Column(length = 100)
    private String region;

    @Column(name = "processing_plant", length = 100)
    private String processingPlant;

    @Column(name = "bean_variety", length = 100)
    private String beanVariety;

    @Column(name = "production_year")
    private Integer productionYear;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "coffeeBean")
    private List<Stock> stocks = new ArrayList<>();

    @OneToMany(mappedBy = "coffeeBean")
    private List<Roast> roasts = new ArrayList<>();

    // ===== getter / setter =====

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public ProcessMethod getProcessMethod() {
        return processMethod;
    }

    public void setProcessMethod(ProcessMethod processMethod) {
        this.processMethod = processMethod;
    }

    public BeanMerchant getBeanMerchant() {
        return beanMerchant;
    }

    public void setBeanMerchant(BeanMerchant beanMerchant) {
        this.beanMerchant = beanMerchant;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getProcessingPlant() {
        return processingPlant;
    }

    public void setProcessingPlant(String processingPlant) {
        this.processingPlant = processingPlant;
    }

    public String getBeanVariety() {
        return beanVariety;
    }

    public void setBeanVariety(String beanVariety) {
        this.beanVariety = beanVariety;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public List<Roast> getRoasts() {
        return roasts;
    }

    public void setRoasts(List<Roast> roasts) {
        this.roasts = roasts;
    }
}
