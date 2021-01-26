package org.loic.api_rest_team.exceptions;

public class TeamNotFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TeamNotFoundException(Long id) {
		
		super("Could not find the team with id " + id);
		
	}

}
