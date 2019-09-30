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
 * Recharge command.
 */
public class AllRechargeCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = Logger.getLogger(AllRechargeCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
		LOG.debug("Settings Command starts");

		HttpSession session = request.getSession();
		Role userRole = (Role) session.getAttribute("userRole");

		if (userRole != Role.ADMIN) {
			throw new AppException("Only administrator is able to recharge user accounts");
		}
		
		DBManager manager = DBManager.getInstance();
		User theUserForRecharging = new User();

		User user = (User) session.getAttribute("user");
		LOG.trace("Request user from settings for update --> " + user);

		if (Role.getRole(user) == Role.USER) {
			theUserForRecharging = user;
		}

		if (Role.getRole(user) == Role.ADMIN) {

			// if userForRechargeId not in request and not in session - set it to user ID

			int theUserForRechargingId;

			if (request.getParameter("userForRechargeId") != null) {
				theUserForRechargingId = Integer.parseInt(request.getParameter("userForRechargeId"));
				theUserForRecharging = manager.findUserByID(theUserForRechargingId);
			} else {
				if (session.getAttribute("userForRecharge") != null) {
					theUserForRecharging = (User) session.getAttribute("userForRecharge");
				} else {
					theUserForRecharging = user;
				}
			}
		}

		LOG.trace("Found user for recharge --> " + theUserForRecharging);

		String upgradeString;
		upgradeString = request.getParameter("recharge");

		LOG.trace("Request parameters recharge --> " + upgradeString);

		int upgradeInt;

		if (upgradeString == null || upgradeString.isEmpty()) {
			upgradeString = "0";
			LOG.trace("Replase recharge value to default if needed --> " + upgradeString);
		}

		try {
			upgradeInt = Integer.parseInt(upgradeString);
		} catch (Exception e) {
			throw new AppException("Wrong value of recharging", e);
		}

		if (upgradeInt < 0 || upgradeInt > 1000) {
			upgradeInt = 0;
			throw new AppException("Value of recharging must be between 0 and 1000");
		}

		theUserForRecharging.setMoney(theUserForRecharging.getMoney() + upgradeInt);

		try {
			manager.updateUserFromRecharge(theUserForRecharging);
			if (!theUserForRecharging.isStatus()) {
				manager.UnBlockUser(theUserForRecharging);
			LOG.info("The user with id " + theUserForRecharging.getId() + "has been unblocked");	
			
			}
			LOG.trace("Recharging of user with ID " + theUserForRecharging.getId() + " for " + upgradeInt
					+ " have succeed ");
		} catch (DBException e) {
			e.printStackTrace();
		}

		request.setAttribute("userForRecharge", theUserForRecharging);
		session.setAttribute("userForRecharge", theUserForRecharging);

		LOG.debug("All Settings Command finished");
		return Path.PAGE_RECHARGE;
	}
}