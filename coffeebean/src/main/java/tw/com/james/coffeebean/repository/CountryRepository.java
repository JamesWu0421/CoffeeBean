package tw.com.james.coffeebean.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import tw.com.james.coffeebean.entity.Country;

import java.util.List;

@Repository
public class CountryRepository {

    @PersistenceContext
    private EntityManager em;

    // ===== CREATE =====
    public void save(Country country) {
        em.persist(country);
    }

    // ===== READ =====
    public Country findById(Integer id) {
        return em.find(Country.class, id);
    }

    public List<Country> findAll() {
        return em.createQuery(
                "SELECT c FROM Country c",
                Country.class
        ).getResultList();
    }

    public Country findByCode(String code) {
        List<Country> list = em.createQuery(
            "FROM Country c WHERE c.countryCode = :code", Country.class)
            .setParameter("code", code)
            .getResultList();

        return list.isEmpty() ? null : list.get(0);
    }

    public Integer findIdByName(String countryName) {
            List<Integer> list = em.createQuery(
                "SELECT c.id FROM Country c WHERE c.countryName = :name",
                Integer.class
            )
            .setParameter("name", countryName)
            .getResultList();

            return list.isEmpty() ? null : list.get(0);
        }

        public List<Country> search(
            String code,
            String name,
            String engName
    ) { 
        return em.createQuery("""
            SELECT c FROM Country c
            WHERE (:code IS NULL OR c.countryCode LIKE :code)
            AND (:name IS NULL OR c.countryName LIKE :name)
            AND (:engName IS NULL OR c.countryEngName LIKE :engName)
            ORDER BY c.id
        """, Country.class)
        .setParameter("code", isEmpty(code) ? null : "%" + code + "%")
        .setParameter("name", isEmpty(name) ? null : "%" + name + "%")
        .setParameter("engName", isEmpty(engName) ? null : "%" + engName + "%")
        .getResultList();
    }

    
    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    

    // ===== UPDATE =====
    public Country update(Country country) {
        return em.merge(country);
    }

    // ===== DELETE =====
    public void deleteById(Integer id) {
        Country country = findById(id);
        if (country != null) {
            em.remove(country);
        }
    }


}
