package tw.com.james.coffeebean.dto;

public class ProcessMethodDto {

    private Integer id;
    private String processMethod;
    private String processMethodEng;

    // ===== getter / setter =====

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProcessMethod() {
        return processMethod;
    }

    public void setProcessMethod(String processMethod) {
        this.processMethod = processMethod;
    }

    public String getProcessMethodEng() {
        return processMethodEng;
    }

    public void setProcessMethodEng(String processMethodEng) {
        this.processMethodEng = processMethodEng;
    }
}
