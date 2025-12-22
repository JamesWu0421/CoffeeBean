package tw.com.james.coffeebean.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "roast")
public class Roast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roast_id")
    private Integer roastId;

    @Column(name = "batch_no", length = 50)
    private String batchNo;

    @Column(name = "green_bean_weight", precision = 6, scale = 1)
    private BigDecimal greenBeanWeight;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "green_bean_code")
    private Integer greenBeanCode;

    @Column(name = "country", length = 50)
    private String country;

    @Column(name = "country_eng", length = 50)
    private String countryEng;

    @Column(name = "bean_code", length = 50)
    private String beanCode;

    @Column(name = "process_method", length = 50)
    private String processMethod;

    @Column(name = "charge_temp")
    private Integer chargeTemp;

    @Column(name = "initial_heat")
    private Integer initialHeat;

    @Column(name = "airflow")
    private Integer airflow;

    @Column(name = "drum_rpm")
    private Integer drumRpm;

    @Column(name = "turning_point_time", length = 50)
    private String turningPointTime;

    @Column(name = "turning_point_temp", precision = 5, scale = 1)
    private BigDecimal turningPointTemp;

    @Column(name = "dehydration_ror", precision = 5, scale = 1)
    private BigDecimal dehydrationRor;

    @Column(name = "first_crack_time", length = 50)
    private String firstCrackTime;

    @Column(name = "first_crack_temp", precision = 5, scale = 1)
    private BigDecimal firstCrackTemp;

    @Column(name = "drop_time", length = 50)
    private String dropTime;

    @Column(name = "drop_temp", precision = 5, scale = 1)
    private BigDecimal dropTemp;

    @Column(name = "development_temp_rise", precision = 5, scale = 1)
    private BigDecimal developmentTempRise;

    @Column(name = "development_seconds")
    private Integer developmentSeconds;

    @Column(name = "development_ror", precision = 5, scale = 1)
    private BigDecimal developmentRor;

    @Column(name = "development_ratio", precision = 6, scale = 2)
    private BigDecimal developmentRatio;

    @Column(name = "roast_date")
    private LocalDate roastDate;

    @Column(name = "roasted_bean_weight", precision = 6, scale = 1)
    private BigDecimal roastedBeanWeight;

    @Column(name = "stock_g")
    private Integer stockG;

    @Column(name = "order_g")
    private Integer orderG;

    @Column(name = "weight_loss", precision = 5, scale = 2)
    private BigDecimal weightLoss;

    @Column(name = "bean_surface", precision = 5, scale = 1)
    private BigDecimal beanSurface;

    @Column(name = "bean_powder", precision = 5, scale = 1)
    private BigDecimal beanPowder;

    @Column(name = "rd")
    private Integer rd;

    @Column(name = "scaa_score", length = 50)
    private String scaaScore;

    @Column(name = "roast_level", length = 20)
    private String roastLevel;

    @Column(name = "drop_point", length = 50)
    private String dropPoint;

    @Column(name = "origin_factory", length = 100)
    private String originFactory;

    @Column(name = "remarks", length = 255)
    private String remarks;

    @Column(name = "source_file", length = 255)
    private String sourceFile;

    // ======================
    // Getter / Setter
    // ======================

    public Integer getRoastId() {
        return roastId;
    }

    public void setRoastId(Integer roastId) {
        this.roastId = roastId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public BigDecimal getGreenBeanWeight() {
        return greenBeanWeight;
    }

    public void setGreenBeanWeight(BigDecimal greenBeanWeight) {
        this.greenBeanWeight = greenBeanWeight;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getGreenBeanCode() {
        return greenBeanCode;
    }

    public void setGreenBeanCode(Integer greenBeanCode) {
        this.greenBeanCode = greenBeanCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryEng() {
        return countryEng;
    }

    public void setCountryEng(String countryEng) {
        this.countryEng = countryEng;
    }

    public String getBeanCode() {
        return beanCode;
    }

    public void setBeanCode(String beanCode) {
        this.beanCode = beanCode;
    }

    public String getProcessMethod() {
        return processMethod;
    }

    public void setProcessMethod(String processMethod) {
        this.processMethod = processMethod;
    }

    public Integer getChargeTemp() {
        return chargeTemp;
    }

    public void setChargeTemp(Integer chargeTemp) {
        this.chargeTemp = chargeTemp;
    }

    public Integer getInitialHeat() {
        return initialHeat;
    }

    public void setInitialHeat(Integer initialHeat) {
        this.initialHeat = initialHeat;
    }

    public Integer getAirflow() {
        return airflow;
    }

    public void setAirflow(Integer airflow) {
        this.airflow = airflow;
    }

    public Integer getDrumRpm() {
        return drumRpm;
    }

    public void setDrumRpm(Integer drumRpm) {
        this.drumRpm = drumRpm;
    }

    public String getTurningPointTime() {
        return turningPointTime;
    }

    public void setTurningPointTime(String turningPointTime) {
        this.turningPointTime = turningPointTime;
    }

    public BigDecimal getTurningPointTemp() {
        return turningPointTemp;
    }

    public void setTurningPointTemp(BigDecimal turningPointTemp) {
        this.turningPointTemp = turningPointTemp;
    }

    public BigDecimal getdehydrationRor() {
        return dehydrationRor;
    }

    public void setdehydrationRor(BigDecimal dehydrationRor) {
        this.dehydrationRor = dehydrationRor;
    }

    public String getFirstCrackTime() {
        return firstCrackTime;
    }

    public void setFirstCrackTime(String firstCrackTime) {
        this.firstCrackTime = firstCrackTime;
    }

    public BigDecimal getFirstCrackTemp() {
        return firstCrackTemp;
    }

    public void setFirstCrackTemp(BigDecimal firstCrackTemp) {
        this.firstCrackTemp = firstCrackTemp;
    }

    public String getDropTime() {
        return dropTime;
    }

    public void setDropTime(String dropTime) {
        this.dropTime = dropTime;
    }

    public BigDecimal getDropTemp() {
        return dropTemp;
    }

    public void setDropTemp(BigDecimal dropTemp) {
        this.dropTemp = dropTemp;
    }

    public BigDecimal getDevelopmentTempRise() {
        return developmentTempRise;
    }

    public void setDevelopmentTempRise(BigDecimal developmentTempRise) {
        this.developmentTempRise = developmentTempRise;
    }

    public Integer getDevelopmentSeconds() {
        return developmentSeconds;
    }

    public void setDevelopmentSeconds(Integer developmentSeconds) {
        this.developmentSeconds = developmentSeconds;
    }

    public BigDecimal getDevelopmentRor() {
        return developmentRor;
    }

    public void setDevelopmentRor(BigDecimal developmentRor) {
        this.developmentRor = developmentRor;
    }

    public BigDecimal getDevelopmentRatio() {
        return developmentRatio;
    }

    public void setDevelopmentRatio(BigDecimal developmentRatio) {
        this.developmentRatio = developmentRatio;
    }

    public LocalDate getRoastDate() {
        return roastDate;
    }

    public void setRoastDate(LocalDate roastDate) {
        this.roastDate = roastDate;
    }

    public BigDecimal getRoastedBeanWeight() {
        return roastedBeanWeight;
    }

    public void setRoastedBeanWeight(BigDecimal roastedBeanWeight) {
        this.roastedBeanWeight = roastedBeanWeight;
    }

    public Integer getStockG() {
        return stockG;
    }

    public void setStockG(Integer stockG) {
        this.stockG = stockG;
    }

    public Integer getOrderG() {
        return orderG;
    }

    public void setOrderG(Integer orderG) {
        this.orderG = orderG;
    }

    public BigDecimal getWeightLoss() {
        return weightLoss;
    }

    public void setWeightLoss(BigDecimal weightLoss) {
        this.weightLoss = weightLoss;
    }

    public BigDecimal getBeanSurface() {
        return beanSurface;
    }

    public void setBeanSurface(BigDecimal beanSurface) {
        this.beanSurface = beanSurface;
    }

    public BigDecimal getBeanPowder() {
        return beanPowder;
    }

    public void setBeanPowder(BigDecimal beanPowder) {
        this.beanPowder = beanPowder;
    }

    public Integer getRd() {
        return rd;
    }

    public void setRd(Integer rd) {
        this.rd = rd;
    }

    public String getScaaScore() {
        return scaaScore;
    }

    public void setScaaScore(String scaaScore) {
        this.scaaScore = scaaScore;
    }

    public String getRoastLevel() {
        return roastLevel;
    }

    public void setRoastLevel(String roastLevel) {
        this.roastLevel = roastLevel;
    }

    public String getDropPoint() {
        return dropPoint;
    }

    public void setDropPoint(String dropPoint) {
        this.dropPoint = dropPoint;
    }

    public String getOriginFactory() {
        return originFactory;
    }

    public void setOriginFactory(String originFactory) {
        this.originFactory = originFactory;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }
}
