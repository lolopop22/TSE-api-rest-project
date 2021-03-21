package org.loic.api_rest_team.services.impl;

import java.util.List;

import org.loic.api_rest_team.dao.PlayerRepository;
import org.loic.api_rest_team.domains.Player;
import org.loic.api_rest_team.domains.PlayerTeam;
import org.loic.api_rest_team.exceptions.PlayerNotFoundException;
import org.loic.api_rest_team.exceptions.TeamNotFoundException;
import org.loic.api_rest_team.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

	@Autowired
	private PlayerRepository playerRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Player> findAllPlayers() {
		
		return this.playerRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Player findPlayer(Long id) {
		
		return this.playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException(id));
	}

	@Override
	public Player createPlayer(Player player) {
		
		return this.playerRepository.save(player);
	}

	@Override
	public void deletePlayer(Long id) {
		
		Player player = this.playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException(id));
		
		//boolean isExistBeforeDelete = this.playerRepository.findById(id).isPresent();
		
		if(player != null) {
			List<PlayerTeam> teams = player.getTeams();
			System.out.println("teams.size: " + teams.size());
			if(!teams.isEmpty()) {
				for(int i=0; i<teams.size(); i++) {
					player.removeTeam(teams.get(i));
				}
			}
			player = this.playerRepository.save(player);
			this.playerRepository.deleteById(player.getId());
		}
		
		/*if(isExistBeforeDelete) {
			player
			this.playerRepository.deleteById(id);
		}*/
	}

	@Override
	public Player savePlayer(Player newPlayer, Long id) {
		
		return playerRepository.findById(id)
				.map(player -> {
					player.setName(newPlayer.getName());
					player.setAge(newPlayer.getAge());
					player.setCitizenship(newPlayer.getCitizenship());
					player.setTeams(newPlayer.getTeams());
					return this.playerRepository.save(player);
				})
				.orElseThrow(() -> new PlayerNotFoundException(id));
	}

}
