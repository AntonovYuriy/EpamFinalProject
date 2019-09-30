package ua.nure.antonov.st4.web.command;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.antonov.st4.path.Path;
import ua.nure.antonov.st4.db.DBManager;
import ua.nure.antonov.st4.db.entity.Order;
import ua.nure.antonov.st4.db.entity.Role;
import ua.nure.antonov.st4.db.entity.User;
import ua.nure.antonov.st4.exception.AppException;

/**
 * Showing orders All for admin Personal for user.
 */

public class OrdersCommand extends Command {

	private static final long serialVersionUID = 7732286214029478505L;

	private static final Logger LOG = Logger.getLogger(OrdersCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {

		LOG.debug("Command starts");

		// get list of orders from DB

		HttpSession session = request.getSession();

		List<Order> allOrders = (List<Order>) session.getAttribute("allorders");

		if (request.getParameter("allOrders") == null) {

			Role userRole = (Role) session.getAttribute("userRole");
			User user = (User) session.getAttribute("user");

			Boolean onlyMyOrders = Boolean.parseBoolean((String) request.getParameter("onlyMyOrders"));

			if (userRole == Role.ADMIN && !onlyMyOrders) {
				allOrders = DBManager.getInstance().findAllOrders();
			}

			if (userRole == Role.USER || onlyMyOrders) {
				allOrders = DBManager.getInstance().findMyAllOrders(user);
			}

			LOG.trace("Found in DB: list of orders --> " + allOrders + " by role = " + userRole.getName());
		} 

		// sort orders, by default = id

		String sortingOrderFromPage = (String) request.getParameter("orderSorting");
		if (sortingOrderFromPage == null) {
			if (request.getParameter("orderSortingInSession") != null) {
				sortingOrderFromPage = (String) session.getAttribute("orderSortingInSession");
			} else {
				sortingOrderFromPage = "id";
			}
		}

		switch (sortingOrderFromPage) {
		case "id":
			Collections.sort(allOrders, new Comparator<Order>() {
				public int compare(Order o1, Order o2) {
					return (int) (o1.getId() - o2.getId());
				}
			});
			break;
		case "idBack":
			Collections.sort(allOrders, new Comparator<Order>() {
				public int compare(Order o1, Order o2) {
					return (int) (o2.getId() - o1.getId());
				}
			});
			break;
		case "userId":
			Collections.sort(allOrders, new Comparator<Order>() {
				public int compare(Order o1, Order o2) {
					return (int) (o1.getOrder_user_id() - o2.getOrder_user_id());
				}
			});
			break;
		case "userIdBack":
			Collections.sort(allOrders, new Comparator<Order>() {
				public int compare(Order o1, Order o2) {
					return (int) (o2.getOrder_user_id() - o1.getOrder_user_id());
				}
			});
			break;
		case "serviceType":
			Collections.sort(allOrders, new Comparator<Order>() {
				public int compare(Order o1, Order o2) {
					return (int) (o1.getOrder_serviceType_id() - o2.getOrder_serviceType_id());
				}
			});
			break;
		case "serviceTypeBack":
			Collections.sort(allOrders, new Comparator<Order>() {
				public int compare(Order o1, Order o2) {
					return (int) (o2.getOrder_serviceType_id() - o1.getOrder_serviceType_id());
				}
			});
			break;
		case "date":
			Collections.sort(allOrders, new Comparator<Order>() {
				public int compare(Order o1, Order o2) {
					return (int) (o1.getOrder_date().compareTo(o2.getOrder_date()));
				}
			});
			break;
		case "dateBack":
			Collections.sort(allOrders, new Comparator<Order>() {
				public int compare(Order o1, Order o2) {
					return (int) (o2.getOrder_date().compareTo(o1.getOrder_date()));
				}
			});
			break;
		case "paid":
			Collections.sort(allOrders, new Comparator<Order>() {
				public int compare(Order o1, Order o2) {
					return (int) (((Boolean) o1.isOrder_status()).compareTo((Boolean) o2.isOrder_status()));
				}
			});
			break;
		case "paidBack":
			Collections.sort(allOrders, new Comparator<Order>() {
				public int compare(Order o1, Order o2) {
					return (int) (((Boolean) o2.isOrder_status()).compareTo((Boolean) o1.isOrder_status()));
				}
			});
			break;
		}

		LOG.trace("Sorting order is set to = " + sortingOrderFromPage);

		// put sorting order in session
		request.setAttribute("orderSorting", sortingOrderFromPage);
		session.setAttribute("orderSortingInSession", sortingOrderFromPage);

		// put list of orders in request
		request.setAttribute("allOrders", allOrders);
		LOG.trace("Set the request attribute: allOrders --> " + allOrders);

		LOG.debug("Command finished");
		return Path.PAGE_ORDERS;
	}

}