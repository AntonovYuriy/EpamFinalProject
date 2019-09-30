package ua.nure.antonov.st4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.antonov.st4.db.DBManager;
import ua.nure.antonov.st4.exception.AppException;
import ua.nure.antonov.st4.path.Path;

public class FeelDBWithRandomInformationCommand extends Command {

	private static final Logger LOG = Logger.getLogger(FeelDBWithRandomInformationCommand.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		
		LOG.debug("FeelDBWithRandomInformation Command finished");
		
		LOG.info("Adding new information = orders and users");
		
		DBManager manager = DBManager.getInstance();
		
		manager.addRandomUsersInformation();
		
		LOG.info("New random users has been added");
		
		manager.addRandomOrdersInformation();
		
		LOG.info("New random orders has been added");
		
		LOG.debug("FeelDBWithRandomInformation Command finished");
		
		return Path.PAGE_ADMIN_All_USERS;
	}

}
