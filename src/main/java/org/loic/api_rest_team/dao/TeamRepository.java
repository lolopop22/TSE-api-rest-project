package org.loic.api_rest_team.dao;

import org.loic.api_rest_team.domains.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
