package tw.com.james.coffeebean.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import tw.com.james.coffeebean.entity.ProcessMethod;

import java.util.List;

@Repository
public class ProcessMethodRepository {

    @PersistenceContext
    private EntityManager em;

    /* ===== Create ===== */
    public void save(ProcessMethod processMethod) {
        em.persist(processMethod);
    }

    /* ===== Read  ===== */
    public ProcessMethod findById(Integer id) {
        return em.find(ProcessMethod.class, id);
    }

    
    public List<ProcessMethod> findAll() {
        return em.createQuery(
                "FROM ProcessMethod", ProcessMethod.class
        ).getResultList();
    }

    public ProcessMethod findByName(String name) {
        List<ProcessMethod> list = em.createQuery(
            "FROM ProcessMethod p WHERE p.processMethod = :name", ProcessMethod.class)
            .setParameter("name", name)
            .getResultList();

        return list.isEmpty() ? null : list.get(0);
    }

    /* ===== Update ===== */
    public ProcessMethod update(ProcessMethod processMethod) {
        return em.merge(processMethod);
    }

    /* ===== Delete ===== */
    public void delete(Integer id) {
        ProcessMethod entity = em.find(ProcessMethod.class, id);
        if (entity != null) {
            em.remove(entity);
        }
    }
}
