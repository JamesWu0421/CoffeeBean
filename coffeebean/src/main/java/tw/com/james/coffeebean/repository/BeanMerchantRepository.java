package tw.com.james.coffeebean.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import tw.com.james.coffeebean.entity.BeanMerchant;

@Repository
public class BeanMerchantRepository {
    @PersistenceContext
    private EntityManager em;

    /* ===== Create ===== */
    public void save(BeanMerchant beanMerchant) {
        em.persist(beanMerchant);
    }

    /* ===== Read  ===== */
    public BeanMerchant findById(Integer id) {
        return em.find(BeanMerchant.class, id);
    }

    
    public List<BeanMerchant> findAll() {
        return em.createQuery(
                "FROM BeanMerchant", BeanMerchant.class
        ).getResultList();
    }

    public BeanMerchant findByName(String name) {
        List<BeanMerchant> list = em.createQuery(
                "FROM BeanMerchant b WHERE b.merchantName = :name",
                BeanMerchant.class
        ).setParameter("name", name)
        .getResultList();

        return list.isEmpty() ? null : list.get(0);
    }
    
    public Integer findIdByName(String name) {
        List<Integer> list = em.createQuery(
            "SELECT b.id FROM BeanMerchant b WHERE b.merchantName = :name",
            Integer.class
        )
        .setParameter("name", name)
        .getResultList();

        return list.isEmpty() ? null : list.get(0);
    }

    public List<BeanMerchant> search(String name) {
        return em.createQuery("""
            SELECT m FROM BeanMerchant m
            WHERE (:name IS NULL OR m.merchantName LIKE :name)
            ORDER BY m.id
        """, BeanMerchant.class)
        .setParameter("name", isEmpty(name) ? null : "%" + name + "%")
        .getResultList();
    }
    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    /* ===== Update ===== */
    public BeanMerchant update(BeanMerchant beanMerchant) {
        return em.merge(beanMerchant);
    }

    /* ===== Delete ===== */
    public void delete(Integer id) {
        BeanMerchant entity = em.find(BeanMerchant.class, id);
        if (entity != null) {
            em.remove(entity);
        }
    }
}
