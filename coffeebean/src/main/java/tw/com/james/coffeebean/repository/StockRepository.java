package tw.com.james.coffeebean.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public class StockRepository {

    @PersistenceContext
    private EntityManager em;

    public void insert(
            Integer coffeeBeanId,
            Integer stockG,
            BigDecimal purchasePrice,
            BigDecimal sellingPrice,
            LocalDate purchaseDate
    ) {
        em.createNativeQuery(
            """
            INSERT INTO stock
            (coffee_bean_id, stock_g, purchase_price, selling_price, purchase_date)
            VALUES (?, ?, ?, ?, ?)
            """
        )
        .setParameter(1, coffeeBeanId)
        .setParameter(2, stockG)
        .setParameter(3, purchasePrice)
        .setParameter(4, sellingPrice)
        .setParameter(5, purchaseDate)
        .executeUpdate();
    }

    public void updateByCoffeeBeanId(
            Integer coffeeBeanId,
            Integer stockG,
            BigDecimal purchasePrice,
            BigDecimal sellingPrice
    ) {
        em.createNativeQuery(
            """
            UPDATE stock
            SET stock_g = ?,
                purchase_price = ?,
                selling_price = ?
            WHERE coffee_bean_id = ?
            """
        )
        .setParameter(1, stockG)
        .setParameter(2, purchasePrice)
        .setParameter(3, sellingPrice)
        .setParameter(4, coffeeBeanId)
        .executeUpdate();
    }
}
