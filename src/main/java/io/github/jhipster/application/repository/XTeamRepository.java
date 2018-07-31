package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.XTeam;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the XTeam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface XTeamRepository extends JpaRepository<XTeam, Long> {

}
