package com.booleanuk.api.dto;


public record UserDto (
        Integer id,
        String email,
        String firstName,
        String lastName,
        String username,
        String phone
) {

}
