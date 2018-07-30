package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.XGroup;
import io.github.jhipster.application.repository.XGroupRepository;
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
 * REST controller for managing XGroup.
 */
@RestController
@RequestMapping("/api")
public class XGroupResource {

    private final Logger log = LoggerFactory.getLogger(XGroupResource.class);

    private static final String ENTITY_NAME = "xGroup";

    private final XGroupRepository xGroupRepository;

    public XGroupResource(XGroupRepository xGroupRepository) {
        this.xGroupRepository = xGroupRepository;
    }

    /**
     * POST  /x-groups : Create a new xGroup.
     *
     * @param xGroup the xGroup to create
     * @return the ResponseEntity with status 201 (Created) and with body the new xGroup, or with status 400 (Bad Request) if the xGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/x-groups")
    @Timed
    public ResponseEntity<XGroup> createXGroup(@Valid @RequestBody XGroup xGroup) throws URISyntaxException {
        log.debug("REST request to save XGroup : {}", xGroup);
        if (xGroup.getId() != null) {
            throw new BadRequestAlertException("A new xGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        XGroup result = xGroupRepository.save(xGroup);
        return ResponseEntity.created(new URI("/api/x-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /x-groups : Updates an existing xGroup.
     *
     * @param xGroup the xGroup to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated xGroup,
     * or with status 400 (Bad Request) if the xGroup is not valid,
     * or with status 500 (Internal Server Error) if the xGroup couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/x-groups")
    @Timed
    public ResponseEntity<XGroup> updateXGroup(@Valid @RequestBody XGroup xGroup) throws URISyntaxException {
        log.debug("REST request to update XGroup : {}", xGroup);
        if (xGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        XGroup result = xGroupRepository.save(xGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, xGroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /x-groups : get all the xGroups.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of xGroups in body
     */
    @GetMapping("/x-groups")
    @Timed
    public List<XGroup> getAllXGroups(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all XGroups");
        return xGroupRepository.findAllWithEagerRelationships();
    }

    /**
     * GET  /x-groups/:id : get the "id" xGroup.
     *
     * @param id the id of the xGroup to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the xGroup, or with status 404 (Not Found)
     */
    @GetMapping("/x-groups/{id}")
    @Timed
    public ResponseEntity<XGroup> getXGroup(@PathVariable Long id) {
        log.debug("REST request to get XGroup : {}", id);
        Optional<XGroup> xGroup = xGroupRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(xGroup);
    }

    /**
     * DELETE  /x-groups/:id : delete the "id" xGroup.
     *
     * @param id the id of the xGroup to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/x-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteXGroup(@PathVariable Long id) {
        log.debug("REST request to delete XGroup : {}", id);

        xGroupRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
