package ua.nure.antonov.st4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.antonov.st4.db.DBManager;
import ua.nure.antonov.st4.db.entity.User;
import ua.nure.antonov.st4.exception.AppException;
import ua.nure.antonov.st4.exception.DBException;
import ua.nure.antonov.st4.path.Path;

/**
 * Recharge command.
 */
public class UserRechargeCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = Logger.getLogger(UserRechargeCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
		LOG.debug("Settings Command starts");

		DBManager manager = DBManager.getInstance();

		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");
		
		LOG.trace("Request user from session for update --> " + user);

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

		user.setMoney(user.getMoney() + upgradeInt);

		try {
			manager.updateUserFromRecharge(user);
			LOG.trace("Recharging of user with ID " + user.getId() + "for " + upgradeInt + " have succeed ");
		} catch (DBException e) {
			e.printStackTrace();
		}

		session.setAttribute("user", user);
		session.setAttribute("userForRecharge", user);
		
		LOG.debug("Settings Command finished");
		return Path.PAGE_RECHARGE;
	}
}