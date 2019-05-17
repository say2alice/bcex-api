package kr.co.bic.bcex;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.google.gson.Gson;

import kr.co.bic.bcex.bean.Order;
import kr.co.bic.bcex.bean.OrderListResBean;
import kr.co.bic.bcex.service.BcexTradingService;

@SpringBootApplication
@EnableScheduling
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
    
    /**
	 * 매일 오전 4시에 한달 전 보낸 메시지 삭제
	 * 초 분 시 일 월 주(년)
     * @throws InterruptedException 
	 */
    @Scheduled(fixedRateString = "2000")
    public void scheduleOrder() throws InterruptedException {
    	becxTrading.initParams();
    	
    	SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");  
    	Date date = new Date(System.currentTimeMillis());
    	System.out.println(formatter.format(date));
    	
    	OrderListResBean orderListResBean = becxTrading.getOrders();
		List<Order> OrderList = orderListResBean.getOrderList();
		
		String strSellPrice = getSysMsg("common.satoshiPrefix") + getSysMsg("order.sellPrice");
		String strSellAmount = getSysMsg("order.sellAmount");
		double sellPrice = Double.parseDouble(strSellPrice);
		
		// usable/total
		double[] balance = becxTrading.getBalance("BTC", "SEED");
		
		if(balance[0] < Double.parseDouble(strSellAmount)) {
			strSellAmount = String.format("%,.8f", balance[0]);
		}
		
		if(orderListResBean.getCount() <= 0) {
			becxTrading.placeOrder(strSellPrice, strSellAmount);
			System.out.println("New Order  --> " + strSellPrice);
		} else {
			ArrayList<String> cancelList = new ArrayList<String>();
			for (Order order : OrderList) {
				if(sellPrice != order.getfPrice()) {
					 cancelList.add(order.getOrder_no());
				}
			}
			
			if(cancelList.size() > 0) {
				becxTrading.cancelOrder(cancelList);
				System.out.println("cancel Orders  --> " + new Gson().toJson(cancelList));
				Thread.sleep(500); 
				becxTrading.placeOrder(strSellPrice, getSysMsg("order.sellAmount"));
				System.out.println("New Order  --> " + strSellPrice);
			}
		}
		
    }

	@Override
	public void run(String... args) throws Exception {
		
		
//		// buy/sell
//		double[] price = becxTrading.buyAndSellOnePrice("BTC", "SEED");
//		
////		double sellPrice = price[0] * 1.2;
//		System.out.println("price --> " + String.format("%,.8f", price[0]) + "/" + String.format("%,.8f", price[1]));
//		
//		// usable/total
//		double[] balance = becxTrading.getBalance("BTC", "SEED");
//		
//		System.out.println("balance --> " + String.format("%,.8f", balance[0]) + "/" + String.format("%,.8f", balance[1]));
////		common.satoshi_prefix
//		
//		System.out.println("sellPrice --> " + String.format("%,.8f", sellPrice) );
//		if(price[0] >= sellPrice) {
//			
//		}
//		// getOrderList
//		System.out.println("orderCount --> " + becxTrading.getOrders().getCount() );
	}
	
	private String getSysMsg(String code) {
		return messageSource.getMessage(code, null, Locale.US);
	}

}
