package kr.co.bic.bcex.bean;

import java.util.List;

public class OrderListResBean extends BeanBasic {
	
	int count;
	
	List<Order> data;
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Order> getOrderList() {
		return data;
	}

	public void setOrderList(List<Order> orderList) {
		this.data = orderList;
	}

}
