package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.XGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the XGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface XGroupRepository extends JpaRepository<XGroup, Long> {

    @Query(value = "select distinct x_group from XGroup x_group left join fetch x_group.xteams",
        countQuery = "select count(distinct x_group) from XGroup x_group")
    Page<XGroup> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct x_group from XGroup x_group left join fetch x_group.xteams")
    List<XGroup> findAllWithEagerRelationships();

    @Query("select x_group from XGroup x_group left join fetch x_group.xteams where x_group.id =:id")
    Optional<XGroup> findOneWithEagerRelationships(@Param("id") Long id);

}
