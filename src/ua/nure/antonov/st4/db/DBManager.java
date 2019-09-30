package ua.nure.antonov.st4.db;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.nure.antonov.st4.db.entity.FullServiceBean;
import ua.nure.antonov.st4.db.entity.Order;
import ua.nure.antonov.st4.db.entity.Service;
import ua.nure.antonov.st4.db.entity.ServiceType;
import ua.nure.antonov.st4.exception.AppException;
import ua.nure.antonov.st4.exception.DBException;
import ua.nure.antonov.st4.exception.Messages;
import ua.nure.antonov.st4.db.entity.User;
import ua.nure.antonov.st4.db.Fields;
import ua.nure.antonov.st4.db.SQLRequests;
import ua.nure.antonov.st4.support.NameGenerator;

public class DBManager {

	private static final Logger LOG = Logger.getLogger(DBManager.class);

	private static DBManager instance;

	private DBManager() {
	}

// This is singleton

	public static synchronized DBManager getInstance() {
		if (instance == null)
			instance = new DBManager();
		return instance;
	}

//GETTING CONNECTION FROM confix.xml data with DataSource

	public Connection getConnection() throws SQLException {
		Connection con = null;
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");

			DataSource ds = (DataSource) envContext.lookup("jdbc/st4db");
			con = ds.getConnection();
		} catch (NamingException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
		}
		return con;
	}

// CONNECTION ROLLING BACK OR CLOSE METHODS

	public void rollbackAndClose(Connection con) {
		try {
			con.rollback();
			con.close();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_ROLLBACK_AND_CLOSE_CONNECTION, ex);
		}
	}

	private void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_ROLLBACK_TRANSACTION, ex);
			}
		}
	}

	public void commitAndClose(Connection con) {
		try {
			con.commit();
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	private void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
			}
		}
	}

	private void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
			}
		}
	}

	private void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, ex);
			}
		}
	}

	private void close(Connection con, Statement stmt, ResultSet rs) {
		close(rs);
		close(stmt);
		close(con);
	}

/////SQL SEARCH 

	public User findUserByLogin(String login) throws DBException {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQLRequests.SQL_FIND_USER_BY_LOGIN);
			pstmt.setString(1, login);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return user;
	}

	public User findUserByID(int id) throws DBException {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQLRequests.SQL_FIND_USER_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return user;
	}

	public List<Service> findAllServices() throws DBException {
		List<Service> servicesList = new ArrayList<Service>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQLRequests.SQL_FIND_ALL_SERVICES);
			while (rs.next()) {
				servicesList.add(extractService(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_SERVICES, ex);
		} finally {
			close(con, stmt, rs);
		}
		return servicesList;
	}

	public List<User> findAllUsers() throws DBException {
		List<User> usersList = new ArrayList<User>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQLRequests.SQL_FIND_ALL_USERS);
			while (rs.next()) {
				usersList.add(extractUser(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USERS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return usersList;
	}

	public List<Order> findAllOrders() throws DBException {
		List<Order> ordersList = new ArrayList<Order>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQLRequests.SQL_FIND_ALL_ORDERS);
			while (rs.next()) {
				ordersList.add(extractOrder(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_ORDERS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return ordersList;
	}

	public List<Order> findMyAllOrders(User user) throws DBException {
		List<Order> ordersList = new ArrayList<Order>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQLRequests.SQL_FIND_MY_ORDERS);
			pstmt.setInt(1, user.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ordersList.add(extractOrder(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_ORDERS, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return ordersList;
	}

	public List<FullServiceBean> findAllFullServices() throws DBException {
		List<FullServiceBean> fullServiceList = new ArrayList<FullServiceBean>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQLRequests.SQL_FIND_ALL_FULL_SERVICE_LIST);
			while (rs.next()) {
				fullServiceList.add(extractFullService(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_FULLSERVICES, ex);
		} finally {
			close(con, stmt, rs);
		}
		return fullServiceList;
	}

	public FullServiceBean findFullServiceBeanById(int id) throws DBException {
		FullServiceBean fsb = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQLRequests.SQL_FIND_FULL_SERVICE_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				fsb = extractFullService(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_FSB_BY_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return fsb;
	}

	// DELETING METHODS

	public void deleteServiceWithId(int id) throws DBException {
		Connection con = null;
		try {
			con = getConnection();
			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement(SQLRequests.SQL_DELETE_FROM_ST_BY_ID);
				pstmt.setInt(1, id);
				pstmt.executeUpdate();
			} finally {
				close(pstmt);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_DELETE_ST_BY_ID, ex);
		} finally {
			close(con);
		}
	}

	// UPDATING METHODS

	public void setNewPasswordForUser(User user, String password) throws DBException {
		Connection con = null;
		try {
			con = getConnection();
			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement(SQLRequests.SQL_RESET_PASSWORD_FOR_USER);
				int k = 1;
				pstmt.setString(k++, password);
				pstmt.setInt(k, user.getId());
				pstmt.executeUpdate();
			} finally {
				close(pstmt);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
		} finally {
			close(con);
		}
	}

	public void updateServiceTypefromFullServiceBean(FullServiceBean fsb) throws DBException {
		Connection con = null;
		try {
			con = getConnection();
			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement(SQLRequests.SQL_UPDATE_SERVICE_TYPE_BY_ID);
				int k = 1;
				pstmt.setInt(k++, fsb.getStId());
				pstmt.setString(k++, fsb.getStInfo());
				pstmt.setInt(k++, fsb.getPrice());
				pstmt.setInt(k, fsb.getId());
				pstmt.executeUpdate();
			} finally {
				close(pstmt);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_FSB, ex);
		} finally {
			close(con);
		}
	}

	public void updateUserFromSettings(User user) throws DBException {
		Connection con = null;
		try {
			con = getConnection();
			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement(SQLRequests.SQL_UPDATE_USER_FROM_SETTINGS);
				int k = 1;
				pstmt.setString(k++, user.getFirstName());
				pstmt.setString(k++, user.getLastName());
				pstmt.setString(k++, user.getLocale());
				pstmt.setInt(k, user.getId());
				pstmt.executeUpdate();
			} finally {
				close(pstmt);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
		} finally {
			close(con);
		}
	}

	public void updateUserFromRecharge(User user) throws DBException {
		Connection con = null;
		try {
			con = getConnection();
			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement(SQLRequests.SQL_UPDATE_USER_FROM_RECHARGE);
				pstmt.setInt(1, user.getMoney());
				pstmt.setInt(2, user.getId());
				pstmt.executeUpdate();
			} finally {
				close(pstmt);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
		} finally {
			close(con);
		}
	}

	public boolean blockUser(User user) throws DBException {
		boolean result = false;
		Connection con = null;
		try {
			con = getConnection();
			PreparedStatement psmt = null;
			try {
				psmt = con.prepareStatement(SQLRequests.SQL_CHANGE_USER_STATUS_TO_FALSE);
				psmt.setInt(1, user.getId());
				psmt.executeUpdate();
				result = true;
			} catch (Exception e) {
				LOG.trace("Something wrong during blocking user in DB");
				throw new DBException();
			} finally {
				con.commit();
				close(psmt);
			}
		} catch (Exception e) {
			rollback(con);
			throw new DBException("Cannot set up connection to DB ", e);
		}
		return result;
	}

	public boolean UnBlockUser(User user) throws DBException {
		boolean result = false;
		Connection con = null;
		try {
			con = getConnection();
			PreparedStatement psmt = null;
			try {
				psmt = con.prepareStatement(SQLRequests.SQL_CHANGE_USER_STATUS_TO_TRUE);
				psmt.setInt(1, user.getId());
				psmt.executeUpdate();
				result = true;
			} catch (Exception e) {
				LOG.trace("Something wrong during UNblocking user in DB");
				throw new DBException();
			} finally {
				con.commit();
				close(psmt);
			}
		} catch (Exception e) {
			rollback(con);
			throw new DBException("Cannot set up connection to DB ", e);
		}
		return result;
	}

	// Inserting methods

	public Boolean createNewServiceType(int stId, String sInfo, String sPrice) throws DBException {
		boolean result = false;
		Connection con = null;
		try {
			con = getConnection();
			PreparedStatement psmt = null;
			try {
				psmt = con.prepareStatement(SQLRequests.SQL_INSERT_SERVICE_TYPE);
				int k = 1;
				psmt.setInt(k++, stId);
				psmt.setString(k++, sInfo);
				psmt.setString(k, sPrice);
				psmt.executeUpdate();
				result = true;
			} catch (Exception e) {
				LOG.trace("Something wrong during adding service_type to DB");
				throw new DBException();
			} finally {
				con.commit();
				close(psmt);
			}
		} catch (Exception e) {
			rollback(con);
			throw new DBException("Cannot set up connection to DB ", e);
		}
		return result;
	}

	public Boolean createNewUser(User newUser) throws AppException {
		boolean result = false;
		Connection con = null;
		try {
			con = getConnection();
			PreparedStatement psmt = null;
			try {
				psmt = con.prepareStatement(SQLRequests.SQL_INSERT_USER);
				int k = 1;
				psmt.setString(k++, newUser.getLogin());
				psmt.setString(k++, newUser.getPassword());
				psmt.setString(k++, newUser.getFirstName());
				psmt.setString(k++, newUser.getLastName());
				psmt.setBoolean(k++, newUser.isStatus());
				psmt.setInt(k++, newUser.getMoney());
				psmt.setString(k++, newUser.getLocale());
				psmt.setInt(k++, newUser.getRoleId());
				psmt.executeUpdate();
				result = true;
			} catch (Exception e) {
				LOG.trace("Something wrong during adding user to DB");
				throw new DBException();
			} finally {
				con.commit();
				close(psmt);
			}
		} catch (Exception e) {
			rollback(con);
			throw new DBException("Cannot set up connection to DB ", e);
		}
		return result;
	}

	public boolean makeOrder(FullServiceBean fs, User user) throws DBException {
		boolean result = false;
		Connection con = null;
		try {
			con = getConnection();
			PreparedStatement psmt = null;
			try {
				psmt = con.prepareStatement(SQLRequests.SQL_INSERT_ORDER);
				int k = 1;
				psmt.setInt(k++, user.getId());
				psmt.setInt(k++, fs.getStId());
				psmt.setDate(k++, new java.sql.Date(new java.util.Date().getTime()));
				psmt.setBoolean(k++, Boolean.TRUE);
				psmt.executeUpdate();
				result = true;
			} catch (Exception e) {
				LOG.trace("Something wrong during adding user to DB");
				throw new DBException();
			} finally {
				con.commit();
			}

			try {
				int k = 1;
				psmt = con.prepareStatement(SQLRequests.SQL_SET_MONEY_FOR_USER);
				psmt.setInt(k++, user.getMoney() - fs.getPrice());
				psmt.setInt(k, user.getId());
				psmt.executeUpdate();
				result = true;
			} catch (Exception e) {
				LOG.trace("Something wrong during adding user to DB");
				throw new DBException();
			} finally {
				con.commit();
				close(psmt);
			}
		} catch (Exception e) {
			rollback(con);
			throw new DBException("Cannot set up connection to DB ", e);
		}
		return result;
	}

	// Extracting methods

	private Order extractOrder(ResultSet rs) throws SQLException {
		Order order = new Order();
		order.setId(rs.getInt(Fields.ENTITY_ID));
		order.setOrder_user_id(rs.getInt(Fields.ORDER_USER_ID));
		order.setOrder_serviceType_id(rs.getInt(Fields.ORDER_SERVICETYPE_ID));
		order.setOrder_date(rs.getDate(Fields.ORDER_DATE));
		order.setOrder_status(rs.getBoolean(ua.nure.antonov.st4.db.Fields.ORDER_STATUS));
		return order;
	}

	private User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt(Fields.ENTITY_ID));
		user.setLogin(rs.getString(Fields.USER_LOGIN));
		user.setPassword(rs.getString(Fields.USER_PASSWORD));
		user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
		user.setLastName(rs.getString(Fields.USER_LAST_NAME));
		user.setStatus(rs.getBoolean(Fields.USER_STATUS));
		user.setMoney(rs.getInt(Fields.USER_MONEY));
		user.setLocale(rs.getString(Fields.USER_LOCALE_NAME));
		user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
		return user;
	}

	private Service extractService(ResultSet rs) throws SQLException {
		Service ser = new Service();
		ser.setId(rs.getInt(Fields.ENTITY_ID));
		ser.setS_name(rs.getString(Fields.SERVICE_NAME));
		LOG.info("Extracted service " + ser + " from DB");
		return ser;
	}

	private ServiceType extractServiceType(ResultSet rs) throws SQLException {
		ServiceType st = new ServiceType();
		st.setId(rs.getInt(Fields.ENTITY_ID));
		st.setSt_id(rs.getInt(Fields.SERVICETYPE_ID));
		st.setSt_info(rs.getString(Fields.SERVICETYPE_INFO));
		st.setSt_price(rs.getInt(Fields.SERVICETYPE_PRICE));
		return st;
	}

	private FullServiceBean extractFullService(ResultSet rs) throws SQLException {
		FullServiceBean fsb = new FullServiceBean();
		fsb.setId(rs.getInt(Fields.ENTITY_ID));
		fsb.setsName(rs.getString(Fields.FULLSERVICEBEAN_NAME));
		fsb.setStId(rs.getInt(Fields.FULLSERVICEBEAN_ST_ID));
		fsb.setStInfo(rs.getString(Fields.FULLSERVICEBEAN_INFO));
		fsb.setPrice(rs.getInt(Fields.FULLSERVICEBEAN_PRICE));
		return fsb;
	}

	// Adding random information just to feel the empty space

	public void addRandomUsersInformation() throws DBException {
		LOG.info("Adding random information to DB begins");
		Connection con = null;

		try {
			con = getConnection();
			con.setAutoCommit(false);
			con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			PreparedStatement psmt = null;
			// add new users
			for (int i = 0; i < 50; i++) {
				try {
					int k = 1;
					psmt = con.prepareStatement(SQLRequests.SQL_INSERT_USER);
					psmt.setString(k++, NameGenerator.generateName());
					psmt.setString(k++, NameGenerator.generateName());
					psmt.setString(k++, NameGenerator.generatePassword());
					psmt.setString(k++, NameGenerator.generateName());
					psmt.setBoolean(k++, NameGenerator.generateStatus());
					psmt.setInt(k++, NameGenerator.generateBallance());
					psmt.setString(k++, NameGenerator.generateLocale());
					psmt.setInt(k++, NameGenerator.generateRole());
					psmt.executeUpdate();
				} catch (Exception e) {
					LOG.trace("Something wrong during generating new user to DB");
					rollback(con);
					throw new DBException();
				} finally {
					con.commit();
					close(psmt);
				}
			}
			close(con);
		} catch (Exception e) {
			throw new DBException("Cannot set up connection to DB ", e);
		}
		LOG.info("Adding random information to DB succesfully finished");
	}

	public void addRandomOrdersInformation() throws DBException {
		LOG.info("Adding random information to DB begins");
		Connection con = null;

		try {
			con = getConnection();
			con.setAutoCommit(false);
			con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			PreparedStatement psmt = null;
			try {
				// add new orders
				for (int i = 0; i < 50; i++) {
					int k = 1;
					psmt = con.prepareStatement(SQLRequests.SQL_INSERT_ORDER);
					k = 1;
					psmt.setInt(k++, NameGenerator.generateRandomTill(10 + i));
					psmt.setInt(k++, NameGenerator.generateRandomTill(8));
					psmt.setDate(k++, NameGenerator.generateDate());
					psmt.setBoolean(k++, NameGenerator.generateStatus());
					psmt.executeUpdate();
				}
			} catch (Exception e) {
				LOG.trace("Something wrong during generating new order to DB");
				rollback(con);
				throw new DBException();
			} finally {
				con.commit();
				close(psmt);
			}
			close(con);
		} catch (Exception e) {
			throw new DBException("Cannot set up connection to DB ", e);
		}
		LOG.info("Adding random information to DB succesfully finished");
	}

	public int getQuantityOfOrder(int stid) throws DBException {
		Connection con = null;
		int quantity = 0;
		try {
			con = getConnection();
			PreparedStatement psmt = null;
			try {
				psmt = con.prepareStatement(SQLRequests.SQL_SELECT_COUNT_FROM_ORDERS_BY_ID);
				psmt.setInt(1, stid);
				ResultSet rs = psmt.executeQuery();
				rs.next();
				quantity = (int) rs.getInt(1);
					System.out.println("QUAN = " + quantity);
			} catch (Exception e) {
				LOG.trace("Something wrong during calculating quantity of orders in DB");
				throw new DBException();
			} finally {
				con.commit();
				close(psmt);
			}
		} catch (Exception e) {
			rollback(con);
			throw new DBException("Cannot set up connection to DB ", e);
		}
		return quantity;
	}

}