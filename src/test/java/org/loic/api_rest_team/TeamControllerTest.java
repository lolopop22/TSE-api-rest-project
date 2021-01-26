package org.loic.api_rest_team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.hamcrest.Matchers.*;

//import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.loic.api_rest_team.dao.PlayerRepository;
import org.loic.api_rest_team.dao.TeamRepository;
import org.loic.api_rest_team.domains.Player;
import org.loic.api_rest_team.domains.PlayerTeam;
import org.loic.api_rest_team.domains.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private TeamRepository teamRepository;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Test
	public void testAll() throws Exception{
		this.mvc.perform(
				MockMvcRequestBuilders
				.get("/teams")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()", is(2)))
				.andExpect(jsonPath("$[?(@.name == 'Paris Saint Germain')].players",  hasSize(1)));
	}
	
	@Test
	public void testOne() throws Exception{
		this.mvc.perform(MockMvcRequestBuilders
				.get("/teams/1")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name", is("Paris Saint Germain")))
			    .andExpect(jsonPath("$.country", is("France")))
			    .andExpect(jsonPath("$.captain", is("Marquinhos")))
			    .andExpect(jsonPath("$.players").isArray())
			    .andExpect(jsonPath("$.players[0].player.name", is("Neymar Jr")))
			    .andExpect(jsonPath("$.players[0].player.age", is(28)))
			    .andExpect(jsonPath("$.players[0].player.citizenship", is("Brazillian")))
			    .andExpect(jsonPath("$.players[0].postion", is("Ailier gauche")));
	}
	
	@Test
	public void testNewTeam() throws Exception{
		
		Player player = new Player("Marquinhos", 26, "Brazillian");
		
		Team newTeam = new Team("Seleção", "Brazil", "National team", "Casemiro",
								Arrays.asList(  new PlayerTeam(player, "Défenseur central")));
		
		ObjectMapper mapper = new ObjectMapper();
        byte[] teamAsBytes = mapper.writeValueAsBytes(newTeam);
        
        this.mvc.perform(
				MockMvcRequestBuilders
				.post("/teams")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(teamAsBytes))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.players").isArray())
			    .andExpect(jsonPath("$.players[0].player.name", is("Marquinhos")))
			    .andExpect(jsonPath("$.players[0].postion", is("Défenseur central")));
        
        assertEquals(3, teamRepository.count());
		
        List<Team> teams = teamRepository.findAll();
        boolean found = false;
        for(Team currentTeam : teams) {
        	if(currentTeam.getName().equals("Seleção")) {
        		found = true;
        		teamRepository.delete(currentTeam);
        	}
        }
        
        assertTrue(found);
	}
	
	@Test
	public void testDeleteTeam() throws Exception{
		Player player = new Player("Marquinhos", 26, "Brazillian");
		playerRepository.save(player);
		
		Team newTeam = new Team("Seleção", "Brazil", "National team", "Casemiro",
								Arrays.asList(  new PlayerTeam(player, "Défenseur central")));
		teamRepository.save(newTeam);
		
		List<Team> teams = teamRepository.findAll();
        
        Long id = 0L;
        for(Team currentTeam : teams) {
        	if(currentTeam.getName().equals("Seleção")) {
        		id = currentTeam.getId();
        	}
        }
        
        this.mvc.perform(
				MockMvcRequestBuilders
				.delete("/teams/" + id)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
        
        assertEquals(2, teamRepository.count());
	}
	
	@Test
	public void testReplaceTeam() throws Exception{
		Team team = new Team("PSG", "France", "Club", "Marquinhos", new ArrayList<PlayerTeam>());
		
		ObjectMapper mapper = new ObjectMapper();
        byte[] teamAsBytes = mapper.writeValueAsBytes(team);
        
        this.mvc.perform(
				MockMvcRequestBuilders
				.put("/teams/1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(teamAsBytes))
				.andExpect(status().isOk());
        
        team = teamRepository.findById(1L).orElse(null);
        
        if (team == null) {
        	fail("team not found");
        }
        
		assertEquals("PSG", team.getName());
		
		team.setName("Paris Saint Germain");
		teamRepository.save(team);
	}
	
}
