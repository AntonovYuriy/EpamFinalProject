package ua.nure.antonov.st4.db;

public final class Fields {

	// common for all tables
	public static final String ENTITY_ID = "id";

	// Personal table fields
	public static final String USER_LOGIN = "login";
	public static final String USER_PASSWORD = "password";
	public static final String USER_FIRST_NAME = "first_name";
	public static final String USER_LAST_NAME = "last_name";
	public static final String USER_STATUS = "status";
	public static final String USER_MONEY = "money";
	public static final String USER_LOCALE_NAME = "locale_name";
	public static final String USER_ROLE_ID = "role_id";

	public static final String ORDER_USER_ID = "o_user_id";
	public static final String ORDER_SERVICETYPE_ID = "o_st_id";
	public static final String ORDER_DATE = "o_date";
	public static final String ORDER_STATUS = "o_status";

	public static final String ROLE_NAME = "name";

	public static final String SERVICE_NAME = "s_name";

	public static final String SERVICETYPE_ID = "st_id";
	public static final String SERVICETYPE_INFO = "st_info";
	public static final String SERVICETYPE_PRICE = "st_price";
	
	public static final String FULLSERVICEBEAN_NAME = "s_name";
	public static final String FULLSERVICEBEAN_ST_ID = "st_id";
	public static final String FULLSERVICEBEAN_INFO = "st_info";
	public static final String FULLSERVICEBEAN_PRICE = "st_price";

}