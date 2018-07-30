package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.XUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the XUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface XUserRepository extends JpaRepository<XUser, Long> {

}
