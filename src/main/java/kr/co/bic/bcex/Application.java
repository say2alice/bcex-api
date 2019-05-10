package kr.co.bic.bcex;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import kr.co.bic.bcex.service.BcexTradingService;

@SpringBootApplication
public class Application implements CommandLineRunner {
	
	@Autowired
	BcexTradingService becxTrading;
	
	@Autowired
    private ReloadableResourceBundleMessageSource messageSource;
	
//	@Value("${api.uri.scheme}")
//	String Apiurl;
	
    public static void main(String[] args) {
//        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    	SpringApplication.run(Application.class, args);
        
//        System.out.println("Let's inspect the beans provided by Spring Boot:");
//        
//        String[] beanNames = ctx.getBeanDefinitionNames();
//        Arrays.sort(beanNames);
//        for (String beanName : beanNames) {
//            System.out.println(beanName);
//        }
        
//        System.out.println( System.getProperty("user.dir") );
    }
    
    public void checkBalance() {
    	
    }

	@Override
	public void run(String... args) throws Exception {
		becxTrading.initParams();
		// buy/sell
		double[] price = becxTrading.buyAndSellOnePrice("BTC", "SEED");
		
//		double sellPrice = price[0] * 1.2;
		System.out.println("price --> " + String.format("%,.8f", price[0]) + "/" + String.format("%,.8f", price[1]));
		
		// usable/total
		double[] balance = becxTrading.getBalance("BTC", "SEED");
		
		System.out.println("balance --> " + String.format("%,.8f", balance[0]) + "/" + String.format("%,.8f", balance[1]));
//		becxTrading.placeOrder( );
		
		double sellPrice = Double.parseDouble(messageSource.getMessage("order.sellPrice", null, Locale.US));
		if(price[0] >= sellPrice) {
			
		}
	}

}
