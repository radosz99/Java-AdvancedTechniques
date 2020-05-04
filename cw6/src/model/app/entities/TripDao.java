package model.app.entities;

import model.app.Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Date;

public class TripDao {
    private Connection connection;
    private Controller controller;
    public TripDao(Controller controller) throws IOException, SQLException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/resources/bike_trip.properties"));
        connection = DriverManager.getConnection(properties.getProperty("dburl"),properties.getProperty("user"),properties.getProperty("password"));
        this.controller = controller;
    }

    public List<Trip> getAllTrips() throws SQLException {
        List<Trip> trips = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from trip");
            while (resultSet.next()) {
                Trip tempTrip = convertRowToTrip(resultSet);
                trips.add(tempTrip);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close(statement,resultSet);
        }


        return trips;
    }

    public List<Trip> getTripsByAuthor(int authorId) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Trip> trips = new ArrayList<>();
        try{
            statement = connection.prepareStatement("select * from trip where author_id = ?");
            statement.setString(1,String.valueOf(authorId));
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Trip tempTrip = convertRowToTrip(resultSet);
                trips.add(tempTrip);
            }
        } catch(SQLException s){
            s.printStackTrace();
        }
        return trips;
    }
    public void changeTripId(int oldId, int newId) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("update trip set id = ? where id = ?");
            statement.setString(1, String.valueOf(newId));
            statement.setString(2, String.valueOf(oldId));
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected ==0){
                controller.warningAlert("Nie ma wycieczki o takim id!");
                return;
            }
        } catch (SQLException s){
            controller.badAlert(s.getErrorCode() + ": " + s.getMessage());
            return;
        }
        controller.goodAlert("Pomyślnie zmieniono!");
    }

    public boolean insertTrip(String name, int authorId, Date dateOfBirth){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = null;
        try {
            date = dateFormat.format(dateOfBirth);
        } catch (NullPointerException n) {
            controller.badAlert("Zły format daty");
        }
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement("insert into trip (name, author_id, date) values (?,?,?)");
            statement.setString(1, name);
            statement.setString(2,String.valueOf(authorId));
            statement.setString(3,date);
            statement.executeUpdate();
        } catch (SQLException e){
            System.err.println(e.getErrorCode() + ": " + e.getMessage());
            return false;
        }
        return true;
    }


    public void deleteTripByAuthor(int authorId) throws SQLException, IOException {
        List<Trip> trips = null;
        ParticipantDao participantDao = new ParticipantDao(controller);
        PreparedStatement statement = null;
        try {
            trips = getTripsByAuthor(authorId);
            for (Trip trip : trips){
                participantDao.deleteAllTripParticipants(trip.getId());
            statement = connection.prepareStatement("delete from trip where id = ?");
            statement.setString(1, String.valueOf(trip.getId()));
            statement.executeUpdate();
            }
            controller.goodAlert("Usunięto " + trips.size() + " wycieczek kolarza o id " + authorId + "!");
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

    private Trip convertRowToTrip(ResultSet myRs) throws SQLException {

        int id = myRs.getInt("id");
        String name = myRs.getString("name");
        int author_id = myRs.getInt("author_id");
        Date date = myRs.getDate("date");

        Trip tempTrip = new Trip(id, author_id, name, date);

        return tempTrip;
    }

}
