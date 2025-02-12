package alexsysoy.connection.v1;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/demo";
        String user = "postgres";
        String password = System.getenv("DB_PASSWORD");

        if (password == null || password.isEmpty()) {
            System.out.println("there is no password!");
            return;
        }

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection == null) {
                System.out.println("connected to the postgresql server unsuccessfully!");
                return;
            }
            System.out.println("------------Statement------------");
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM bookings.airports");
                while (resultSet.next()) {
                    String aircraftCode = resultSet.getString("airport_code");
                    String airportName = resultSet.getString("airport_name");
                    String city = resultSet.getString("city");
                    System.out.println(aircraftCode + ": " + airportName + ": " + city);
                }
            }
            System.out.println("------------PreparedStatement------------");
            String query = "SELECT * FROM bookings.airports WHERE city = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, "Псков");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String aircraftCode = resultSet.getString("airport_code");
                    String airportName = resultSet.getString("airport_name");
                    String city = resultSet.getString("city");
                    System.out.println(aircraftCode + ": " + airportName + ": " + city);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
