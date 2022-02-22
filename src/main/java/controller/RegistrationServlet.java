package controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import dao.RegistrationDao;
import model.Registration;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	 private static final long serialVersionUID = 1L;
	    private RegistrationDao registrationDao = new RegistrationDao();

	    public RegistrationServlet() {
	    	super();
	    }
	    
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    	    throws ServletException, IOException {
	    	String username=request.getParameter("username");  
		    boolean status = false; 
		    
		    try {
		    	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
				Connection con=DriverManager.getConnection("jdbc:sqlserver://INDIA-44P0HC2;databaseName=FootballForm", "<name>", "<password>");  
				PreparedStatement ps = con.prepareStatement("select * from register where username=?");  
				ps.setString(1,username);  
				ResultSet rs=ps.executeQuery();
				status = rs.next();
				
				//If data available then send it to web page using AJAX
				if(status) {
					JSONArray array = new JSONArray();
			        JSONObject user1 = new JSONObject();
			        user1.put("status", 1);
			        user1.put("username", rs.getString(1));
			        user1.put("firstName", rs.getString(2));
			        user1.put("lastName", rs.getString(3));
			        user1.put("code", rs.getString(4));
			        user1.put("contact", rs.getLong(5));
			        user1.put("email", rs.getString(6));
			        user1.put("age", rs.getString(7));
			        user1.put("team", rs.getString(8));
			        user1.put("position", rs.getString(9));
			        user1.put("address", rs.getString(10));
			        user1.put("pincode", rs.getLong(11));
			        user1.put("country", rs.getString(12));
			        user1.put("state", rs.getString(13));
			        user1.put("city", rs.getString(14));
			        array.put(user1);
			        response.setContentType("application/json");		        
			      ServletOutputStream output = response.getOutputStream();
			      output.write(array.toString().getBytes());
			      output.flush();
			      output.close();
				}
				else {}
		    }catch(Exception e){System.out.println(e);}
	    }
	    
	    // To store data into database
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
			PrintWriter out = response.getWriter(); 	
	        String username = request.getParameter("username");
	        String firstName = request.getParameter("firstName");
	        String lastName = request.getParameter("lastName");
	        String code = request.getParameter("code");
	        String contact1 = request.getParameter("contact");
	        long contact = Long.parseLong(contact1);
	        String email = request.getParameter("email");
	        String age = request.getParameter("age");
	        String team = request.getParameter("team");
	        String position = request.getParameter("position");
	        String address = request.getParameter("address");
	        String pincode1 = request.getParameter("pincode");
	        long pincode = 0;
	        try {
	        	pincode = Long.parseLong(pincode1);
	        } catch (NumberFormatException e) {
	            pincode = 0;
	        }
	        String country = request.getParameter("country");
	        String state = request.getParameter("state");
	        String city = request.getParameter("city");
	        out.print(address);

	        Registration registration = new Registration();
	        registration.setFirstName(firstName);
	        registration.setLastName(lastName);
	        registration.setUsername(username);
	        registration.setCode(code);
	        registration.setContact(contact);
	        registration.setEmail(email);
	        registration.setAge(age);
	        registration.setTeam(team);
	        registration.setPosition(position);
	        registration.setAddress(address);
	        registration.setPincode(pincode);
	        registration.setCountry(country);
	        registration.setState(state);
	        registration.setCity(city);
	        
	        //Passing the value of registration to registerDao
	        try {
	        	registrationDao.registerDetails(registration);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
	    	dispatcher.forward(request, response);
	    }

}
