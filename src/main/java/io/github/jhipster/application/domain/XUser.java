package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToMany(mappedBy = "xusers")
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

    public XUser name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<XGroup> getXgroups() {
        return xgroups;
    }

    public XUser xgroups(Set<XGroup> xGroups) {
        this.xgroups = xGroups;
        return this;
    }

    public XUser addXgroup(XGroup xGroup) {
        this.xgroups.add(xGroup);
        xGroup.getXusers().add(this);
        return this;
    }

    public XUser removeXgroup(XGroup xGroup) {
        this.xgroups.remove(xGroup);
        xGroup.getXusers().remove(this);
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
