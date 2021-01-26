package org.loic.api_rest_team.db;

import java.util.Arrays;

import org.loic.api_rest_team.dao.PlayerRepository;
import org.loic.api_rest_team.dao.TeamRepository;
import org.loic.api_rest_team.domains.Player;
import org.loic.api_rest_team.domains.PlayerTeam;
import org.loic.api_rest_team.domains.Team;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class LoadDatabases {

	@Bean
	CommandLineRunner initDatabases(TeamRepository teamRepository, PlayerRepository playerRepository) {
		
		Player player1 = new Player("Neymar Jr", 28, "Brazillian");
		Player player2 = new Player("Lionel Messi", 33, "Argentine");
		
		Team psg = new Team("Paris Saint Germain", "France", "Club", "Marquinhos",
								Arrays.asList(  new PlayerTeam(player1, "Ailier gauche")));
		
		Team argNationalTeam = new Team("La Albiceleste", "Argentina", "National team", "Lionel Messi",
								Arrays.asList(  new PlayerTeam(player2, "Ailier droit")));

		
		return args -> {
			log.info("Preloading " + playerRepository.saveAll(Arrays.asList(player1, player2)));
			log.info("Preloading " + teamRepository.save(psg));
			log.info("Preloading " + teamRepository.save(argNationalTeam));
			
		};
	}
}
/*public class LoadDatabases {
	
	@Bean
	CommandLineRunner initDatabases(TeamRepository teamRepository, PlayerRepository playerRepository) {
		
		Player player1 = new Player("Neymar Jr", 28, "Brazillian");
		Player player2 = new Player("Ángel Di María", 32, "Argentine");
		Player player3 = new Player("Marquinhos", 26, "Brazillian");
		Player player4 = new Player("Casemiro", 37, "Brazillian");
		Player player5 = new Player("Lionel Messi", 33, "Argentine");
		
		Team psg = new Team("Paris Saint Germain", "Fance", "Club", "Marquinhos",
				Arrays.asList(  new PlayerTeam(player1, "Ailier gauche"),
						new PlayerTeam(player2, "Ailier droit"),
						new PlayerTeam(player3, "Défenseur central")));
		
		Team brazilNationalTeam = new Team("Seleção", "Brazil", "National team", "Casemiro",
				Arrays.asList(  new PlayerTeam(player3, "Défenseur central"),
						new PlayerTeam(player4, "Milieu défensif"),
						new PlayerTeam(player1, "Ailier gauche")));
		
		Team argNationalTeam = new Team("La Albiceleste", "Argentina", "National team", "Lionel Messi",
				Arrays.asList(  new PlayerTeam(player2, "Ailier droit"),
						new PlayerTeam(player5, "Ailier droit")));
		
		
		return args -> {
			log.info("Preloading " + playerRepository.saveAll(Arrays.asList(player1, player2, player3, player4, player5)));
			log.info("Preloading " + teamRepository.save(psg));
			log.info("Preloading " + teamRepository.save(brazilNationalTeam));
			log.info("Preloading " + teamRepository.save(argNationalTeam));
			
		};
	}
}*/
