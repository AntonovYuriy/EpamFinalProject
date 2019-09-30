package ua.nure.antonov.st4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.antonov.st4.db.DBManager;
import ua.nure.antonov.st4.db.entity.User;
import ua.nure.antonov.st4.exception.DBException;
import ua.nure.antonov.st4.path.Path;
import javax.servlet.jsp.jstl.core.Config;

/**
 * Settings command.
 */
public class UserSettingsCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = Logger.getLogger(UserSettingsCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {
		LOG.debug("Settings Command starts");

		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");
		LOG.trace("Request user from settings for update --> " + user);

		// Get parameters for changing in settings
		DBManager manager = DBManager.getInstance();

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String locale = request.getParameter("locale");

		LOG.trace("Request parameters from settings for update : firstName/lastName/locale --> " + firstName + "/"
				+ lastName + "/" + locale);

		if (firstName == null || firstName.isEmpty()) {
			firstName = user.getFirstName();
		}
		if (lastName == null || lastName.isEmpty()) {
			lastName = user.getLastName();
		}

		if (locale == null || locale.isEmpty()) {
			locale = user.getLocale();
		}

		if (locale != null && !locale.isEmpty()) {
			Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", locale);			
			session.setAttribute("locale", locale);
		}
		
		LOG.trace("Set parameters to default if needed : firstName/lastName/locale --> " + firstName + "/" + lastName
				+ "/" + locale);

		
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setLocale(locale);

		try {
			manager.updateUserFromSettings(user);
			user = manager.findUserByID(user.getId());
			session.setAttribute("user", user);
		} catch (DBException e) {
			e.printStackTrace();
		}
		
		session.setAttribute("user", user);
		session.setAttribute("userForUpdate", user);
		LOG.debug("Settings Command finished");
		return Path.PAGE_SETTINGS;
	}
}