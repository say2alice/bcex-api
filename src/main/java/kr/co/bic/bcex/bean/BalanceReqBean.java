package kr.co.bic.bcex.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class BalanceReqBean extends BeanBasic implements BcexSignedReqBeanInterface {
	
	private String api_key;
	private String page;
	private String size;
	
	@JsonInclude(Include.NON_NULL)
	private String sign;
	
	@JsonInclude(Include.NON_NULL)
	private List<String> tokens;
	
	public String getApi_key() {
		return api_key;
	}
	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public List<String> getTokens() {
		return tokens;
	}
	public void setTokens(List<String> tokens) {
		this.tokens = tokens;
	}
}
