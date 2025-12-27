package tw.com.james.coffeebean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "tw.com.james.coffeebean")
public class CoffeebeanApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoffeebeanApplication.class, args);
	}

}
