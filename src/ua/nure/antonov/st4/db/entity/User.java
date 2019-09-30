package ua.nure.antonov.st4.db.entity;

public class User extends Entity {

	private static final long serialVersionUID = -6889036256149495388L;

	private int id;
	
	private String login;

	private String password;

	private String firstName;

	private String lastName;
	
	private boolean status;

	private int money;
	
	private String locale;

	private int roleId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "User [id=" + id + 
				" , login=" + login 
				+ ", password=" + password
				+ ", firstName=" + firstName 
				+ ", lastName=" + lastName 
				+ ", status=" + status 
				+ ", money=" + money
				+ ", locale=" + locale 
				+ ", roleId=" + roleId + "]";
	}

}
