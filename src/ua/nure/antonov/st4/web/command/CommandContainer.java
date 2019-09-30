package ua.nure.antonov.st4.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

/**
 * Holder for all commands.
 */
public class CommandContainer {
	
	private static final Logger LOG = Logger.getLogger(CommandContainer.class);
	
	private static Map<String, Command> commands = new TreeMap<String, Command>();
	
	static {
		// common commands
		commands.put("login", new LoginCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("noCommand", new NoCommand());
		commands.put("Orders", new OrdersCommand());
		commands.put("Services", new ServicesCommand());
		commands.put("EditService", new EditServiceCommand());
		
		// user commands
		commands.put("userSettings", new UserSettingsCommand());
		commands.put("userRecharge", new UserRechargeCommand());
		commands.put("randomFeelDataBase", new FeelDBWithRandomInformationCommand());
		commands.put("PurchaseService", new PurchaseServiceCommand());
		
		// admin commands
		commands.put("allUsers", new AllUsersCommand());
		commands.put("allSettings", new AllSettingsCommand());
		commands.put("createNewUser", new CreateNewUser());
		commands.put("allRecharge", new AllRechargeCommand());
		commands.put("allPasswordChange", new ChangePasswordCommand());
		commands.put("DeleteService", new DeleteServiceCommand());
		commands.put("CreateNewService", new CreateNewServiceCommand());
		
		
		LOG.debug("Command container was successfully initialized");
		LOG.trace("Total possible number of commands --> " + commands.size());
	}

	/**
	 * Returns command object with the given name.
	 * 
	 * @param commandName
	 *            Name of the command.
	 * @return Command object.
	 */
	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			LOG.trace("Command not found, name --> " + commandName);
			return commands.get("noCommand"); 
		}
		return commands.get(commandName);
	}
	
}