package ua.nure.antonov.st4.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.antonov.st4.db.DBManager;
import ua.nure.antonov.st4.db.entity.FullServiceBean;
import ua.nure.antonov.st4.exception.AppException;
import ua.nure.antonov.st4.path.Path;

public class EditServiceCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = Logger.getLogger(EditServiceCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException, IOException {
		LOG.debug("Edit service Command starts");

		request.setAttribute("sId", (String) request.getParameter("sId"));
		request.setAttribute("stId", (String) request.getParameter("stId"));
		request.setAttribute("sName", (String) request.getParameter("sName"));
		request.setAttribute("sPrice", (String) request.getParameter("sPrice"));
		request.setAttribute("sInfo", (String) request.getParameter("sInfo"));
		
		String confirm = (String) request.getParameter("editServiceConfirm");
		
		if ("true".equals(confirm)) {
			
			int id = Integer.parseInt( (String) request.getParameter("sId"));
			
			DBManager manager = DBManager.getInstance();
			
			FullServiceBean fsb = manager.findFullServiceBeanById(id);
			
			fsb.setsName( (String) request.getParameter("sName"));
			fsb.setPrice(Integer.parseInt(((String) request.getParameter("sPrice"))));
			fsb.setStInfo( (String) request.getParameter("sInfo"));
			
			manager.updateServiceTypefromFullServiceBean(fsb);
			
			request.setAttribute("editHasBeenDone", "true");
			
			List<FullServiceBean> fullServicesList = DBManager.getInstance().findAllFullServices();
			
			HttpSession session = request.getSession();
			
			session.setAttribute("fullServices", fullServicesList);
		}
		
		
		LOG.debug("Edit service Command finished");
		return Path.PAGE_EDIT_SERVICE;
	}
}