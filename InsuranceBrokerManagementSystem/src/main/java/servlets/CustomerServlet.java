package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Customer;
import services.CustomerService;
import crud.service.CrudService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet implementation class CustomerServlet
 */
public class CustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CrudService<Customer> customerService; // Specify Customer as a generic type

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerServlet() {
        super();
        customerService = new CrudService<>(Customer.class); // Initialize with the Customer class type
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Logic to retrieve all customers
        List<Customer> customers = customerService.getAll(); // Use getAll() method to get customers
        
        // Set content type for response
        response.setContentType("text/html");
        
        // Get PrintWriter to write response
        PrintWriter out = response.getWriter();
        
        // Write HTML response
        out.println("<html>");
        out.println("<head><title>Customer List</title></head>");
        out.println("<body>");
        out.println("<h1>Customer List</h1>");
        out.println("<table border='1'>");
        out.println("<tr><th>ID</th><th>Name</th><th>Email</th><th>Phone</th></tr>");
        
        // Iterate over customers and write each one to the table
        for (Customer customer : customers) {
            out.println("<tr>");
            out.println("<td>" + customer.getCustomerId() + "</td>");
            out.println("<td>" + customer.getName() + "</td>");
            out.println("<td>" + customer.getEmail() + "</td>");
            out.println("<td>" + customer.getPhone() + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("<a href='addCustomer.html'>Add New Customer</a>"); // Link to add new customer
        out.println("</body>");
        out.println("</html>");

        out.close(); // Close PrintWriter
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Logic to add a new customer
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        Customer customer = new Customer(id, name, email, phone);
        customerService.add(customer); // Use the generic add method from CrudService

        response.sendRedirect("customers"); // Redirect to customer list
    }
}
