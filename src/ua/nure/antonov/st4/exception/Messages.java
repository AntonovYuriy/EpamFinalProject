package ua.nure.antonov.st4.exception;

public class Messages {

	private Messages() {
	}
	
	public static final String ERR_CANNOT_ROLLBACK_TRANSACTION = "Cannot rollback transaction";
	
	public static final String ERR_CANNOT_ROLLBACK_AND_CLOSE_CONNECTION = "Cannot rollback and close connection";
	
	public static final String ERR_CANNOT_CLOSE_CONNECTION = "Cannot close a connection";

	public static final String ERR_CANNOT_CLOSE_RESULTSET = "Cannot close a result set";

	public static final String ERR_CANNOT_CLOSE_STATEMENT = "Cannot close a statement";
	
	public static final String ERR_CANNOT_OBTAIN_CONNECTION = "Cannot obtain a connection from the pool";
	
	public static final String ERR_CANNOT_UPDATE_USER = "Cannot update a user in DB";
	
	public static final String ERR_CANNOT_OBTAIN_ORDERS = "Cannot obtain all orders from DB";
	
	public static final String ERR_CANNOT_OBTAIN_USERS = "Cannot obtain all users from DB";
	
	public static final String ERR_CANNOT_OBTAIN_USER_BY_ID = "Cannot obtain a user by its id";
	
	public static final String ERR_CANNOT_OBTAIN_FULLSERVICES = "Cannot obtain a list of FullServices from DB";
	
	// SORT!!!
	
	public static final String ERR_CANNOT_OBTAIN_USER_ORDER_BEANS = "Cannot obtain user order beans";

	public static final String ERR_CANNOT_OBTAIN_CATEGORIES = "Cannot obtain categories";

	public static final String ERR_CANNOT_OBTAIN_MENU_ITEMS = "Cannot obtain menu items";

	public static final String ERR_CANNOT_OBTAIN_MENU_ITEMS_BY_ORDER = "Cannot obtain menu items by order";

	public static final String ERR_CANNOT_OBTAIN_MENU_ITEMS_BY_IDENTIFIERS = "Cannot obtain menu items by its identifiers";

	

	public static final String ERR_CANNOT_OBTAIN_ORDERS_BY_STATUS_ID = "Cannot obtain orders by status id";

	public static final String ERR_CANNOT_OBTAIN_ORDERS_BY_IDENTIFIERS = "Cannot obtain orders by its identifiers";

	public static final String ERR_CANNOT_OBTAIN_ORDERS_BY_USER_AND_STATUS_ID = "Cannot obtain orders by user and status id";


	public static final String ERR_CANNOT_OBTAIN_USER_BY_LOGIN = "Cannot obtain a user by it login from DB";

	public static final String ERR_CANNOT_OBTAIN_DATA_SOURCE = "Cannot obtain the data source";

	public static final String ERR_ACCESS_ONLY_FOR_ADMINISTARTOR = "Only administrator have access to this page";

	public static final String ERR_CANNOT_OBTAIN_FSB_BY_ID = "Cannot obtain Services types by ID from DB";

	public static final String ERR_CANNOT_DELETE_ST_BY_ID = "Cannot delete from service type by id in DB";

	public static final String ERR_CANNOT_UPDATE_FSB = "Cannot update service type by id in DB";

	public static final String ERR_CANNOT_OBTAIN_SERVICES = "Cannot get services from DB";


	
}