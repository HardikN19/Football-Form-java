package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Registration;

public class UpdationDao {
	//To update data into DB where username is same as given by user.
	public int updateDetails(Registration register) throws ClassNotFoundException {
        String INSERT_USERS_SQL = "UPDATE register set firstname=?, lastname=?, code=?, contact=?, email=?, age=?, team=?, position=?, address=?, pincode=?, country=?, state=?, city=? WHERE username=? " ;
        int result = 0;

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        try (Connection connection = DriverManager
            .getConnection("jdbc:sqlserver://INDIA-44P0HC2;databaseName=FootballForm", "<name>", "<password>");

        	
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
        	
        	System.out.print("connected!");
        	preparedStatement.setString(1, register.getFirstName());
            preparedStatement.setString(2, register.getLastName());
            preparedStatement.setString(3, register.getCode());
            preparedStatement.setLong(4, register.getContact());
            preparedStatement.setString(5, register.getEmail());
            preparedStatement.setString(6, register.getAge());
            preparedStatement.setString(7, register.getTeam());
            preparedStatement.setString(8, register.getPosition());
            preparedStatement.setString(9, register.getAddress());
            preparedStatement.setLong(10, register.getPincode());
            preparedStatement.setString(11, register.getCountry());
            preparedStatement.setString(12, register.getState());
            preparedStatement.setString(13, register.getCity());
            preparedStatement.setString(14, register.getUsername());



            System.out.println(preparedStatement);
            //Execute the query or update query
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // process SQL exception
            printSQLException(e);
        }
        return result;
    }

    private void printSQLException(SQLException ex) {
    	//To print error code and message if error occure. 
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

}
