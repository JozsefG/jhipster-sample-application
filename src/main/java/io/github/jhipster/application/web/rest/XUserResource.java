package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.XUser;
import io.github.jhipster.application.repository.XUserRepository;
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
 * REST controller for managing XUser.
 */
@RestController
@RequestMapping("/api")
public class XUserResource {

    private final Logger log = LoggerFactory.getLogger(XUserResource.class);

    private static final String ENTITY_NAME = "xUser";

    private final XUserRepository xUserRepository;

    public XUserResource(XUserRepository xUserRepository) {
        this.xUserRepository = xUserRepository;
    }

    /**
     * POST  /x-users : Create a new xUser.
     *
     * @param xUser the xUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new xUser, or with status 400 (Bad Request) if the xUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/x-users")
    @Timed
    public ResponseEntity<XUser> createXUser(@Valid @RequestBody XUser xUser) throws URISyntaxException {
        log.debug("REST request to save XUser : {}", xUser);
        if (xUser.getId() != null) {
            throw new BadRequestAlertException("A new xUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        XUser result = xUserRepository.save(xUser);
        return ResponseEntity.created(new URI("/api/x-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /x-users : Updates an existing xUser.
     *
     * @param xUser the xUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated xUser,
     * or with status 400 (Bad Request) if the xUser is not valid,
     * or with status 500 (Internal Server Error) if the xUser couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/x-users")
    @Timed
    public ResponseEntity<XUser> updateXUser(@Valid @RequestBody XUser xUser) throws URISyntaxException {
        log.debug("REST request to update XUser : {}", xUser);
        if (xUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        XUser result = xUserRepository.save(xUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, xUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /x-users : get all the xUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of xUsers in body
     */
    @GetMapping("/x-users")
    @Timed
    public List<XUser> getAllXUsers() {
        log.debug("REST request to get all XUsers");
        return xUserRepository.findAll();
    }

    /**
     * GET  /x-users/:id : get the "id" xUser.
     *
     * @param id the id of the xUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the xUser, or with status 404 (Not Found)
     */
    @GetMapping("/x-users/{id}")
    @Timed
    public ResponseEntity<XUser> getXUser(@PathVariable Long id) {
        log.debug("REST request to get XUser : {}", id);
        Optional<XUser> xUser = xUserRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(xUser);
    }

    /**
     * DELETE  /x-users/:id : delete the "id" xUser.
     *
     * @param id the id of the xUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/x-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteXUser(@PathVariable Long id) {
        log.debug("REST request to delete XUser : {}", id);

        xUserRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
