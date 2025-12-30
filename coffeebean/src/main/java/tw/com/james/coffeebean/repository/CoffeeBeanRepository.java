package tw.com.james.coffeebean.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import tw.com.james.coffeebean.entity.CoffeeBean;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    public List<CoffeeBean> findAll() {
        return em.createQuery(
            """
            SELECT c
            FROM CoffeeBean c
            LEFT JOIN FETCH c.country
            LEFT JOIN FETCH c.processMethod
            LEFT JOIN FETCH c.beanMerchant
            ORDER BY c.id
            """,
            CoffeeBean.class
        ).getResultList();
    }

    public void deleteById(Integer id) {
        CoffeeBean bean = findById(id);
        if (bean != null) {
            em.remove(bean);
        }
    }

    public Page<CoffeeBean> search(
        Integer countryId,
        Integer processMethodId,
        Integer merchantId,
        String beanVariety,
        Integer productionYear,
        Pageable pageable
) {
    String jpql = """
        SELECT c
        FROM CoffeeBean c
        WHERE (:countryId IS NULL OR c.country.id = :countryId)
          AND (:processMethodId IS NULL OR c.processMethod.id = :processMethodId)
          AND (:merchantId IS NULL OR c.beanMerchant.id = :merchantId)
          AND (:beanVariety IS NULL OR c.beanVariety LIKE :beanVariety)
          AND (:productionYear IS NULL OR c.productionYear = :productionYear)
        ORDER BY c.id
    """;

    List<CoffeeBean> list = em.createQuery(jpql, CoffeeBean.class)
            .setParameter("countryId", countryId)
            .setParameter("processMethodId", processMethodId)
            .setParameter("merchantId", merchantId)
            .setParameter("beanVariety", beanVariety)
            .setParameter("productionYear", productionYear)
            .setFirstResult((int) pageable.getOffset())
            .setMaxResults(pageable.getPageSize())
            .getResultList();

    Long total = em.createQuery("""
        SELECT COUNT(c)
        FROM CoffeeBean c
        WHERE (:countryId IS NULL OR c.country.id = :countryId)
          AND (:processMethodId IS NULL OR c.processMethod.id = :processMethodId)
          AND (:merchantId IS NULL OR c.beanMerchant.id = :merchantId)
          AND (:beanVariety IS NULL OR c.beanVariety LIKE :beanVariety)
          AND (:productionYear IS NULL OR c.productionYear = :productionYear)
    """, Long.class)
            .setParameter("countryId", countryId)
            .setParameter("processMethodId", processMethodId)
            .setParameter("merchantId", merchantId)
            .setParameter("beanVariety", beanVariety)
            .setParameter("productionYear", productionYear)
            .getSingleResult();

    return new PageImpl<>(list, pageable, total);
}

}
