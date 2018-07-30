package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.XTeam;
import io.github.jhipster.application.repository.XTeamRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing XTeam.
 */
@RestController
@RequestMapping("/api")
public class XTeamResource {

    private final Logger log = LoggerFactory.getLogger(XTeamResource.class);

    private static final String ENTITY_NAME = "xTeam";

    private final XTeamRepository xTeamRepository;

    public XTeamResource(XTeamRepository xTeamRepository) {
        this.xTeamRepository = xTeamRepository;
    }

    /**
     * POST  /x-teams : Create a new xTeam.
     *
     * @param xTeam the xTeam to create
     * @return the ResponseEntity with status 201 (Created) and with body the new xTeam, or with status 400 (Bad Request) if the xTeam has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/x-teams")
    @Timed
    public ResponseEntity<XTeam> createXTeam(@Valid @RequestBody XTeam xTeam) throws URISyntaxException {
        log.debug("REST request to save XTeam : {}", xTeam);
        if (xTeam.getId() != null) {
            throw new BadRequestAlertException("A new xTeam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        XTeam result = xTeamRepository.save(xTeam);
        return ResponseEntity.created(new URI("/api/x-teams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /x-teams : Updates an existing xTeam.
     *
     * @param xTeam the xTeam to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated xTeam,
     * or with status 400 (Bad Request) if the xTeam is not valid,
     * or with status 500 (Internal Server Error) if the xTeam couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/x-teams")
    @Timed
    public ResponseEntity<XTeam> updateXTeam(@Valid @RequestBody XTeam xTeam) throws URISyntaxException {
        log.debug("REST request to update XTeam : {}", xTeam);
        if (xTeam.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        XTeam result = xTeamRepository.save(xTeam);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, xTeam.getId().toString()))
            .body(result);
    }

    /**
     * GET  /x-teams : get all the xTeams.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of xTeams in body
     */
    @GetMapping("/x-teams")
    @Timed
    public List<XTeam> getAllXTeams() {
        log.debug("REST request to get all XTeams");
        return xTeamRepository.findAll();
    }

    /**
     * GET  /x-teams/:id : get the "id" xTeam.
     *
     * @param id the id of the xTeam to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the xTeam, or with status 404 (Not Found)
     */
    @GetMapping("/x-teams/{id}")
    @Timed
    public ResponseEntity<XTeam> getXTeam(@PathVariable Long id) {
        log.debug("REST request to get XTeam : {}", id);
        Optional<XTeam> xTeam = xTeamRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(xTeam);
    }

    /**
     * DELETE  /x-teams/:id : delete the "id" xTeam.
     *
     * @param id the id of the xTeam to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/x-teams/{id}")
    @Timed
    public ResponseEntity<Void> deleteXTeam(@PathVariable Long id) {
        log.debug("REST request to delete XTeam : {}", id);

        xTeamRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
