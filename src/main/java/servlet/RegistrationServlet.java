package servlet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	    	response.getWriter().append("Server at: ").append(request.getContextPath());
	    	
	    	RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
	    	dispatcher.forward(request, response);
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
