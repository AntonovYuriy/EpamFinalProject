package ua.nure.antonov.st4.web.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.antonov.st4.db.DBManager;
import ua.nure.antonov.st4.db.entity.FullServiceBean;
import ua.nure.antonov.st4.db.entity.Role;
import ua.nure.antonov.st4.db.entity.Service;
import ua.nure.antonov.st4.exception.AppException;
import ua.nure.antonov.st4.path.Path;

public class CreateNewServiceCommand extends Command {

	/**
	 * Create New Service Command
	 */

	private static final long serialVersionUID = -4443193538337838405L;

	private static final Logger LOG = Logger.getLogger(CreateNewServiceCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

		LOG.debug("CreateNewServiceCommand Command starts");

		HttpSession session = request.getSession();
		Role userRole = (Role) session.getAttribute("userRole");

		if (userRole != Role.ADMIN) {
			throw new AppException("Only administrator is able to delete services");
		}

		DBManager manager = DBManager.getInstance();

		List<Service> allServicesList = manager.findAllServices();

		request.setAttribute("allServicesList", allServicesList);

		String sInfo = (String) request.getParameter("sInfo");
		String sPrice = (String) request.getParameter("sPrice");

		if ((sInfo != null) && (!sInfo.isEmpty()) && (sPrice != null) && (!sPrice.isEmpty())) {

			int sPriceInt;
			int stId;
			try {
				stId = Integer.parseInt((String) request.getParameter("stId"));
				sPriceInt = Integer.parseInt(sPrice);
			} catch (Exception e) {
				throw new AppException("Price shold be digit");
			}

			manager.createNewServiceType(stId, sInfo, sPrice);

			request.setAttribute("createHasBeenDone", "true");

			List<FullServiceBean> fullServicesList = DBManager.getInstance().findAllFullServices();

			session.setAttribute("fullServices", fullServicesList);
		}

		LOG.debug("CreateNewServiceCommand Command finished");

		return Path.ADMIN_CREATE_NEW_SERVICE;
	}
}
