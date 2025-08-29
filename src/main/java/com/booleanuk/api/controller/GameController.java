package com.booleanuk.api.controller;

import com.booleanuk.api.dto.GameDto;
import com.booleanuk.api.model.Game;
import com.booleanuk.api.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("games")
public class GameController {

    @Autowired
    private GameRepository repository;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GameDto> getAll() {
        List<Game> games = repository.findAll();
        List<GameDto> result = new ArrayList<>();
        for (Game g : games) {
            result.add(toDto(g));
        }
        return result;
    }


    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public GameDto getById(@PathVariable Integer id) {
        Game game = mustFind(id);
        return toDto(game);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameDto create(@RequestBody GameDto dto) {
        Game toSave = new Game(
                dto.title(),
                dto.genre(),
                dto.publisher(),
                dto.developer(),
                dto.releaseYear(),
                dto.isEarlyAccess()
        );
        Game saved = repository.save(toSave);
        return toDto(saved);
    }


    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public GameDto update(@PathVariable Integer id, @RequestBody GameDto dto) {
        Game existing = mustFind(id);
        existing.setTitle(dto.title());
        existing.setGenre(dto.genre());
        existing.setPublisher(dto.publisher());
        existing.setDeveloper(dto.developer());
        existing.setReleaseYear(dto.releaseYear());
        existing.setIsEarlyAccess(dto.isEarlyAccess());
        Game updated = repository.save(existing);
        return toDto(updated);
    }


    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public GameDto delete(@PathVariable Integer id) {
        Game existing = mustFind(id);
        GameDto dto = toDto(existing);
        repository.delete(existing);
        return dto;
    }

    private Game mustFind(Integer id) {
        Game g = repository.findById(id).orElse(null);
        if (g == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
        return g;
    }

    private GameDto toDto(Game g) {
        return new GameDto(
                g.getId(),
                g.getTitle(),
                g.getGenre(),
                g.getPublisher(),
                g.getDeveloper(),
                g.getReleaseYear(),
                g.getIsEarlyAccess()
        );
    }
}
