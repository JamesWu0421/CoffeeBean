package tw.com.james.coffeebean.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor; 
import tw.com.james.coffeebean.entity.Roast;


public interface RoastRepository extends JpaRepository<Roast, Integer>, JpaSpecificationExecutor<Roast> {
}