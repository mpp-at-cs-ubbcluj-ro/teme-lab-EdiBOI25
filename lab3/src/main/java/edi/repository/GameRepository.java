package edi.repository;

import edi.domain.Game;

import java.time.LocalDate;
import java.util.List;

public interface GameRepository extends Repository<Integer,Game> {
    List<Game> findByName(String name);
    List<Game> findBetweenDates(LocalDate min, LocalDate max);
}
