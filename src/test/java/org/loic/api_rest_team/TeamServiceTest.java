package org.loic.api_rest_team;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.loic.api_rest_team.dao.TeamRepository;
import org.loic.api_rest_team.domains.PlayerTeam;
import org.loic.api_rest_team.domains.Team;
import org.loic.api_rest_team.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TeamServiceTest {

	@Autowired
	private TeamService teamService;
	
	@Autowired
	private TeamRepository teamRepository;
	
	@Test
	public void testFindAllTeams()  throws Exception{
		
		List<Team> allTeams = this.teamService.findAllTeams();
		
		Assertions.assertEquals(2, allTeams.size());
	}
	
	@Test
	public void testFindTeam()  throws Exception{
		
		Team team = this.teamService.findTeam(1L);
		
		Assertions.assertEquals("Paris Saint Germain", team.getName());
		Assertions.assertEquals("France", team.getCountry());
		Assertions.assertEquals("Club", team.getType());
		Assertions.assertEquals("Marquinhos", team.getCaptain());
	}
	
	@Test 
	public void testCreateTeam() {
		Team team = new Team("Seleção", "Brazil", "National team", "Casemiro", new ArrayList<PlayerTeam>());
		
		team = this.teamService.createTeam(team);
		
		List<Team> allTeams = this.teamService.findAllTeams();
		
		Assertions.assertEquals(3, allTeams.size());
		
		this.teamRepository.delete(team);
	}
	
	@Test
	public void testDeleteTeam() {
		Team team = new Team("Seleção", "Brazil", "National team", "Casemiro", new ArrayList<PlayerTeam>());

		this.teamRepository.save(team);
		
		List<Team> allTeams = this.teamService.findAllTeams();
		
		Long id = 0L;
        for(Team currentTeam : allTeams) {
        	if(currentTeam.getName().equals("Seleção")) {
        		id = currentTeam.getId();
        	}
        }
		
		this.teamService.deleteTeam(id);
		
		allTeams = this.teamService.findAllTeams();
		
		Assertions.assertEquals(2, allTeams.size());
	}
	
	@Test
	public void testUpdateTeam() throws Exception{
		
		Team team = this.teamRepository.findById(1L).orElse(null);
		
		team.setCaptain("ASSONTIA");
		
		this.teamService.saveTeam(team, 1L);
		
		team = this.teamService.findTeam(1L);
		
		Assertions.assertEquals("ASSONTIA", team.getCaptain());
		
		team.setCaptain("Marquinhos");
		
		this.teamRepository.save(team);
	}
	
}
