package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Claim;
import services.ClaimService; // Assuming you have a ClaimService similar to CustomerService
import crud.service.CrudService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet implementation class ClaimServlet
 */
@WebServlet("/claims") // The URL mapping for this servlet
public class ClaimServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CrudService<Claim> claimService; // Specify Claim as a generic type

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClaimServlet() {
        super();
        claimService = new CrudService<>(Claim.class); // Initialize with the Claim class type
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Logic to retrieve all claims
        List<Claim> claims = claimService.getAll(); // Use getAll() method to get claims
        
        // Set content type for response
        response.setContentType("text/html");
        
        // Get PrintWriter to write response
        PrintWriter out = response.getWriter();
        
        // Write HTML response
        out.println("<html>");
        out.println("<head><title>Claim List</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }");
        out.println("h1 { color: #333; }");
        out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        out.println("th, td { padding: 10px; text-align: left; border: 1px solid #ddd; }");
        out.println("th { background-color: #295F98; color: #EAE4DD; }");
        out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
        out.println("tr:hover { background-color: #ddd; }");
        out.println("a { display: inline-block; margin-top: 20px; padding: 10px 15px; background-color: #295F98; color: #EAE4DD; text-decoration: none; border-radius: 5px; }");
        out.println("a:hover { background-color: #EAE4DD; color:#295F98; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Claim List</h1>");
        out.println("<table>");
        out.println("<tr><th>ID</th><th>Policy ID</th><th>Description</th><th>Amount</th><th>Status</th></tr>");
        
        
        // Iterate over claims and write each one to the table
        for (Claim claim : claims) {
            out.println("<tr>");
            out.println("<td>" + claim.getId() + "</td>");
            out.println("<td>" + claim.getPolicyId() + "</td>");
            out.println("<td>" + claim.getDescription() + "</td>");
            out.println("<td>" + claim.getAmount() + "</td>");
            out.println("<td>" + claim.getStatus() + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("<a href='addClaim.html'>Add New Claim</a>"); // Link to add new claim
        out.println("</body>");
        out.println("</html>");

        out.close(); // Close PrintWriter
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Logic to add a new claim
        String id = request.getParameter("id");
        String policyId = request.getParameter("policyId");
        String description = request.getParameter("description");
        String status = request.getParameter("status");
        double amount = Double.parseDouble(request.getParameter("amount"));

        Claim claim = new Claim(id, policyId, description, status, amount);
        claimService.add(claim); // Use the generic add method from CrudService

        response.sendRedirect("claims"); // Redirect to claim list
    }
}
