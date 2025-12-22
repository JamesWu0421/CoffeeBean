package tw.com.james.coffeebean.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "process_method")
public class ProcessMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "process_method", length = 50)
    private String processMethod;

    @Column(name = "process_method_eng", length = 50)
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
