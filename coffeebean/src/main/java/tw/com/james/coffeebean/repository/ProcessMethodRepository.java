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

    public Integer findIdByName(String name) {
        List<Integer> list = em.createQuery(
            "SELECT p.id FROM ProcessMethod p WHERE p.processMethod = :name",
            Integer.class
        )
        .setParameter("name", name)
        .getResultList();

        return list.isEmpty() ? null : list.get(0);
    }

    public List<ProcessMethod> search(String name, String engName) {
        return em.createQuery("""
            SELECT p FROM ProcessMethod p
            WHERE (:name IS NULL OR p.processMethod LIKE :name)
                AND (:engName IS NULL OR p.processMethodEng LIKE :engName)
            ORDER BY p.id
        """, ProcessMethod.class)
        .setParameter("name", isEmpty(name) ? null : "%" + name + "%")
        .setParameter("engName", isEmpty(engName) ? null : "%" + engName + "%")
        .getResultList();
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
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
