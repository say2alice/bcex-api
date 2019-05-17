package kr.co.bic.bcex.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class CancelOrderReqBean extends BeanBasic implements BcexSignedReqBeanInterface {
	
	private String api_key;
	
	String order_nos;
	
	@JsonInclude(Include.NON_EMPTY)
	private String sign;

	public String getApi_key() {
		return api_key;
	}

	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}

	public String getOrder_nos() {
		return order_nos;
	}

	public void setOrder_nos(String order_nos) {
		this.order_nos = order_nos;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
