package ua.nure.antonov.st4.web.command;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.antonov.st4.path.Path;
import ua.nure.antonov.st4.db.DBManager;
import ua.nure.antonov.st4.db.entity.Role;
import ua.nure.antonov.st4.db.entity.User;
import ua.nure.antonov.st4.exception.AppException;

/**
 * Showing all users for admin.
 */
public class AllUsersCommand extends Command {

	private static final long serialVersionUID = 7732286214029478505L;

	private static final Logger LOG = Logger.getLogger(AllUsersCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {

		LOG.debug("Command starts");

		HttpSession session = request.getSession();
		Role userRole = (Role) session.getAttribute("userRole");

		if (userRole != Role.ADMIN) {
			throw new AppException("Only administrator is able to watch users information!");
		}

		DBManager manager = DBManager.getInstance();
		List<User> allUsers = manager.findAllUsers();

		LOG.trace("Found in DB: list of orders --> " + allUsers + " by role = " + userRole.getName());

		// sort orders by request

		String sortingOrder = request.getParameter("userSorting");
		String sortingOrderFromSession = (String) session.getAttribute("userSortingInSession");

		if (sortingOrder != null) {
			if (sortingOrder.intern() == sortingOrderFromSession.intern()) {
				sortingOrder = sortingOrder + "Back";
			}
		} else {
			sortingOrder = "id";
		}

		LOG.info("Sorting order for users is set to " + sortingOrder);

		switch (sortingOrder) {
		case "id":
			Collections.sort(allUsers, (o1, o2) -> o1.getId() - o2.getId());
			break;
		case "idBack":
			Collections.sort(allUsers, (o1, o2) -> o2.getId() - o1.getId());
			break;
		case "login":
			Collections.sort(allUsers, (o1, o2) -> o1.getLogin().toLowerCase().compareTo(o2.getLogin().toLowerCase()));
			break;
		case "loginBack":
			Collections.sort(allUsers, (o1, o2) -> o2.getLogin().toLowerCase().compareTo(o1.getLogin().toLowerCase()));
			break;
		case "firstName":
			Collections.sort(allUsers,
					(o1, o2) -> o1.getFirstName().toLowerCase().compareTo(o2.getFirstName().toLowerCase()));
			break;
		case "firstNameBack":
			Collections.sort(allUsers,
					(o1, o2) -> o2.getFirstName().toLowerCase().compareTo(o1.getFirstName().toLowerCase()));
			break;
		case "lastName":
			Collections.sort(allUsers,
					(o1, o2) -> o1.getLastName().toLowerCase().compareTo(o2.getLastName().toLowerCase()));
			break;
		case "lastNameBack":
			Collections.sort(allUsers,
					(o1, o2) -> o2.getLastName().toLowerCase().compareTo(o1.getLastName().toLowerCase()));
			break;
		case "status":
			Collections.sort(allUsers, (o1, o2) -> ((Boolean) o1.isStatus()).compareTo((Boolean) o2.isStatus()));
			break;
		case "statusBack":
			Collections.sort(allUsers, (o1, o2) -> ((Boolean) o2.isStatus()).compareTo((Boolean) o1.isStatus()));
			break;
		case "ballance":
			Collections.sort(allUsers, (o1, o2) -> o1.getMoney() - o2.getMoney());
			break;
		case "ballanceBack":
			Collections.sort(allUsers, (o1, o2) -> o2.getMoney() - o1.getMoney());
			break;
		case "locale":
			Collections.sort(allUsers,
					(o1, o2) -> o1.getLocale().toLowerCase().compareTo(o2.getLocale().toLowerCase()));
			break;
		case "localeBack":
			Collections.sort(allUsers,
					(o1, o2) -> o2.getLocale().toLowerCase().compareTo(o1.getLocale().toLowerCase()));
			break;
		case "role":
			Collections.sort(allUsers, (o1, o2) -> o1.getRoleId() - o2.getRoleId());
			break;
		case "roleBack":
			Collections.sort(allUsers, (o1, o2) -> o2.getRoleId() - o1.getRoleId());
			break;
		default:
			Collections.sort(allUsers, (o1, o2) -> o1.getId() - o2.getId());
			break;
		}

		// put menu items list to the request

		session.setAttribute("userSortingInSession", sortingOrder);
		request.setAttribute("allUsers", allUsers);

		LOG.trace("Set the request attribute: allUserrs --> " + allUsers);

		LOG.debug("Command finished");
		return Path.PAGE_ADMIN_All_USERS;
	}

}