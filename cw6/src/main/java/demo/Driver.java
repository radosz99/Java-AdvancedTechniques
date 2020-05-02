package demo;

import java.sql.*;
public class Driver {
    public static void main(String[] args) {
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/world?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root");
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM world.city;");
            while(resultSet.next()){
                System.out.println(resultSet.getString("ID") + ", " + resultSet.getString("Name") + ", " + resultSet.getString("Population"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
