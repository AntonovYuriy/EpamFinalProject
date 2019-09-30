package ua.nure.antonov.st4.db.entity;

public class ServiceType extends Entity{

	private static final long serialVersionUID = 2221368863562041619L;

	private int id;

	private int st_id;
	
	private String st_info;
	
	private int st_price;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSt_id() {
		return st_id;
	}

	public void setSt_id(int st_id) {
		this.st_id = st_id;
	}

	public String getSt_info() {
		return st_info;
	}

	public void setSt_info(String st_info) {
		this.st_info = st_info;
	}

	public int getSt_price() {
		return st_price;
	}

	public void setSt_price(int st_price) {
		this.st_price = st_price;
	}

	@Override
	public String toString() {
		return "Service type N = " + id
				+ ", service id = " + st_id 
				+ ", information = " + st_info
				+ ", price = " + st_price;
	}
}
