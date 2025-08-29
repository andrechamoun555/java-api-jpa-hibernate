package com.booleanuk.api.controller;

import com.booleanuk.api.dto.UserDto;
import com.booleanuk.api.model.User;
import com.booleanuk.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::toDto).toList();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserDto dto) {
        User toSave = new User(
                dto.email(),
                dto.firstName(),
                dto.lastName(),
                dto.username(),
                dto.phone()
        );
        User saved = userRepository.save(toSave);
        return toDto(saved);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto update(@PathVariable Integer id, @RequestBody UserDto dto) {
        User toUpdate = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        toUpdate.setEmail(dto.email());
        toUpdate.setFirstName(dto.firstName());
        toUpdate.setLastName(dto.lastName());
        toUpdate.setUsername(dto.username());
        toUpdate.setPhone(dto.phone());
        User saved = userRepository.save(toUpdate);
        return toDto(saved);

    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto delete(@PathVariable Integer id) {
        User toDelete = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        UserDto dto = toDto(toDelete);
        userRepository.delete(toDelete);
        return dto;
    }


    private UserDto toDto(User u) {
        return new UserDto(
                u.getId(),
                u.getEmail(),
                u.getFirstName(),
                u.getLastName(),
                u.getUsername(),
                u.getPhone()
        );
    }

}

