package com.spring.example.crud.domain.models.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "refresh_token")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "expires_in", nullable = false )
    private LocalDateTime expiresIn;

    @Column(name = "available", nullable = false)
    private final Boolean available = true;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    public RefreshToken() { }

    public RefreshToken(User user, Integer daysToExpire) {
        this.user = user;
        this.expiresIn = LocalDateTime.now().plusDays(daysToExpire);
        this.code = UUID.randomUUID().toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(LocalDateTime expiresIn) {
        this.expiresIn = expiresIn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean nonExpired() {
        return expiresIn.isAfter(LocalDateTime.now());
    }
}
