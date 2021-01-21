package org.loic.api_rest_team.exceptions;

public class TeamNotFoundException extends RuntimeException{
	
	public TeamNotFoundException(Long id) {
		
		super("Could not find the team with id " + id);
		
	}

}
