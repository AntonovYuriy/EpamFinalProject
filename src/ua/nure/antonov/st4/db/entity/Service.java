package ua.nure.antonov.st4.db.entity;

public class Service extends Entity{

	private static final long serialVersionUID = 1L;

	private int id;
	
	private String s_name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getS_name() {
		return s_name;
	}

	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	@Override
	public String toString() {
		return "Service ID =" + id
				+ ", name = " + s_name;
	}


}
