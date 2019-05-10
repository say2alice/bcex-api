package kr.co.bic.bcex.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class PlaceOrderReqBean extends BeanBasic implements BcexReqBeanInterface {
	private String amount;
	
	private String api_key;
	
	private String market;
	
	/**
	 * 1
	 */
	private String market_type;
	
	private String price;
	
	@JsonInclude(Include.NON_NULL)
	private String sign;
	
	private String token;
	
	/**
	 * 1 buy, 2 sell
	 */
	private String type;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getApi_key() {
		return api_key;
	}

	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getMarket_type() {
		return market_type;
	}

	public void setMarket_type(String market_type) {
		this.market_type = market_type;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
