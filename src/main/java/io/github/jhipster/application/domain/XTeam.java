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
 * A XTeam.
 */
@Entity
@Table(name = "x_team")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class XTeam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Organization organization;

    @ManyToMany(mappedBy = "xteams")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<XGroup> xgroups = new HashSet<>();

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

    public XTeam name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Organization getOrganization() {
        return organization;
    }

    public XTeam organization(Organization organization) {
        this.organization = organization;
        return this;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Set<XGroup> getXgroups() {
        return xgroups;
    }

    public XTeam xgroups(Set<XGroup> xGroups) {
        this.xgroups = xGroups;
        return this;
    }

    public XTeam addXgroup(XGroup xGroup) {
        this.xgroups.add(xGroup);
        xGroup.getXteams().add(this);
        return this;
    }

    public XTeam removeXgroup(XGroup xGroup) {
        this.xgroups.remove(xGroup);
        xGroup.getXteams().remove(this);
        return this;
    }

    public void setXgroups(Set<XGroup> xGroups) {
        this.xgroups = xGroups;
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
        XTeam xTeam = (XTeam) o;
        if (xTeam.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), xTeam.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "XTeam{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
