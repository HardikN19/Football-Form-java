package controller;

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

//To update data from DB
@WebServlet("/updateData")
public class UpdateDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RegistrationDao updationDao = new RegistrationDao();
    
    public UpdateDataServlet() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Server at: ").append(request.getContextPath());
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
    	dispatcher.forward(request, response);
	}

	//Store the values from web page into variables.
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	        out.print(address);
	        
	        //Send the value to updateDao 
	        try {
	        	updationDao.updateDetails(registration);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
	    	dispatcher.forward(request, response);

	}

}
