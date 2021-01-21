package org.loic.api_rest_team.exceptions;

public class PlayerNotFoundException extends RuntimeException {

	public PlayerNotFoundException(Long id) {
		
		super("Could not find the player with id " + id);
		
	}
	
}
