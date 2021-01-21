package org.loic.api_rest_team.dao;

import org.loic.api_rest_team.domains.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {

}
