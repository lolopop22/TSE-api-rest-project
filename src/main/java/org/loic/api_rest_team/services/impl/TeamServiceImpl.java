package org.loic.api_rest_team.services.impl;

import java.util.List;

import org.loic.api_rest_team.dao.PlayerRepository;
import org.loic.api_rest_team.dao.TeamRepository;
import org.loic.api_rest_team.domains.PlayerTeam;
import org.loic.api_rest_team.domains.Team;
import org.loic.api_rest_team.exceptions.TeamNotFoundException;
import org.loic.api_rest_team.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

	@Autowired
	private TeamRepository teamRepository;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Team> findAllTeams() {
		
		return this.teamRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Team findTeam(Long id) {
		
		return this.teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException(id));
	}

	@Override
	public Team createTeam(Team team) {
		
		/*Team foundTeam = this.teamRepository.findById(id).orElse(null);
		
		if(newTeam != null) {
			foundTeam.setName(newTeam.getName());
			foundTeam.setCountry(newTeam.getCountry());
			foundTeam.setType(newTeam.getType());
			foundTeam.setCaptain(newTeam.getCaptain());
			if(newT)
		}*/
		
		//Enregistrer les joueurs de l'équipe avant d'enregistrer l'équipe? 
			//ça ne marche pas. A moins que je m'y prend mal...
		
		/*if(team.getPlayers() != null) {
			for(PlayerTeam player : team.getPlayers()) {
				this.playerRepository.save(player.getPlayer());
			}
		}*/
		
		return this.teamRepository.save(team);
	}

	@Override
	public void deleteTeam(Long id) {
		
		this.teamRepository.deleteById(id);
	}

	@Override
	public Team saveTeam(Team newTeam, Long id) {
		
		return teamRepository.findById(id)
				.map(team -> {
					team.setName(newTeam.getName());
					team.setCountry(newTeam.getCountry());
					team.setType(newTeam.getType());
					team.setCaptain(newTeam.getCaptain());
					team.setPlayers(newTeam.getPlayers());
					return this.teamRepository.save(team);
				})
				.orElseThrow(() -> new TeamNotFoundException(id));
	}

}
