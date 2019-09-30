package ua.nure.antonov.st4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.antonov.st4.db.DBManager;
import ua.nure.antonov.st4.db.entity.FullServiceBean;
import ua.nure.antonov.st4.db.entity.User;
import ua.nure.antonov.st4.exception.AppException;
import ua.nure.antonov.st4.path.Path;

public class PurchaseServiceCommand extends Command {

	/**
	 * Purchasing service
	 */

	private static final long serialVersionUID = -8544723165106153948L;

	private static final Logger LOG = Logger.getLogger(PurchaseServiceCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {

		LOG.debug("PurchaseServiceCommand Command starts");

		HttpSession session = request.getSession();

		DBManager manager = DBManager.getInstance();

		User user = (User) session.getAttribute("user");

		request.setAttribute("userMoney", user.getMoney());

		if (request.getParameter("fullServiceForPurchaseId") != null) {

			int fsPurchadesId = Integer.valueOf((String) request.getParameter("fullServiceForPurchaseId"));

			FullServiceBean fsPurchased = manager.findFullServiceBeanById(fsPurchadesId);

			request.setAttribute("fullServiceForPurchaseId", fsPurchased.getId());
			request.setAttribute("fullServiceForPurchaseName", fsPurchased.getsName());
			request.setAttribute("fullServiceForPurchaseStId", fsPurchased.getStId());
			request.setAttribute("fullServiceForPurchaseStInfo", fsPurchased.getStInfo());
			request.setAttribute("fullServiceForPurchasePrice", fsPurchased.getPrice());

			LOG.info("Asking for order confirmation");
		}

		if (request.getParameter("fullServiceForPurchaseIdConfirmed") != null) {

			int fsPurchadesId = Integer.valueOf((String) request.getParameter("fullServiceForPurchaseIdConfirmed"));

			FullServiceBean fsPurchased = manager.findFullServiceBeanById(fsPurchadesId);

			if (user.getMoney() < fsPurchased.getPrice()) {
				manager.blockUser(user);
				LOG.info("The user with id " + user.getId() + "has been blocked");
				session.invalidate();
				throw new AppException(
						"Your account has been blocked because of trying to purchase too expencive servise");
			}

			manager.makeOrder(fsPurchased, user);

			user = manager.findUserByID(user.getId());

			session.setAttribute("user", user);

			request.setAttribute("orderConfirmation", true);

			LOG.info("The order has been sucesfully made");

		}

		LOG.debug("PurchaseServiceCommand Command finished");

		return Path.PAGE_PURCHASE_SERVICE;
	}

}
