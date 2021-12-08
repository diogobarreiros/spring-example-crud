package com.spring.example.crud.domain.services.user;

import com.spring.example.crud.domain.models.entity.User;
import com.spring.example.crud.domain.repositories.UserRepository;
import com.spring.example.crud.utils.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.spring.example.crud.utils.Responses.noContent;
import static com.spring.example.crud.utils.Responses.notFound;

@Service
public class RemoveUserService {

    @Autowired
    UserRepository repository;

    public ResponseEntity<User> remove(Long id) {
        return repository.findOptionalByIdAndDeletedAtIsNull(id)
            .map(user -> noContent(user, repository))
                .orElseGet(Responses::notFound);
    }
}
