package edi;

import edi.domain.Game;
import edi.repository.GameRepository;
import edi.repository.GamesDBRepository;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }

        GameRepository gameRepo=new GamesDBRepository(props);
        gameRepo.add(new Game("God of War",
                LocalDate.of(2018,4,20), 60));
        System.out.println("Toate jocurile din db");
        for(Game game:gameRepo.findAll())
            System.out.println(game);
        String gameName="Bloodborne";
        System.out.println("Jocuri gasite cu numele "+ gameName);
        for(Game game:gameRepo.findByName(gameName))
            System.out.println(game);
    }
}