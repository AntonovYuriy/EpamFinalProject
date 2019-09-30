package ua.nure.antonov.st4.db.entity;

import java.sql.Date;

public class Order extends Entity{

	private static final long serialVersionUID = 722597838502757420L;

	private int id;
	
	private int order_user_id;
	
	private int order_serviceType_id;
	
	private Date order_date;
	
	private boolean order_status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrder_user_id() {
		return order_user_id;
	}

	public void setOrder_user_id(int order_user_id) {
		this.order_user_id = order_user_id;
	}

	public int getOrder_serviceType_id() {
		return order_serviceType_id;
	}

	public void setOrder_serviceType_id(int order_serviceType_id) {
		this.order_serviceType_id = order_serviceType_id;
	}

	public Date getOrder_date() {
		return order_date;
	}

	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}

	public boolean isOrder_status() {
		return order_status;
	}

	public void setOrder_status(boolean order_status) {
		this.order_status = order_status;
	}
	
	@Override
	public String toString() {
		return "Order id = " + id
				+ ", made by user with id = " + order_user_id
				+ ", was ordered service with id = " + order_serviceType_id
				+ ", date of order = " + order_date
				+ ", paid = " + order_status;
	}
}
