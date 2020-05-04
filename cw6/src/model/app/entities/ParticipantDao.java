package model.app.entities;

import model.app.Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ParticipantDao {
    private Connection connection;
    private Controller controller;
    public ParticipantDao(Controller controller) throws IOException, SQLException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/resources/bike_trip.properties"));
        connection = DriverManager.getConnection(properties.getProperty("dburl"),properties.getProperty("user"),properties.getProperty("password"));
        this.controller = controller;
    }

    public List<Participant> getAllParticipants(long tripId) throws SQLException {
        List<Participant> participants = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            statement = connection.prepareStatement("select * from participant where trip_id = ?");
            statement.setString(1,String.valueOf(tripId));
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                participants.add(convertRowToParticipant(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(statement,resultSet);
        }

        return participants;
    }

    public void deleteAllCyclistParticipants(int cyclistId) throws SQLException {
        PreparedStatement statement = null;
        try{
            statement=connection.prepareStatement("delete from participant where cyclist_id = ?");
            statement.setString(1,String.valueOf(cyclistId));
            int rowsAffected = statement.executeUpdate();
            controller.goodAlert("Usunięto " + rowsAffected + " uczestnictw!");
        } catch (SQLException s){
            controller.badAlert(s.getErrorCode() + ": " + s.getMessage());
            return;
        }
    }

    public void insertParticipant(int cyclistId, int tripId){
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("insert into participant(cyclist_id, trip_id) values (?,?)");
            statement.setString(1,String.valueOf(cyclistId));
            statement.setString(2,String.valueOf(tripId));
            statement.executeUpdate();
            controller.goodAlert("Pomyślnie dodano uczestnika do wycieczki!");
        } catch (SQLException s) {
            controller.badAlert(s.getErrorCode() + ": " + s.getMessage());
            return;
        }
    }

    public void deleteAllTripParticipants(int cyclistId) throws SQLException {
        PreparedStatement statement = null;
        try{
            statement=connection.prepareStatement("delete from participant where trip_id = ?");
            statement.setString(1,String.valueOf(cyclistId));
            statement.executeUpdate();
        } catch (SQLException s){
            controller.badAlert(s.getErrorCode() + ": " + s.getMessage());
            return;
        }
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


    private Participant convertRowToParticipant(ResultSet myRs) throws SQLException {

        int trip_id = myRs.getInt("trip_id");
        int cyclist_id = myRs.getInt("cyclist_id");

        Participant tempParticipant = new Participant(cyclist_id, trip_id);

        return tempParticipant;
    }

}
