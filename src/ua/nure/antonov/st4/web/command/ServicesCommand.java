package ua.nure.antonov.st4.web.command;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.antonov.st4.db.DBManager;
import ua.nure.antonov.st4.db.entity.FullServiceBean;
import ua.nure.antonov.st4.exception.AppException;
import ua.nure.antonov.st4.path.Path;

public class ServicesCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -598650620781698854L;

	private static final Logger LOG = Logger.getLogger(OrdersCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {

		LOG.debug("Services Command starts");

		// get list of full services from DB or from session if it is still there

		HttpSession session = request.getSession();

		DBManager manager = DBManager.getInstance();

		List<FullServiceBean> fullServicesList = (List<FullServiceBean>) session.getAttribute("fullServices");

		if (session.getAttribute("fullServices") == null) {

			fullServicesList = manager.findAllFullServices();

			LOG.trace("Found in DB: list of full services --> " + fullServicesList);

		}
		System.out.println(" fullServicesList = " + fullServicesList);
		
//		int [] ordersQuantity = new int [fullServicesList.size()+1];
//		for (int i = 0; i < fullServicesList.size(); i++) {
//			ordersQuantity[i] = 0;
//			System.out.println(" PUT " + i + " = " + 0);
//		}
//
//		System.out.println("LEN = " + fullServicesList.size());
//		
//		for (FullServiceBean fullServiceBean : fullServicesList) {
//			ordersQuantity[fullServiceBean.getStId()] = manager.getQuantityOfOrder(fullServiceBean.getStId());
//			System.out.println("PUUUT " + manager.getQuantityOfOrder(fullServiceBean.getStId()) + " for " + fullServiceBean.getStId());
//		}
//		
//		System.out.println(" fullServicesList = " + fullServicesList);
//		System.out.println("DONE");
//		session.setAttribute("ordersQuantity", ordersQuantity);
		
		int [] arr = new int [fullServicesList.size()];
		for (int i = 0; i < 10; i++) {
			int ans = manager.getQuantityOfOrder(i);
			System.out.println("for " + i + " === " + ans);
		}
		
		System.out.println(arr);
		
		// sort orders, by default = id

		String sortingFullServicesFromPage = request.getParameter("fullServicesSorting");
		String sortingFullServicesFromSession = (String) session.getAttribute("fullServicesSortingInSession");

		if (sortingFullServicesFromPage != null) {
			if (sortingFullServicesFromPage.intern() == sortingFullServicesFromSession.intern()) {
				sortingFullServicesFromPage = sortingFullServicesFromPage + "Back";
			}
		} else {
			sortingFullServicesFromPage = "id";
		}

		switch (sortingFullServicesFromPage) {
		case "id":
			Collections.sort(fullServicesList, (o1, o2) -> o1.getId() - o2.getId());
			break;
		case "idBack":
			Collections.sort(fullServicesList, (o1, o2) -> o2.getId() - o1.getId());
			break;
		case "name":
			Collections.sort(fullServicesList,
					(o1, o2) -> o1.getsName().toLowerCase().compareTo(o2.getsName().toLowerCase()));
			break;
		case "nameBack":
			Collections.sort(fullServicesList,
					(o1, o2) -> o2.getsName().toLowerCase().compareTo(o1.getsName().toLowerCase()));
			break;
		case "stId":
			Collections.sort(fullServicesList, (o1, o2) -> o1.getStId() - o2.getStId());
			break;
		case "stIdBack":
			Collections.sort(fullServicesList, (o1, o2) -> o2.getStId() - o1.getStId());
			break;
		case "info":
			Collections.sort(fullServicesList,
					(o1, o2) -> o1.getStInfo().toLowerCase().compareTo(o2.getStInfo().toLowerCase()));
			break;
		case "infoBack":
			Collections.sort(fullServicesList,
					(o1, o2) -> o2.getStInfo().toLowerCase().compareTo(o1.getStInfo().toLowerCase()));
			break;
		case "price":
			Collections.sort(fullServicesList, (o1, o2) -> o1.getPrice() - o2.getPrice());
			break;
		case "priceBack":
			Collections.sort(fullServicesList, (o1, o2) -> o2.getPrice() - o1.getPrice());
			break;
		default:
			Collections.sort(fullServicesList, (o1, o2) -> o1.getId() - o2.getId());
			break;
		}
		LOG.trace("Sorting fullServices is set to = " + sortingFullServicesFromPage);

		// put sorting order in session

		session.setAttribute("fullServicesSortingInSession", sortingFullServicesFromPage);

		// put list of orders in request

		session.setAttribute("fullServices", fullServicesList);

		LOG.trace("Set the request attribute: allOrders --> " + fullServicesList);

		LOG.debug("Services Command finished");
		return Path.PAGE_SERVICES;
	}

}
