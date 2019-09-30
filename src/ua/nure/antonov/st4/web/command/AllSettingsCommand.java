package ua.nure.antonov.st4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.antonov.st4.db.DBManager;
import ua.nure.antonov.st4.db.entity.Role;
import ua.nure.antonov.st4.db.entity.User;
import ua.nure.antonov.st4.exception.AppException;
import ua.nure.antonov.st4.exception.DBException;
import ua.nure.antonov.st4.path.Path;

/**
 * Settings command.
 */
public class AllSettingsCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = Logger.getLogger(AllSettingsCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
		
		LOG.debug("All Settings Command starts");

		HttpSession session = request.getSession();
		Role userRole = (Role) session.getAttribute("userRole");

		if (userRole != Role.ADMIN) {
			throw new AppException("Only administrator is able to watch services information!");
		}
		
		DBManager manager = DBManager.getInstance();
		User theUserForUpdate = new User();
		
		User user = (User) session.getAttribute("user");
		LOG.trace("Request user from settings for update --> " + user);

		int theUserForUpdateId;

		if (request.getParameter("userForUpdateId") != null) {
			theUserForUpdateId = Integer.parseInt(request.getParameter("userForUpdateId"));
			theUserForUpdate = manager.findUserByID(theUserForUpdateId);
		} else {
			if (session.getAttribute("userForUpdate") != null) {
				theUserForUpdate = (User) session.getAttribute("userForUpdate");
			} else {
				theUserForUpdate = user;
			}
		}
		
		LOG.trace("Found user for update --> " + theUserForUpdate);
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String locale = request.getParameter("locale");

		LOG.trace("Request parameters from settings for update : firstName/lastName/locale --> " + firstName + "/"
				+ lastName + "/" + locale);

		if (firstName == null || firstName.isEmpty()) {
			firstName = theUserForUpdate.getFirstName();
		}
		if (lastName == null || lastName.isEmpty()) {
			lastName = theUserForUpdate.getLastName();
		}
		if (locale == null || locale.isEmpty() || !"ru".equals(locale.toLowerCase())) {
			locale = "en";
		}

		LOG.trace("Set parameters to default if needed : firstName/lastName/locale --> " + firstName + "/" + lastName
				+ "/" + locale);

		theUserForUpdate.setFirstName(firstName);
		theUserForUpdate.setLastName(lastName);
		theUserForUpdate.setLocale(locale);

		try {
			manager.updateUserFromSettings(theUserForUpdate);
		} catch (DBException e) {
			e.printStackTrace();
		}
		
		session.setAttribute("locale", locale);
		session.setAttribute("userForUpdate", theUserForUpdate);
		
		LOG.debug("All Settings Command finished");
		return Path.PAGE_SETTINGS;
	}

}