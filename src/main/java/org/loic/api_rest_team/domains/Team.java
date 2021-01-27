package org.loic.api_rest_team.domains;

import java.util.ArrayList;
import java.util.List;
/*import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;*/

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "post")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Team {

	@Id
//	@GeneratedValue
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String country;
	
	private String type;
	
	private String captain;
	
	//@OneToMany(cascade = CascadeType.ALL)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@JsonIgnoreProperties("team")
	@OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
	private List<PlayerTeam> players = new ArrayList<>();
		
	public Team(){
		
	}
	
	public void addPlayer(PlayerTeam player) {
		players.add(player);
		player.setTeam(this);
	}
	
	public Team(String name, String country, String type, String captain, List<PlayerTeam> players){
		this.name = name;
		this.country = country;
		this.type = type;
		this.captain = captain;
		for(PlayerTeam player : players) {
			addPlayer(player);
		}
	}
}
