package ua.nure.antonov.st4.db.entity;

import ua.nure.antonov.st4.db.entity.User;

public enum Role {
	ADMIN, USER;

	public static Role getRole(User user) {
		int roleId = user.getRoleId();
		return Role.values()[roleId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}

}
