package ua.nure.antonov.st4.web.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.antonov.st4.db.DBManager;
import ua.nure.antonov.st4.db.entity.FullServiceBean;
import ua.nure.antonov.st4.db.entity.Role;
import ua.nure.antonov.st4.exception.AppException;
import ua.nure.antonov.st4.path.Path;

public class DeleteServiceCommand extends Command {

	/**
	 * Delete service Command
	 */
	private static final long serialVersionUID = -4443193538337838405L;

	private static final Logger LOG = Logger.getLogger(CreateNewUser.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
		
		LOG.debug("DeleteService Command starts");
		
		
		HttpSession session = request.getSession();
		Role userRole = (Role) session.getAttribute("userRole");

		if (userRole != Role.ADMIN) {
			throw new AppException("Only administrator is able to delete services");
		}
		
		request.setAttribute("sId", (String) request.getParameter("sId"));
		request.setAttribute("sName", (String) request.getParameter("sName"));
		request.setAttribute("sInfo", (String) request.getParameter("sInfo"));
		
		String confirm = (String) request.getParameter("deleteServiceConfirm");
		
		if ("true".equals(confirm)) {
			
			int id = Integer.parseInt( (String) request.getParameter("sId"));
			
			DBManager manager = DBManager.getInstance();
			
			manager.deleteServiceWithId(id);
			
			request.setAttribute("deleteHasBeenDone", "true");
			
			List<FullServiceBean> fullServicesList = DBManager.getInstance().findAllFullServices();
			
			session.setAttribute("fullServices", fullServicesList);
		}
		
		LOG.debug("DeleteService Command starts");
		
		return Path.ADMIN_DELETE_SERVICE;
	}
}
