package com.example.superherov5;

import com.example.superherov5.dto.HeroCityDTO;
import com.example.superherov5.dto.HeroPowerCountDTO;
import com.example.superherov5.dto.HeroPowerDTO;
import com.example.superherov5.dto.Superhero;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class SuperheroRepository {
    /* metode fungerer ikke, kan ikke lokalisere i drivermanager.
    @Value("${spring.datasource.url}")
    private String db_url;

    @Value("${spring.datasource.username}")
    private String uid;

    @Value("${spring.datasource.password}")
    private String pwd;
*/


    // En superhelt med et bestemt heroName eller en liste med alle superhelte, der indeholder:
    //heroName, realName og creationYear
    public List<SuperheroModel> getAllSuperheroes() {
        List<SuperheroModel> superheroes = new ArrayList<>();
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/superheroes", "root", "Qhr96wmr2k")) { //Din mySQL database, root som er mySQL username, kodeord til mySQL
            String SQL = "SELECT * FROM SUPERHERO;"; // SQL select all statement
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                superheroes.add(new SuperheroModel(rs.getString("SUPERHERONAME"), rs.getString("REALNAME"), rs.getInt("CREATIONYEAR")));
            }
            return superheroes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<HeroPowerDTO> getSuperheroesWithPowersID(String navn) {
        List<HeroPowerDTO> superheroes = new ArrayList<>();
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/superheroes", "root", "Qhr96wmr2k")) {
            String SQL = "SELECT s.superheroname, GROUP_CONCAT(sp.powername) AS superpowers\n" +
                    "FROM superhero s\n" +
                    "LEFT JOIN superhero_power shp ON s.superheroID = shp.superheroID\n" +
                    "LEFT JOIN superpower sp ON shp.powerID = sp.superpowerID\n" +
                    "WHERE s.superheroname = ? " +
                    "GROUP BY s.superheroname;";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, navn);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("superheroname");
                List<String> superpowerList = Arrays.asList(rs.getString("superpowers").split(","));
                superheroes.add(new HeroPowerDTO(name, superpowerList));
            }
            return superheroes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<SuperheroModel> getCities() {
        List<SuperheroModel> cities = new ArrayList<>();
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/superheroes", "root", "Qhr96wmr2k")) {
            String SQL = "SELECT cityname " +
                    "FROM city;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                cities.add(new SuperheroModel(rs.getString("cityname")));
            }
            return cities;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<SuperheroModel> getPowers() {
        List<SuperheroModel> superheroes = new ArrayList<>();
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/superheroes", "root", "Qhr96wmr2k")) {
            String SQL = "SELECT powername " +
                    "FROM superpower;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                List<String> superpowerList = Arrays.asList(rs.getString("powername").split(","));
                superheroes.add(new SuperheroModel(superpowerList));
            }
            return superheroes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addSuperheroToDatabase(SuperheroModel model) throws SQLException {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/superheroes", "root", "Qhr96wmr2k")) {
            // ID's
            int cityId = 0;
            int heroId = 0;
            List<Integer> powerIDs = new ArrayList<>();

            // find city_id
            String SQL1 = "select cityid from city where cityname = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL1);
            pstmt.setString(1, model.getCityName());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cityId = rs.getInt("cityid");
                System.out.println("CityID = " + cityId);
            }

            // insert row in superhero table
            String SQL2 = "insert into superhero (superheroname, realname, creationyear, cityid) " +
                    "values(?, ?, ?, ?);";
            pstmt = con.prepareStatement(SQL2, Statement.RETURN_GENERATED_KEYS); // return autoincremented key
            pstmt.setString(1, model.getName());
            pstmt.setString(2, model.getRealName());
            pstmt.setInt(3, model.getIntroductionDate());
            pstmt.setInt(4, cityId);
            int rows = pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                heroId = rs.getInt(1);
                System.out.println("HeroID = " + heroId);
            }

            // find power_ids
            String SQL3 = "select superpowerID from superpower where powername = ?;";
            pstmt = con.prepareStatement(SQL3);
            for (String power : model.getSuperpowerList()) {
                pstmt.setString(1, power);
                rs = pstmt.executeQuery();
                System.out.println(rs);
                if (rs.next()) {
                    System.out.println("check");
                    powerIDs.add(rs.getInt("superpowerID"));
                    System.out.println("PowerIDs = " + powerIDs.get(0));
                }
            }

            // insert entries in superhero_powers join table
            String SQL4 = "insert into superhero_power values (?,?);";
            pstmt = con.prepareStatement(SQL4);
            for (int i = 0; i < powerIDs.size(); i++) {
                pstmt.setInt(1, heroId);
                pstmt.setInt(2, powerIDs.get(i));
                System.out.println(powerIDs.get(i));
                rows = pstmt.executeUpdate();
            }
        }
    }
}