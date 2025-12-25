package tw.com.james.coffeebean.dto;

public class CountryDto {
    private Integer id;
    private String countryName;
    private String countryEngName;
    private String countryCode;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryEngName() {
        return countryEngName;
    }

    public void setCountryEngName(String countryEngName) {
        this.countryEngName = countryEngName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
