package ua.nure.antonov.st4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.antonov.st4.db.DBManager;
import ua.nure.antonov.st4.db.entity.Role;
import ua.nure.antonov.st4.db.entity.User;
import ua.nure.antonov.st4.exception.AppException;
import ua.nure.antonov.st4.path.Path;

/**
 * Recharge command.
 */

public class ChangePasswordCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = Logger.getLogger(ChangePasswordCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

		LOG.debug("ChangePassword Command starts");

		HttpSession session = request.getSession();

		Role userRole = (Role) session.getAttribute("userRole");

		if (userRole != Role.ADMIN) {
			throw new AppException("Only administrator is able to change users passwords");
		}

		if (request.getParameter("userForPasswordChangeId") != null) {
			session.setAttribute("userForPasswordChangeIdConfirmed", (String) request.getParameter("userForPasswordChangeId"));
		}


		if ((request.getParameter("newPassword") != null)
				&& (session.getAttribute("userForPasswordChangeIdConfirmed") != null)) {

			DBManager manager = DBManager.getInstance();

			String pass = (String) request.getParameter("newPassword");
			
			if (pass.length()>10) {
				throw new AppException("The password has to bo no longer than 10 symbols");
			}
			int userForPasswordChangeId = Integer.parseInt((String) session.getAttribute("userForPasswordChangeIdConfirmed"));

			User userForPasswordChange = manager.findUserByID(userForPasswordChangeId);

			manager.setNewPasswordForUser(userForPasswordChange, pass);
			
			session.setAttribute("userForPasswordChangeIdConfirmed", null);
			session.setAttribute("allUsers", null);
			
			request.setAttribute("changePasswordConfirm", "done");
		}

		LOG.debug("ChangePassword Command finished");

		return Path.PAGE_CHANGE_PASSWORD;
	}
}