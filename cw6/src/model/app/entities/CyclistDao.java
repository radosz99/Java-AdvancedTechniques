package model.app.entities;

import model.app.Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class CyclistDao {
    private Connection connection;
    private Controller controller;
    public CyclistDao(Controller controller) throws IOException, SQLException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/resources/bike_trip.properties"));
        connection = DriverManager.getConnection(properties.getProperty("dburl"),properties.getProperty("user"),properties.getProperty("password"));
        this.controller = controller;
    }

    public List<Cyclist> getAllCyclists() throws SQLException {
        List<Cyclist> cyclists = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from cyclist");
            while (resultSet.next()) {
                Cyclist tempCyclist = convertRowToCyclist(resultSet);
                cyclists.add(tempCyclist);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close(statement,resultSet);
        }


        return cyclists;
    }

    public Cyclist getCyclistById(long id){
        Cyclist cyclist = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            statement = connection.prepareStatement("select * from cyclist where id = ?");
            statement.setString(1, String.valueOf(id));
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                cyclist = convertRowToCyclist(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cyclist;
    }

    public void changeCyclistId(int oldId, int newId){
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement("SET foreign_key_checks = 0;" +
                    "update cyclist set id = ? where id = ?;" +
                    "update trip set author_id = ? where author_id = ?;" +
                    "update participant set cyclist_id = ? where cyclist_id = ?;" +
                            "SET foreign_key_checks = 1;"
                    );
            statement.setString(1,String.valueOf(newId));
            statement.setString(2,String.valueOf(oldId));
            statement.setString(3,String.valueOf(newId));
            statement.setString(4,String.valueOf(oldId));
            statement.setString(5,String.valueOf(newId));
            statement.setString(6,String.valueOf(oldId));
            statement.executeUpdate();
        } catch (SQLException e) {
            controller.badAlert(e.getErrorCode() + ": " + e.getMessage());
            return;
        }
    }
    public void deleteCyclistById(int id){
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement("SET foreign_key_checks = 0;" +
                    "delete from cyclist where id = ?;" +
                    "delete from trip where author_id = ?;" +
                    "delete from participant where cyclist_id = ?;" +
                    "SET foreign_key_checks = 1;"
            );
            statement.setString(1,String.valueOf(id));
            statement.setString(2,String.valueOf(id));
            statement.setString(3,String.valueOf(id));
            statement.executeUpdate();
        } catch (SQLException e) {
            controller.badAlert(e.getErrorCode() + ": " + e.getMessage());
            return;
        }
        controller.goodAlert("Pomyślnie usunięto");
    }

    public boolean insertCyclist(String name, String surname, Date dateOfBirth){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = null;
        try {
            date = dateFormat.format(dateOfBirth);
        } catch (NullPointerException n) {
            controller.badAlert("Zły format daty");
        }
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement("insert into cyclist (name, surname, date_of_birth) values (?,?,?)");
            statement.setString(1, name);
            statement.setString(2,surname);
            statement.setString(3,date);
            statement.executeUpdate();
        } catch (SQLException e){
            controller.badAlert(e.getErrorCode() + ": " + e.getMessage());
            return false;
        }
        return true;
    }
    private static void close(Connection myConn, Statement myStmt, ResultSet myRs)
            throws SQLException {

        if (myRs != null) {
            myRs.close();
        }

        if (myStmt != null) {

        }

        if (myConn != null) {
            myConn.close();
        }
    }

    private void close(Statement myStmt, ResultSet myRs) throws SQLException {
        close(null, myStmt, myRs);
    }

    private Cyclist convertRowToCyclist(ResultSet myRs) throws SQLException {

        int id = myRs.getInt("id");
        String name = myRs.getString("name");
        String surname = myRs.getString("surname");
        Date date = myRs.getDate("date_of_birth");

        Cyclist tempCyclist = new Cyclist(id, name, surname, date);

        return tempCyclist;
    }



}
