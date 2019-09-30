package ua.nure.antonov.st4.db.entity;

public class FullServiceBean extends Entity {

	/**
	 * Bean for a full data about the service
	 */
	
	private static final long serialVersionUID = 7070865232170322221L;

	private int id;
	
	private String sName;
	
	private int stId;
	
	private String stInfo;
	
	private int price;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public int getStId() {
		return stId;
	}

	public void setStId(int stId) {
		this.stId = stId;
	}

	public String getStInfo() {
		return stInfo;
	}

	public void setStInfo(String stInfo) {
		this.stInfo = stInfo;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Full Service id = " + id
				+ ", name = " + sName
				+ ", serviceTypeId = " + stId
				+ ", info = " + stInfo
				+ ", price = " + price;
	}
	
	

}
