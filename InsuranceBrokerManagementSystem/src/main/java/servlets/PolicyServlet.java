package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Policy;
import services.PolicyService; // Assuming you have a PolicyService similar to CustomerService
import crud.service.CrudService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet implementation class PolicyServlet
 */
@WebServlet("/policies") // The URL mapping for this servlet
public class PolicyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CrudService<Policy> policyService; // Specify Policy as a generic type

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PolicyServlet() {
        super();
        policyService = new CrudService<>(Policy.class); // Initialize with the Policy class type
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Logic to retrieve all policies
        List<Policy> policies = policyService.getAll(); // Use getAll() method to get policies
        
        // Set content type for response
        response.setContentType("text/html");
        
        // Get PrintWriter to write response
        PrintWriter out = response.getWriter();
        
        // Write HTML response
        out.println("<html>");
        out.println("<head><title>Policy List</title></head>");
        out.println("<body>");
        out.println("<h1>Policy List</h1>");
        out.println("<table border='1'>");
        out.println("<tr><th>ID</th><th>Customer ID</th><th>Type</th><th>Premium</th></tr>");
        
        // Iterate over policies and write each one to the table
        for (Policy policy : policies) {
            out.println("<tr>");
            out.println("<td>" + policy.getId() + "</td>");
            out.println("<td>" + policy.getCustomerId() + "</td>");
            out.println("<td>" + policy.getType() + "</td>");
            out.println("<td>" + policy.getPremium() + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("<a href='addPolicy.html'>Add New Policy</a>"); // Link to add new policy
        out.println("</body>");
        out.println("</html>");

        out.close(); // Close PrintWriter
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Logic to add a new policy
        String id = request.getParameter("id");
        String customerId = request.getParameter("customerId");
        String type = request.getParameter("type");
        double premium = Double.parseDouble(request.getParameter("premium"));

        Policy policy = new Policy(id, customerId, type, premium);
        policyService.add(policy); // Use the generic add method from CrudService

        response.sendRedirect("policies"); // Redirect to policy list
    }
}
