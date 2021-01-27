package org.loic.api_rest_team.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.loic.api_rest_team.dao.PlayerRepository;
import org.loic.api_rest_team.dao.TeamRepository;
import org.loic.api_rest_team.domains.Player;
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
		
		List<PlayerTeam> newPlayers = new ArrayList<>();
		
		if(team.getPlayers() != null) {
			for(PlayerTeam player : team.getPlayers()) {
				Player player_ = player.getPlayer();
				Player newPlayer = new Player(player_.getName(), player_.getAge(), player_.getCitizenship());
				PlayerTeam newPlayerteam = new PlayerTeam(newPlayer, player.getPosition());
				newPlayers.add(newPlayerteam);
				this.playerRepository.save(newPlayer);
			}
		}
		
		Team newTeam = new Team(team.getName(), team.getCountry(), team.getType(), team.getCaptain(), newPlayers);
		
		return this.teamRepository.save(newTeam);
	}

	@Override
	public void deleteTeam(Long id) {
		
		boolean isExistBeforeDelete = this.teamRepository.findById(id).isPresent();
		
		if(isExistBeforeDelete) {
			this.teamRepository.deleteById(id);
		}
		
	}

	@Override
	public Team saveTeam(Team newTeam, Long id) {
		
		Team team = teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException(id));
		
		if(team != null) {
			team.setName(newTeam.getName());
			team.setCountry(newTeam.getCountry());
			team.setType(newTeam.getType());
			team.setCaptain(newTeam.getCaptain());
			
			if(team.getPlayers() != null) {
				List<PlayerTeam> newPlayers = new ArrayList<>();
				for(PlayerTeam player : team.getPlayers()) {
					Player player_ = player.getPlayer();
					Player newPlayer = new Player(player_.getName(), player_.getAge(), player_.getCitizenship());
					PlayerTeam newPlayerteam = new PlayerTeam(newPlayer, player.getPosition());
					newPlayers.add(newPlayerteam);
					this.playerRepository.save(newPlayer);
				}
				team.setPlayers(newPlayers);
			}
			return this.teamRepository.save(team);
		}
		
		return team;
		
		
		/*return teamRepository.findById(id)
				.map(team -> {
					team.setName(newTeam.getName());
					team.setCountry(newTeam.getCountry());
					team.setType(newTeam.getType());
					team.setCaptain(newTeam.getCaptain());
					team.setPlayers(newTeam.getPlayers());
					return this.teamRepository.save(team);
				})
				.orElseThrow(() -> new TeamNotFoundException(id));*/
	}

}
