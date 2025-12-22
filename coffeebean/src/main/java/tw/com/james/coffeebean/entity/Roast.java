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


    @ManyToOne
    @JoinColumn(name = "coffee_bean_id") 
    private CoffeeBean coffeeBean;

    @Column(name = "batch_no", length = 50)
    private String batchNo;

    @Column(name = "green_bean_weight", precision = 6, scale = 1)
    private BigDecimal greenBeanWeight;

    private Integer rating;

    @Column(name = "charge_temp")
    private Integer chargeTemp;

    @Column(name = "initial_heat")
    private Integer initialHeat;

    private Integer airflow;

    @Column(name = "drum_rpm")
    private Integer drumRpm;

    @Column(name = "turning_point_time", length = 50)
    private String turningPointTime;

    @Column(name = "turning_point_temp", precision = 5, scale = 1)
    private BigDecimal turningPointTemp;

    @Column(name = "first_crack_time", length = 50)
    private String firstCrackTime;

    @Column(name = "first_crack_temp", precision = 5, scale = 1)
    private BigDecimal firstCrackTemp;

    @Column(name = "drop_time", length = 50)
    private String dropTime;

    @Column(name = "drop_temp", precision = 5, scale = 1)
    private BigDecimal dropTemp;

    @Column(name = "roast_date")
    private LocalDate roastDate;

    @Column(name = "roasted_bean_weight", precision = 6, scale = 1)
    private BigDecimal roastedBeanWeight;

    @Column(name = "bean_surface", precision = 5, scale = 1)
    private BigDecimal beanSurface;

    @Column(name = "bean_powder", precision = 5, scale = 1)
    private BigDecimal beanPowder;

    @Column(name = "roast_level", length = 20)
    private String roastLevel;

    @Column(name = "drop_point", length = 50)
    private String dropPoint;

    // ===== getter / setter =====

    public Integer getRoastId() {
        return roastId;
    }

    public void setRoastId(Integer roastId) {
        this.roastId = roastId;
    }

    public CoffeeBean getCoffeeBean() {
        return coffeeBean;
    }

    public void setCoffeeBean(CoffeeBean coffeeBean) {
        this.coffeeBean = coffeeBean;
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
}
