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
