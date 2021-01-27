package org.loic.api_rest_team.domains;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "age")
	private int age;
	
	@Column(name = "citizenship")
	private String citizenship;

	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@JsonIgnoreProperties(value = {"player"}, allowSetters = true)
	@OneToMany(mappedBy = "player")
	private List<PlayerTeam> teams = new ArrayList<>();
	
	public Player(){
		
	}
	
	
	public Player(String name, int age, String citizenship){
		this.name = name;
		this.age = age;
		this.citizenship = citizenship;
	}


	public void addTeam(PlayerTeam playerTeam) {
		this.teams.add(playerTeam);
		playerTeam.setPlayer(this);
	}
	
	public void removeTeam(PlayerTeam playerTeam) {
	    this.teams.remove(playerTeam);
	    playerTeam.setPlayer(null);
	    playerTeam.setPosition(null);
	}
}
