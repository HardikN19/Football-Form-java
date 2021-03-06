package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import model.Registration;

public class RegistrationDao {
	 
	//To register data into database.
	public int registerDetails(Registration register) throws ClassNotFoundException {
		 	boolean status=false;  
		 
		//Query for insertion of new data into database.
		 String INSERT_USERS_SQL = "INSERT INTO register" +
	            "  (username, firstname, lastname, code, contact, email, age, team, position, address, pincode, country, state, city) VALUES " +
	            " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	        
		//Query for validation of data where username and contact must be different.	
		String VALIDATE = "SELECT * FROM register WHERE username=? or contact=?";
	        int result = 0;

	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        
	        //To check duplicate username and contact number
	        try (Connection connection = DriverManager
	            .getConnection("jdbc:sqlserver://INDIA-44P0HC2;databaseName=FootballForm", "sa", "hanathgr");

	            PreparedStatement ps = connection.prepareStatement(VALIDATE)) {
	        	ps.setString(1,register.getUsername());  
	        	ps.setLong(2,register.getContact());  
	        	ResultSet rs=ps.executeQuery();  
	        	status=rs.next();  
	        	
	        	//If already exist
	        	if(status) {
	        		System.out.print("Already Exists");
	        	}
	        	
	        	//If not exist then store data into database
	        	else {
	        		try (Connection conn = DriverManager
		    	            .getConnection("jdbc:sqlserver://INDIA-44P0HC2;databaseName=FootballForm", "sa", "hanathgr");
		    	            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_USERS_SQL)) {
		        		System.out.print("connected!");
			            preparedStatement.setString(1, register.getUsername());
			        	preparedStatement.setString(2, register.getFirstName());
			            preparedStatement.setString(3, register.getLastName());
			            preparedStatement.setString(4, register.getCode());
			            preparedStatement.setLong(5, register.getContact());
			            preparedStatement.setString(6, register.getEmail());
			            preparedStatement.setString(7, register.getAge());
			            preparedStatement.setString(8, register.getTeam());
			            preparedStatement.setString(9, register.getPosition());
			            preparedStatement.setString(10, register.getAddress());
			            preparedStatement.setLong(11, register.getPincode());
			            preparedStatement.setString(12, register.getCountry());
			            preparedStatement.setString(13, register.getState());
			            preparedStatement.setString(14, register.getCity());
			            System.out.println(preparedStatement);
			            
			            // Execute the query or update query
			            result = preparedStatement.executeUpdate();
			            return result;
		        	}
    	            // process SQL exception
	        		catch (SQLException e) {
	    	            printSQLException(e);
	    	        }
	        	}
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
            return result;
	    }

	    private void printSQLException(SQLException ex) {
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
	    
	    //To update the details of already existing user based on username.
	    public int updateDetails(Registration register) throws ClassNotFoundException {
	    	
		   String INSERT_USERS_SQL = "UPDATE register set firstname=?, lastname=?, code=?, contact=?, email=?, age=?, team=?, position=?, address=?, pincode=?, country=?, state=?, city=? WHERE username=? " ;
	        int result = 0;

	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

	        try (Connection connection = DriverManager
	            .getConnection("jdbc:sqlserver://INDIA-44P0HC2;databaseName=FootballForm", "sa", "hanathgr");

	        	
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
}
