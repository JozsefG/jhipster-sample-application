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
 * A XUser.
 */
@Entity
@Table(name = "x_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class XUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("users")
    private Team team;

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<XGroup> groups = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Role> roles = new HashSet<>();

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

    public XUser name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public XUser team(Team team) {
        this.team = team;
        return this;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Set<XGroup> getGroups() {
        return groups;
    }

    public XUser groups(Set<XGroup> xGroups) {
        this.groups = xGroups;
        return this;
    }

    public XUser addGroup(XGroup xGroup) {
        this.groups.add(xGroup);
        xGroup.getUsers().add(this);
        return this;
    }

    public XUser removeGroup(XGroup xGroup) {
        this.groups.remove(xGroup);
        xGroup.getUsers().remove(this);
        return this;
    }

    public void setGroups(Set<XGroup> xGroups) {
        this.groups = xGroups;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public XUser roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public XUser addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
        return this;
    }

    public XUser removeRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
        return this;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
        XUser xUser = (XUser) o;
        if (xUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), xUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "XUser{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
