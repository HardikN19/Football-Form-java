package controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.RegistrationDao;
import model.Registration;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	 	private static final long serialVersionUID = 1L;
	    private RegistrationDao registrationDao = new RegistrationDao();
		private Registration reg = new Registration();
	    public RegistrationServlet() {
	    	super();
	    }
	    
	    //This function used to get data from DB using fetch button.
	    //We are passing username as a parameter.
	    //It will return the data from DB if username matches from DB.
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    	    throws ServletException, IOException {
	    	String username=request.getParameter("username");  
		    boolean status = false; 
		    
		    try {
		    	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
				Connection con=DriverManager.getConnection("jdbc:sqlserver://INDIA-44P0HC2;databaseName=FootballForm", "sa", "hanathgr");  
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
	    
	    //This function used To store data into database.
	    //It is used to store new data in DB.
	    //If the user is already present with same username or contact number it will not store that same data.
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
	        
	        //Passing the value of registration to registerDao.
	        //Functionality of already present user is check in Dao file.
	        try {
	        	registrationDao.registerDetails(registration);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
	    	dispatcher.forward(request, response);
	    }
	    
	    
	    private static String inputStreamToString(InputStream inputStream) {
			Scanner scanner = new Scanner(inputStream, "UTF-8");
			return scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
		}
	    
	    
	    //This function is used to update already present userdata.
	    //It will read the json file sended by update button.
	    @Override
		protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	String body = inputStreamToString(request.getInputStream());
	    	System.out.println("body: " + body);
	    	GsonBuilder builder = new GsonBuilder();
	    	builder.setPrettyPrinting();
	    	Gson gson = builder.create();
	    	Registration register = gson.fromJson(body, Registration.class);
	    	System.out.println(register);
	    	System.out.println(register.getAddress());
	    	try {
	    		registrationDao.updateDetails(register);
	    		response.sendRedirect("index.html");
	    	} catch (ClassNotFoundException e) {
	    	// TODO Auto-generated catch block
	    	e.printStackTrace();
	    	}
		}

}
