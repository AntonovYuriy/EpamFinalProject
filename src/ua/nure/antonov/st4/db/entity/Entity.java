package ua.nure.antonov.st4.db.entity;

import java.io.Serializable;

/**
Each entity must have some identifier as a primary key
 * 
 */

public abstract class Entity implements Serializable {

	private static final long serialVersionUID = 8466257860808346236L;

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
