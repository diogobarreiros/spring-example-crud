package com.spring.example.crud.controllers.api;

import com.spring.example.crud.domain.models.entity.User;
import com.spring.example.crud.domain.models.pagination.Page;
import com.spring.example.crud.domain.models.pagination.Pagination;
import com.spring.example.crud.domain.services.user.CreateUserService;
import com.spring.example.crud.domain.services.user.FindUserService;
import com.spring.example.crud.domain.services.user.RemoveUserService;
import com.spring.example.crud.domain.services.user.UpdateUserService;
import com.spring.example.crud.domain.services.user.dto.CreateUserApi;
import com.spring.example.crud.domain.services.user.dto.SearchUser;
import com.spring.example.crud.domain.services.user.dto.UpdateUser;
import com.spring.example.crud.domain.services.user.dto.UserDetails;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Api(tags = "User")
@RestController
@RequestMapping("/api/users")
public class UsersController {
    
    @Autowired
    private CreateUserService createService;
    
    @Autowired
    private UpdateUserService updateService;
    
    @Autowired
    private RemoveUserService removeService;
    
    @Autowired
    private FindUserService findService;
    
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADM')")
    public ResponseEntity<Page<UserDetails>> index(Pagination pagination, Sort sort, SearchUser search) {
        return findService.find(pagination, sort, search);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADM', 'USER')")
    public ResponseEntity<UserDetails> show(@PathVariable Long id) {
        return findService.find(id);
    }
    
    @PostMapping
    @ResponseStatus(CREATED)
    public ResponseEntity<UserDetails> save(@Validated @RequestBody CreateUserApi body) {
        return createService.create(body);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADM', 'USER')")
    public ResponseEntity<UserDetails> update(
        @PathVariable Long id,
        @RequestBody @Validated UpdateUser body
    ) {
        return updateService.update(id, body);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADM')")
    public ResponseEntity<User> destroy(@PathVariable Long id) {
        return removeService.remove(id);
    }
}