package org.loic.api_rest_team.domains;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class PlayerTeamKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "player_id")
	Long playerId;
	
	@Column(name = "team_id")
	Long teamId;
	
}
