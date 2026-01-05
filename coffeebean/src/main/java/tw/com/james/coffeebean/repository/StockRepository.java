package tw.com.james.coffeebean.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    public Page<Stock> search(
            Integer coffeeBeanId,
            Integer minStockG,
            Integer maxStockG,
            String purchaseDateFrom,
            String purchaseDateTo,
            Pageable pageable
    ) {
        String jpql = """
            SELECT s
            FROM Stock s
            WHERE (:coffeeBeanId IS NULL OR s.coffeeBean.id = :coffeeBeanId)
            AND (:minStockG IS NULL OR s.stockG >= :minStockG)
            AND (:maxStockG IS NULL OR s.stockG <= :maxStockG)
            AND (:purchaseDateFrom IS NULL OR s.purchaseDate >= CAST(:purchaseDateFrom AS date))
            AND (:purchaseDateTo IS NULL OR s.purchaseDate <= CAST(:purchaseDateTo AS date))
            ORDER BY s.id DESC
        """;

        List<Stock> list = em.createQuery(jpql, Stock.class)
                .setParameter("coffeeBeanId", coffeeBeanId)
                .setParameter("minStockG", minStockG)
                .setParameter("maxStockG", maxStockG)
                .setParameter("purchaseDateFrom", purchaseDateFrom)
                .setParameter("purchaseDateTo", purchaseDateTo)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        Long total = em.createQuery("""
            SELECT COUNT(s)
            FROM Stock s
            WHERE (:coffeeBeanId IS NULL OR s.coffeeBean.id = :coffeeBeanId)
            AND (:minStockG IS NULL OR s.stockG >= :minStockG)
            AND (:maxStockG IS NULL OR s.stockG <= :maxStockG)
            AND (:purchaseDateFrom IS NULL OR s.purchaseDate >= CAST(:purchaseDateFrom AS date))
            AND (:purchaseDateTo IS NULL OR s.purchaseDate <= CAST(:purchaseDateTo AS date))
        """, Long.class)
                .setParameter("coffeeBeanId", coffeeBeanId)
                .setParameter("minStockG", minStockG)
                .setParameter("maxStockG", maxStockG)
                .setParameter("purchaseDateFrom", purchaseDateFrom)
                .setParameter("purchaseDateTo", purchaseDateTo)
                .getSingleResult();

        return new PageImpl<>(list, pageable, total);
    }

}