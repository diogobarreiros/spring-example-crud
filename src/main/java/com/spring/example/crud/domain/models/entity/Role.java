package com.spring.example.crud.domain.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serial;
import java.util.Objects;

@Entity
@Table(name = "role")
@Where(clause = Auditable.NON_DELETED_CLAUSE)
public class Role extends Auditable implements GrantedAuthority {

    @Serial
    private static final long serialVersionUID = -8524505911742593369L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @JsonIgnore
    @Column(name = "deleted_name")
    private String deletedName;

    @Column(name = "initials", unique = true)
    private String initials;

    @JsonIgnore
    @Column(name = "deleted_initials")
    private String deletedInitials;

    @Column(unique = true)
    private String description;

    public Role() { }

    public Role(String initials) {
        this.initials = initials;
    }

    public Role(Long id) {
        this.id = id;
    }

    public Role(Long id, String initials) {
        this.id = id;
        this.initials = initials;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeletedName() {
        return deletedName;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getDeletedInitials() {
        return deletedInitials;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean compare(String search) {
        if (Objects.nonNull(search)) {
            return 
                getName().equalsIgnoreCase(search) ||
                getInitials().equalsIgnoreCase(search);
        }
        return false;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this)
            return true;
        if (!(object instanceof Role role)) {
            return false;
        }
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return this.getAuthority();
    }

    @JsonIgnore
    @Override
    public String getAuthority() {
        return this.getInitials();
    }
    
}