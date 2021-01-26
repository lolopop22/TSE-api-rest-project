package org.loic.api_rest_team;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.loic.api_rest_team.dao.PlayerRepository;
import org.loic.api_rest_team.domains.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayerControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Test
	public void testAll() throws Exception{
		this.mvc.perform(
				MockMvcRequestBuilders
				.get("/players")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()", is(2)))
				.andExpect(jsonPath("$[?(@.name == 'Neymar Jr')].citizenship", Matchers.contains("Brazillian")));
	}
	
	@Test
	public void testOne() throws Exception{
		this.mvc.perform(
				MockMvcRequestBuilders
				.get("/players/1")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name", is("Neymar Jr")))
			    .andExpect(jsonPath("$.age", is(28)))
			    .andExpect(jsonPath("$.citizenship", is("Brazillian")));
	}
	
	@Test
	public void testNewPlayer() throws Exception{
		
		Player player = new Player("Casemiro", 37, "Brazillian");
		
		ObjectMapper mapper = new ObjectMapper();
		byte[] playerAsBytes = mapper.writeValueAsBytes(player);
		
		this.mvc.perform(
				MockMvcRequestBuilders
				.post("/players")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(playerAsBytes))
				.andExpect(status().isOk());
		
		assertEquals(3, playerRepository.count());
		
		List<Player> players = playerRepository.findAll();
		
		boolean found = false;
		for(Player currentPlayer : players) {
			if(currentPlayer.getName().equals("Casemiro")) {
				found = true;
				playerRepository.delete(currentPlayer);
			}
		}
		
		assertTrue(found);
		
	}
	
	@Test
	public void testDeletePlayer() throws Exception{
		
		Player player = new Player("Casemiro", 37, "Brazillian");
		playerRepository.save(player);
		
		List<Player> players = playerRepository.findAll();
		
		Long id = 0L;
		for(Player currentPlayer : players) {
			if(currentPlayer.getName().equals("Casemiro")) {
				id = currentPlayer.getId();
			}
		}
		
		this.mvc.perform(
				MockMvcRequestBuilders
				.delete("/players/" + id)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		assertEquals(2, playerRepository.count());
	}
	
	@Test
	public void testReplacePlayer() throws Exception{
		Player player = new Player("Neymar Jr",28 , "French");
		
		ObjectMapper mapper = new ObjectMapper();
        byte[] playerAsBytes = mapper.writeValueAsBytes(player);
        
        this.mvc.perform(
				MockMvcRequestBuilders
				.put("/players/1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(playerAsBytes))
				.andExpect(status().isOk());
        
        player = playerRepository.findById(1L).orElse(null);
		        
		if (player == null) {
        	fail("Player not found");
        }
        
		assertEquals("French", player.getCitizenship());
		
		player.setCitizenship("Brazillian");
		playerRepository.save(player);
	}
	
}
