package edi.repository;

import edi.domain.Game;
import edi.utils.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GamesDBRepository implements GameRepository{

    private JdbcUtils dbUtils;



    private static final Logger logger= LogManager.getLogger();

    public GamesDBRepository(Properties props) {
        logger.info("Initializing CarsDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public List<Game> findByName(String name) {
 	//to do
        logger.traceEntry("finding games with name {}",name);
        Connection con=dbUtils.getConnection();
        List<Game> games=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Games where name like ?")){
            preStmt.setString(1, "%"+name+"%");
            try(ResultSet result=preStmt.executeQuery()){
                while(result.next()){
                    int id=result.getInt("id");
                    String name1=result.getString("name");
                    LocalDate releaseDate=result.getDate("release_date").toLocalDate();
                    int price=result.getInt("price");
                    Game game=new Game(name1,releaseDate,price);
                    game.setId(id);
                    games.add(game);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        return games;
    }

    @Override
    public List<Game> findBetweenDates(LocalDate min, LocalDate max) {
	//to do
        logger.traceEntry("finding games between dates {} and {}",min,max);
        Connection con=dbUtils.getConnection();
        List<Game> games=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Games where release_date between ? and ?")){
            preStmt.setDate(1, Date.valueOf(min));
            preStmt.setDate(2, Date.valueOf(max));
            try(ResultSet result=preStmt.executeQuery()){
                while(result.next()){
                    int id=result.getInt("id");
                    String name=result.getString("name");
                    LocalDate releaseDate=result.getDate("release_date").toLocalDate();
                    int price=result.getInt("price");
                    Game game=new Game(name,releaseDate,price);
                    game.setId(id);
                    games.add(game);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        return games;
    }

    @Override
    public void add(Game elem) {
        //to do
        logger.traceEntry("saving game {} ",elem);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Games (name,release_date,price) values (?,?,?)")) {
            preStmt.setString(1, elem.getName());
            preStmt.setDate(2, Date.valueOf(elem.getReleaseDate()));
            preStmt.setInt(3, elem.getPrice());
            int result = preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void update(Integer integer, Game elem) {
      //to do
        logger.traceEntry("updating game {} ",elem);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("update Games set name=?, release_date=?, price=? where id=?")){
            preStmt.setString(1, elem.getName());
            preStmt.setDate(2, Date.valueOf(elem.getReleaseDate()));
            preStmt.setInt(3, elem.getPrice());
            preStmt.setInt(4, integer);
            int result = preStmt.executeUpdate();
            logger.trace("Updated {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public Iterable<Game> findAll() {
         //to do
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Game> games=new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Games")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    LocalDate releaseDate = result.getDate("release_date").toLocalDate();
                    int price = result.getInt("price");
                    Game game = new Game(name, releaseDate, price);
                    game.setId(id);
                    games.add(game);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit(games);
	    return games;
    }
}
