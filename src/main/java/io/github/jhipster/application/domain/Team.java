package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Team.
 */
@Entity
@Table(name = "team")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "team")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<XGroup> groups = new HashSet<>();

    @OneToMany(mappedBy = "team")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<XUser> users = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("teams")
    private Organization organization;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Team name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<XGroup> getGroups() {
        return groups;
    }

    public Team groups(Set<XGroup> xGroups) {
        this.groups = xGroups;
        return this;
    }

    public Team addGroup(XGroup xGroup) {
        this.groups.add(xGroup);
        xGroup.setTeam(this);
        return this;
    }

    public Team removeGroup(XGroup xGroup) {
        this.groups.remove(xGroup);
        xGroup.setTeam(null);
        return this;
    }

    public void setGroups(Set<XGroup> xGroups) {
        this.groups = xGroups;
    }

    public Set<XUser> getUsers() {
        return users;
    }

    public Team users(Set<XUser> xUsers) {
        this.users = xUsers;
        return this;
    }

    public Team addUser(XUser xUser) {
        this.users.add(xUser);
        xUser.setTeam(this);
        return this;
    }

    public Team removeUser(XUser xUser) {
        this.users.remove(xUser);
        xUser.setTeam(null);
        return this;
    }

    public void setUsers(Set<XUser> xUsers) {
        this.users = xUsers;
    }

    public Organization getOrganization() {
        return organization;
    }

    public Team organization(Organization organization) {
        this.organization = organization;
        return this;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Team team = (Team) o;
        if (team.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), team.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Team{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
