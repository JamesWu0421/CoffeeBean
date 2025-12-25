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


    public void save(Stock stock) {
        if (stock.getId() == null) {
            em.persist(stock);
        } else {
            em.merge(stock);
        }
    }
}
