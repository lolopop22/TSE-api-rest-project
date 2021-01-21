package org.loic.api_rest_team.services;

import java.util.List;

import org.loic.api_rest_team.domains.Team;

public interface TeamService {
	
	public List<Team> findAllTeams();
	
	public Team findTeam(Long id);
	
	public Team createTeam(Team team);
	
	public void deleteTeam(Long id);
	
	public Team saveTeam(Team newTeam, Long id);

}
