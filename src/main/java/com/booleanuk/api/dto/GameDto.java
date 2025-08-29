package com.booleanuk.api.dto;

public record GameDto(
        Integer id,
        String title,
        String genre,
        String publisher,
        String developer,
        Integer releaseYear,
        Boolean isEarlyAccess
) {}
