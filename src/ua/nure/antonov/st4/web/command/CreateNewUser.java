package ua.nure.antonov.st4.web.command;

import java.io.IOException;

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
 * Ñreate new user ony by admin.
 */
public class CreateNewUser extends Command {

	private static final long serialVersionUID = 7732286214029478505L;

	private static final Logger LOG = Logger.getLogger(CreateNewUser.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {

		LOG.debug("Command starts");

		HttpSession session = request.getSession();
		Role userRole = (Role) session.getAttribute("userRole");

		if (userRole != Role.ADMIN) {
			throw new AppException("Only administrator is able to create new user!");
		}

		DBManager manager = DBManager.getInstance();

		User newUser = new User();

		try {
			if (request.getParameter("tempUserLogin") != null) {
				newUser.setLogin((String) request.getParameter("tempUserLogin"));
				newUser.setPassword((String) request.getParameter("tempUserPassword"));
				newUser.setFirstName((String) request.getParameter("tempUserFirstName"));
				newUser.setLastName((String) request.getParameter("tempUserFirstName"));
				newUser.setStatus(Boolean.parseBoolean((String) request.getParameter("tempUserStatus")));
				if (request.getParameter("tempUserMoney") == null) {
					newUser.setMoney(0);
				} else {
					newUser.setMoney(Integer.parseInt((String) request.getParameter("tempUserMoney")));
				}
				if ((newUser.getMoney() < 0) || (newUser.getMoney() > 1000)) {
					throw new AppException("Account cannot be less than 0 and higher than 1000");
				}
				newUser.setLocale((String) ("ru".equals(request.getParameter("tempUserLocale")) ? "ru" : "en"));
				if (request.getParameter("tempUserRole") == null) {
					newUser.setRoleId(0);
				} else {
					newUser.setRoleId(Integer.parseInt((String) request.getParameter("tempUserRole")));
				}
				
				LOG.trace("New User has been succesfully accepted from form page --> " + newUser);

				User newUpdatedUser = newUser;

				if (manager.createNewUser(newUser)) {
					newUpdatedUser = manager.findUserByLogin(newUser.getLogin());

					LOG.trace("New user has been succesfully created " + newUpdatedUser);

					request.setAttribute("newUpdatedUser", newUpdatedUser);

					LOG.trace("Set the request attribute: newUpdatedUser --> " + newUpdatedUser);
				}
			}

		} catch (Exception e) {
			throw new AppException("You have made error during setting data of new user");
		}

		LOG.debug("Command finished");

		return Path.PAGE_ADMIN_CREATE_NEW_USER;
	}

}