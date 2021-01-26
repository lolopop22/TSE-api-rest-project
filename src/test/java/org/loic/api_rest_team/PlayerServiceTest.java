package org.loic.api_rest_team;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.loic.api_rest_team.dao.PlayerRepository;
import org.loic.api_rest_team.domains.Player;
import org.loic.api_rest_team.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PlayerServiceTest {

	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Test
	public void testFindAllplayers()  throws Exception{
		
		List<Player> allPlayers = this.playerService.findAllPlayers();
		
		Assertions.assertEquals(2, allPlayers.size());
	}
	
	@Test
	public void testFindTeam()  throws Exception{
		
		Player player = this.playerService.findPlayer(1L);
		
		Assertions.assertEquals("Neymar Jr", player.getName());
		Assertions.assertEquals(28, player.getAge());
		Assertions.assertEquals("Brazillian", player.getCitizenship());
	}
	
	@Test 
	public void testCreatePlayer() {
		Player player = new Player("Marquinhos", 26, "Brazillian");
		
		player = this.playerService.createPlayer(player);
		
		List<Player> allPlayers = this.playerService.findAllPlayers();
		
		Assertions.assertEquals(3, allPlayers.size());
		
		this.playerRepository.delete(player);
	}
	
	@Test
	public void testDeletePlayer() {
		Player player = new Player("Marquinhos", 26, "Brazillian");

		this.playerRepository.save(player);
		
		List<Player> allPlayers = this.playerService.findAllPlayers();
		
		Long id = 0L;
        for(Player currentPlayer : allPlayers) {
        	if(currentPlayer.getName().equals("Marquinhos")) {
        		id = currentPlayer.getId();
        	}
        }
		
		this.playerService.deletePlayer(id);
		
		allPlayers = this.playerService.findAllPlayers();
		
		Assertions.assertEquals(2, allPlayers.size());
	}
	
	@Test
	public void testUpdatePlayer() throws Exception{
		
		Player player = this.playerRepository.findById(1L).orElse(null);
		
		player.setAge(50);
		
		this.playerService.savePlayer(player, 1L);
		
		player = this.playerService.findPlayer(1L);
		
		Assertions.assertEquals(50, player.getAge());
		
		player.setAge(28);
		
		this.playerRepository.save(player);
	}
	
}
