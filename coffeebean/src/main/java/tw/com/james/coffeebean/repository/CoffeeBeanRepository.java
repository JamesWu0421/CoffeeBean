package tw.com.james.coffeebean.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CoffeeBeanRepository {

    @PersistenceContext
    private EntityManager em;

    public Integer findIdByUnique(
            Integer year,
            String region,
            String plant
    ) {
        List<Integer> list = em.createQuery(
            """
            SELECT c.id
            FROM CoffeeBean c
            WHERE c.productionYear = :year
                AND c.region = :region
                AND c.processingPlant = :plant
            """,
            Integer.class
        )
        .setParameter("year", year)
        .setParameter("region", region)
        .setParameter("plant", plant)
        .getResultList();

        return list.isEmpty() ? null : list.get(0);
    }

    public Integer insert(
        Integer countryId,
        Integer processId,
        Integer merchantId,
        String region,
        String plant,
        String variety,
        Integer year
) {
    Object result = em.createNativeQuery(
        """
        INSERT INTO coffee_bean
        (country_id, process_method_id, bean_merchant_id,
            region, processing_plant, bean_variety, production_year)
        OUTPUT INSERTED.id
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """
    )
    .setParameter(1, countryId)
    .setParameter(2, processId)
    .setParameter(3, merchantId)
    .setParameter(4, region)
    .setParameter(5, plant)
    .setParameter(6, variety)
    .setParameter(7, year)
    .getSingleResult();

    return ((Number) result).intValue();
}
}