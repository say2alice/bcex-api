package kr.co.bic.bcex.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class OrderListReqBean extends BeanBasic implements BcexSignedReqBeanInterface {
	
	private String api_key;
	
	private String page;
	
	@JsonInclude(Include.NON_EMPTY)
	private String begin_time;
	
	@JsonInclude(Include.NON_EMPTY)
	private String end_time;
	
	@JsonInclude(Include.NON_EMPTY)
	private String market;
	
	@JsonInclude(Include.NON_EMPTY)
	private String sign;
	
	private String size;
	
	@JsonInclude(Include.NON_EMPTY)
	private List<String> status;
	
	@JsonInclude(Include.NON_EMPTY)
	private List<String> tokens;
	
	@JsonInclude(Include.NON_EMPTY)
	private String type;

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

	public String getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public List<String> getStatus() {
		return status;
	}

	public void setStatus(List<String> status) {
		this.status = status;
	}

	public List<String> getTokens() {
		return tokens;
	}

	public void setTokens(List<String> tokens) {
		this.tokens = tokens;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
