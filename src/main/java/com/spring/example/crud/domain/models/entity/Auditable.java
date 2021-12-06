package com.spring.example.crud.domain.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.example.crud.domain.models.shared.Entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.spring.example.crud.domain.services.security.SecurityService.authorized;
import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;
import static javax.persistence.FetchType.LAZY;

@MappedSuperclass
public abstract class Auditable implements Entity {

    public static final String NON_DELETED_CLAUSE = "deleted_at IS NULL";
    
    @Override
    public abstract Long getId();

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonIgnore
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    
    @JoinColumn(name = "created_by")
    @ManyToOne(fetch = LAZY)
    private User createdBy;
    
    @JoinColumn(name = "updated_by")
    @ManyToOne(fetch = LAZY)
    private User updatedBy;
    
    @JoinColumn(name = "deleted_by")
    @ManyToOne(fetch = LAZY)
    private User deletedBy;

    @JsonIgnore
    public Optional<User> getCreatedBy() {
        return ofNullable(createdBy);
    }

    @JsonIgnore
    public Optional<User> getUpdatedBy() {
        return ofNullable(updatedBy);
    }

    @JsonIgnore
    public Optional<User> getDeletedBy() {
        return ofNullable(deletedBy);
    }

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Boolean isActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @PrePersist
    private void save() {
        createdAt = now();
        createdBy = authorized()
        .map(authorized -> new User(authorized.getId()))
            .orElse(null);
    }

    @PreUpdate
    private void update() {
        updatedAt = now();
        updatedBy = authorized()
        .map(authorized -> new User(authorized.getId()))
            .orElse(null);
    }
}