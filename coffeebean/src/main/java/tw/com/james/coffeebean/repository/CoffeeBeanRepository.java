package tw.com.james.coffeebean.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import tw.com.james.coffeebean.entity.CoffeeBean;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CoffeeBeanRepository {

    @PersistenceContext
    private EntityManager em;

    public CoffeeBean findById(Integer id) {
        return em.find(CoffeeBean.class, id);
    }

    /** 查 unique key（回 entity） */
    public CoffeeBean findByUnique(
            Integer year,
            String region,
            String plant
    ) {
        List<CoffeeBean> list = em.createQuery(
            """
            FROM CoffeeBean c
            WHERE c.productionYear = :year
                AND c.region = :region
                AND c.processingPlant = :plant
            """,
            CoffeeBean.class
        )
        .setParameter("year", year)
        .setParameter("region", region)
        .setParameter("plant", plant)
        .getResultList();

        return list.isEmpty() ? null : list.get(0);
    }


    public CoffeeBean save(CoffeeBean bean) {
        if (bean.getId() == null) {
            em.persist(bean);
            return bean;           
        } else {
            return em.merge(bean);
        }
    }
}
