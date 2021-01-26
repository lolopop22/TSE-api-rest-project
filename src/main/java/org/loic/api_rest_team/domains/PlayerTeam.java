package org.loic.api_rest_team.domains;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
//import javax.persistence.MapsId;
//import javax.persistence.EmbeddedId;
//import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PlayerTeam {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "player_id")
	@JsonIgnoreProperties("teams")
	Player player;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	@JsonIgnoreProperties("players")
	Team team;
	
	String position;
	
	PlayerTeam(){

	}
	
	public PlayerTeam(Player player, String position){
		player.addTeam(this);
		this.position = position;
	}
	
}

/*package org.loic.api_rest_team.domains;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Data;

@Data
@Entity
public class PlayerTeam {
	
	@EmbeddedId
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	PlayerTeamKey id;
	
	@ManyToOne
	@MapsId("playerId")
	@JoinColumn(name = "player_id")
	Player player;
	
	@ManyToOne
	@MapsId("teamId")
	@JoinColumn(name = "team_id")
	Team team;
	
	String postion;
	
	PlayerTeam(){

	}
	
	public PlayerTeam(Player player, String position){
		this.player = player;
		this.postion = position;
	}
	
}*/
