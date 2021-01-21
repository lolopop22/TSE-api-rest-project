package org.loic.api_rest_team.services;

import java.util.List;

import org.loic.api_rest_team.domains.Player;

public interface PlayerService {
	
	public List<Player> findAllPlayers();
	
	public Player findPlayer(Long id);
	
	public Player createPlayer(Player player);
	
	public void deletePlayer(Long id);
	
	public Player savePlayer(Player newPlayer, Long id);
	
}
