package kr.co.bic.bcex.bean;

public class Order extends BeanBasic {

	String order_no;

	String market;

	String token;

	String price;
	
	double fPrice;

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public double getfPrice() {
		if(this.price != null && this.fPrice == 0.0f) {
			this.fPrice = Double.parseDouble(this.price);
		}
		
		return fPrice;
	}
}
