package com.spring.example.crud.domain.repositories;

import com.spring.example.crud.domain.models.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends SoftDeleteRepository<User> {

    @Override
    @Transactional
    @Modifying
    @Query("""
        UPDATE
            #{#entityName}
        SET
            deleted_email = (
                SELECT
                    email
                FROM
                    #{#entityName}
                WHERE id = ?1),
            email = NULL,
            deleted_at = CURRENT_TIMESTAMP,
            active = 0,
            deleted_by = ?#{principal?.id}
        WHERE id = ?1
    """)
    void deleteById(Long id);

    @Override
    @Transactional
    default void delete(User user) {
        deleteById(user.getId());
    }

    @Override
    @Transactional
    default void deleteAll(Iterable<? extends User> entities) {
        entities.forEach(entity -> deleteById(entity.getId()));
    }

    Page<User> findDistinctBy(Pageable pageable);

    Boolean existsByEmail(String email);
    
    Optional<User> findOptionalByIdAndDeletedAtIsNull(Long id);

    @Query("""
        SELECT user FROM #{#entityName} user
        LEFT JOIN FETCH user.roles
        WHERE user.id = ?1
    """)
    Optional<User> findOptionalByIdAndDeletedAtIsNullFetchRoles(Long id);

    @Query("""
        SELECT user FROM #{#entityName} user
        LEFT JOIN FETCH user.roles
        WHERE user.email = ?1
    """)
    Optional<User> findOptionalByEmailFetchRoles(String email);

    Optional<User> findOptionalByEmail(String email);
}