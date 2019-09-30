package ua.nure.antonov.st4.db;

public class SQLRequests {

	static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";

	static final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE id=?";

	static final String SQL_FIND_ALL_ORDERS = "SELECT * FROM orders";

	public static final String SQL_FIND_MY_ORDERS = "SELECT * FROM orders WHERE o_user_id =?";

	static final String SQL_UPDATE_USER_FROM_SETTINGS = "UPDATE users SET first_name=?, last_name=?, locale_name=? WHERE id=?";

	static final String SQL_FIND_ALL_USERS = "SELECT * FROM users";

	static final String SQL_UPDATE_USER_FROM_RECHARGE = "UPDATE users SET money=? WHERE id=?";

	static final String SQL_INSERT_USER = "INSERT INTO users VALUES (DEFAULT,?,?,?,?,?,?,?,?)";

	static final String SQL_INSERT_SERVICE_TYPE = "INSERT INTO services_types VALUES (DEFAULT,?,?,?)";
	
	static final String SQL_INSERT_ORDER = "INSERT INTO orders VALUES (DEFAULT,?,?,?,?)";

	static final String SQL_RESET_PASSWORD_FOR_USER = "UPDATE users SET password=? WHERE id=?";

	static final String SQL_FIND_ALL_FULL_SERVICE_LIST = "SELECT services_types.id, services.s_name, services_types.st_id, services_types.st_info, services_types.st_price from services, services_types  WHERE services.id=services_types.st_id";

	static final String SQL_FIND_FULL_SERVICE_BY_ID = "SELECT services_types.id, services.s_name, services_types.st_id, services_types.st_info, services_types.st_price from services, services_types  WHERE services.id=services_types.st_id AND services_types.id=?";

	static final String SQL_CHANGE_USER_STATUS_TO_FALSE = "UPDATE users SET status=FALSE WHERE id=?";

	static final String SQL_CHANGE_USER_STATUS_TO_TRUE = "UPDATE users SET status=TRUE WHERE id=?";

	static final String SQL_SET_MONEY_FOR_USER = "UPDATE users SET money=? WHERE id=?";

	static final String SQL_DELETE_FROM_ST_BY_ID = "DELETE FROM services_types WHERE id=?";
	
	static final String SQL_UPDATE_SERVICE_TYPE_BY_ID = "UPDATE services_types SET st_id=?, st_info=?, st_price=? WHERE id=?";

	static final String SQL_FIND_ALL_SERVICES = "SELECT * FROM services";

	public static final String SQL_SELECT_COUNT_FROM_ORDERS_BY_ID = "select count(*) from orders where o_st_id=?";

}
