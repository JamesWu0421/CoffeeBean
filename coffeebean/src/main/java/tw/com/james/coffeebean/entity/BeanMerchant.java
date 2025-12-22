package tw.com.james.coffeebean.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bean_merchant")
public class BeanMerchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "merchant_name", length = 50)
    private String merchantName;

    // ===== getter / setter =====

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}
