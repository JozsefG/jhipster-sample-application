package io.github.jhipster.application.domain;

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
 * A XGroup.
 */
@Entity
@Table(name = "x_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class XGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("")
    private XTeam xTeam;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "xgroup_xteam",
               joinColumns = @JoinColumn(name = "xgroups_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "xteams_id", referencedColumnName = "id"))
    private Set<XTeam> xteams = new HashSet<>();

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

    public XGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public XTeam getXTeam() {
        return xTeam;
    }

    public XGroup xTeam(XTeam xTeam) {
        this.xTeam = xTeam;
        return this;
    }

    public void setXTeam(XTeam xTeam) {
        this.xTeam = xTeam;
    }

    public Set<XTeam> getXteams() {
        return xteams;
    }

    public XGroup xteams(Set<XTeam> xTeams) {
        this.xteams = xTeams;
        return this;
    }

    public XGroup addXteam(XTeam xTeam) {
        this.xteams.add(xTeam);
        xTeam.getXgroups().add(this);
        return this;
    }

    public XGroup removeXteam(XTeam xTeam) {
        this.xteams.remove(xTeam);
        xTeam.getXgroups().remove(this);
        return this;
    }

    public void setXteams(Set<XTeam> xTeams) {
        this.xteams = xTeams;
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
        XGroup xGroup = (XGroup) o;
        if (xGroup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), xGroup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "XGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
