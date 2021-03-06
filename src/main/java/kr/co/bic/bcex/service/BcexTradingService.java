package kr.co.bic.bcex.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import kr.co.bic.bcex.bean.BalanceReqBean;
import kr.co.bic.bcex.bean.BcexSignedReqBeanInterface;
import kr.co.bic.bcex.bean.CancelOrderReqBean;
import kr.co.bic.bcex.bean.OrderListReqBean;
import kr.co.bic.bcex.bean.OrderListResBean;
import kr.co.bic.bcex.bean.PlaceOrderReqBean;
import kr.co.bic.bcex.util.CryptoUtil;

@Component
public class BcexTradingService {
	
	@Autowired
    private ReloadableResourceBundleMessageSource messageSource;
	
	@Autowired
	CryptoUtil cryptoUtil;
	
	private Gson gson;
	private JsonParser jsonParser;
	
	private String apiScheme;
	private String apiHost;
	private String apiPath;
	private String apiKey;
	
	public void initParams() {
		this.apiScheme = messageSource.getMessage("api.uri.scheme", null, Locale.US);
		this.apiHost = messageSource.getMessage("api.uri.host", null, Locale.US);
		this.apiPath = messageSource.getMessage("api.uri.path", null, Locale.US);
		this.apiKey = messageSource.getMessage("api.key", null, Locale.US);
		
		this.jsonParser = new JsonParser();
		this.gson = new Gson();
	}
	
	public double[] buyAndSellOnePrice(String market, String token) {
		
		double[] price = new double[2];
		
		RestTemplate restTemplate = new RestTemplate();
		
		URI bcexApiUri = UriComponentsBuilder.newInstance()
				.scheme(apiScheme).host(apiHost).path(apiPath).path("buyAndSellOnePrice")
				.queryParam("market", market)
				.queryParam("token", token)
				.build().toUri();
		
		ResponseEntity<String> responseEntity	=  restTemplate.exchange(RequestEntity.get(bcexApiUri).build(), String.class);
		
		if(responseEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
			
			JsonElement element = jsonParser.parse(responseEntity.getBody());
			price[0] = Double.parseDouble(element.getAsJsonObject().get("buy").getAsString());
			price[1] = Double.parseDouble(element.getAsJsonObject().get("sell").getAsString());
		}
		
		return price;
	}
	
	
	public double[] getBalance(String market, String token) {
		double[] balance = new double[2];
		
		ArrayList<String> tokens = new ArrayList<>();
		tokens.add("SEED");
		
		BalanceReqBean reqBean = new BalanceReqBean();
		reqBean.setApi_key(this.apiKey);
		reqBean.setPage("1");
		reqBean.setSize("10");
		reqBean.setTokens(tokens);
		reqBean.setSign(cryptoUtil.getEncryptMessage(reqBean));

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
//		requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		URI bcexApiUri = UriComponentsBuilder.newInstance()
				.scheme(apiScheme).host(apiHost).path(apiPath).path("getBalance")
				.build().toUri();
		
		HttpEntity<String> requestEntity = new HttpEntity<String>(gson.toJson(reqBean), requestHeaders);
		
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				bcexApiUri,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
		
		if(responseEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
//			System.out.println(responseEntity.getBody());
			JsonElement element = jsonParser.parse(responseEntity.getBody());
			
			JsonArray jsonArray = element.getAsJsonObject().get("data").getAsJsonObject().get("data").getAsJsonArray();
			for (JsonElement jsonElement : jsonArray) {
				balance[0] = Double.parseDouble(jsonElement.getAsJsonObject().get("usable").getAsString());
				balance[1] = Double.parseDouble(jsonElement.getAsJsonObject().get("total").getAsString());
			}
			
		}
		
		return balance;
	}
	
	public void placeOrder(String price, String amount) {
		PlaceOrderReqBean reqBean = new PlaceOrderReqBean();
		reqBean.setApi_key(this.apiKey);
		reqBean.setMarket_type("One");
		reqBean.setAmount(amount);
		reqBean.setMarket("BTC");
		reqBean.setToken("SEED");
		reqBean.setType("2");
		reqBean.setPrice(price);
		
		reqBean.setSign(cryptoUtil.getEncryptMessage(reqBean));
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
//		requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		URI bcexApiUri = UriComponentsBuilder.newInstance()
				.scheme(apiScheme).host(apiHost).path(apiPath).path("placeOrder")
				.build().toUri();
		
//		System.out.println(gson.toJson(reqBean));
		
		HttpEntity<String> requestEntity = new HttpEntity<String>(gson.toJson(reqBean), requestHeaders);
		
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				bcexApiUri,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
		
		if(responseEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
//			System.out.println(responseEntity.getBody());
//			JsonElement element = jsonParser.parse(responseEntity.getBody());
//			
//			JsonArray jsonArray = element.getAsJsonObject().get("data").getAsJsonObject().get("data").getAsJsonArray();
//			for (JsonElement jsonElement : jsonArray) {
//				balance[0] = Double.parseDouble(jsonElement.getAsJsonObject().get("usable").getAsString());
//				balance[1] = Double.parseDouble(jsonElement.getAsJsonObject().get("total").getAsString());
//			}
			
		}
		
		
	}
	
	public OrderListResBean getOrders() {
		OrderListReqBean reqBean = new OrderListReqBean();
		ArrayList<String> status = new ArrayList<>();
		status.add("1");
		
		reqBean.setApi_key(this.apiKey);
		reqBean.setPage("1");
		reqBean.setSize("100");
		reqBean.setStatus(status);
		
		reqBean.setSign(cryptoUtil.getEncryptMessage(reqBean));

		JsonElement element = requestAction("getOrders", reqBean);
		
		Gson gson = new Gson();
		OrderListResBean resonse = gson.fromJson(element, OrderListResBean.class);
		
		
//		JsonArray orderList = element.getAsJsonObject().get("data").getAsJsonArray();
//		for (JsonElement jsonElement : orderList) {
//				balance[0] = Double.parseDouble(jsonElement.getAsJsonObject().get("usable").getAsString());
//				balance[1] = Double.parseDouble(jsonElement.getAsJsonObject().get("total").getAsString());
//		}
		
		return resonse;

	}
	
	public void cancelOrder(ArrayList<String> orderNumbers) {
		String orderListJson = new Gson().toJson(orderNumbers);
		CancelOrderReqBean reqBean = new CancelOrderReqBean();
		reqBean.setApi_key(this.apiKey);
		reqBean.setOrder_nos(orderListJson);
		reqBean.setSign(cryptoUtil.getEncryptMessage(reqBean));
		
		requestAction("cancelOrder", reqBean);
		
	}
	
	private JsonElement requestAction(String strCMD, BcexSignedReqBeanInterface reqBean) {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		JsonElement resopnseJsonElement = null;
		
		URI bcexApiUri = UriComponentsBuilder.newInstance()
				.scheme(apiScheme).host(apiHost).path(apiPath).path(strCMD)
				.build().toUri();
		
//		System.out.println(gson.toJson(reqBean));
		
		HttpEntity<String> requestEntity = new HttpEntity<String>(gson.toJson(reqBean), requestHeaders);
		
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				bcexApiUri,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
		
		if (responseEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
			
			JsonElement element = jsonParser.parse(responseEntity.getBody());
			
			int resCode = element.getAsJsonObject().get("code").getAsInt();
			String resMsg = element.getAsJsonObject().get("msg").getAsString();
			
			if(resCode == 0) {
				resopnseJsonElement = element.getAsJsonObject().get("data");
			} else {
				System.out.println(strCMD + " Error [" + resCode + "] --> " + resMsg);
			}
		}
		
		return resopnseJsonElement;
	}
}
