package tw.com.james.coffeebean.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import tw.com.james.coffeebean.entity.CoffeeBean;
import tw.com.james.coffeebean.entity.Stock;

import java.util.List;

@Repository
public class StockRepository {

    @PersistenceContext
    private EntityManager em;

    
    public Stock findById(Integer id) {
        return em.find(Stock.class, id);
    }

    
    public List<Stock> findAll() {
        return em.createQuery("FROM Stock", Stock.class).getResultList();
    }

    
    public boolean existsById(Integer id) {
        Long count = em.createQuery("SELECT COUNT(s) FROM Stock s WHERE s.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult();
        return count > 0;
    }

    
    public Stock findByCoffeeBean(CoffeeBean coffeeBean) {
        List<Stock> list = em.createQuery(
            """
            FROM Stock s
            WHERE s.coffeeBean = :coffeeBean
            """,
            Stock.class
        )
        .setParameter("coffeeBean", coffeeBean)
        .getResultList();

        return list.isEmpty() ? null : list.get(0);
    }

    
    public Stock save(Stock stock) {
        if (stock.getId() == null) {
            em.persist(stock);
            return stock; 
        } else {
            return em.merge(stock); 
        }
    }

    
    public void deleteById(Integer id) {
        Stock stock = findById(id);
        if (stock != null) {
            em.remove(stock);
        }
    }
}