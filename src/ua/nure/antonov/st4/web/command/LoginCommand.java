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
import javax.servlet.jsp.jstl.core.Config;

/**
 * Login command.
 */
public class LoginCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException, AppException {
		LOG.debug("Login Command starts");

		HttpSession session = request.getSession();

		// obtain login and password from a request
		DBManager manager = DBManager.getInstance();
		String login = request.getParameter("login");
		LOG.trace("Request parameter: login --> " + login);

		String password = request.getParameter("password");
		if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
			throw new AppException("Login/password cannot be empty");
		}

		User user = manager.findUserByLogin(login);
		LOG.trace("Found in DB: user --> " + user);

		if (user == null || !password.equals(user.getPassword())) {
			throw new AppException("Cannot find user with such login/password");
		}
		
		Role userRole = Role.getRole(user);
		LOG.trace("userRole --> " + userRole);
		
		if (userRole == Role.USER & !user.isStatus()) {
			throw new AppException("Your account is blocked, possible because of low balance during purchase");
		} 

		// if it is not user and not admin
		String forward = Path.PAGE_ERROR_PAGE;

		if (userRole == Role.ADMIN) {
			forward = Path.ADMIN_PAGE;
		}

		if (userRole == Role.USER) {
			forward = Path.USER_PAGE;
		}

		String userLocaleName = user.getLocale();
		LOG.trace("userLocaleName --> " + userLocaleName);
		
		if (userLocaleName != null && !userLocaleName.isEmpty()) {
			Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", userLocaleName);
			session.setAttribute("locale", userLocaleName);
			session.setAttribute("defaultLocale", userLocaleName);
			LOG.trace("Set the session attribute: defaultLocaleName --> " + userLocaleName);
			
		}
		
		session.setAttribute("user", user);
		LOG.trace("Set the session attribute: user --> " + user);

		session.setAttribute("userRole", userRole);
		LOG.trace("Set the session attribute: userRole --> " + userRole);

		LOG.info("User " + user + " logged as " + userRole.toString().toLowerCase());

		LOG.debug("Login Command finished");
		return forward;
	}
}