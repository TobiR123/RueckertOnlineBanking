package RueckertOnlineBanking.ui;

import RueckertOnlineBanking.entity.Customer;
import RueckertOnlineBanking.service.CustomerService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/IrgendEinServlet2"})
public class IrgendEinServlet extends HttpServlet {

    @Inject
    private CustomerService customerService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        ServletOutputStream out = resp.getOutputStream();

        Customer customer = new Customer();


        out.println("<html>");
        out.println("<head/>");
        out.println("<body>");
        out.println("<h1>Kundenverwaltung</h1>");


        out.println("<form method=\"post\">");
        if("Registrieren".equals(req.getParameter("action"))) {
            String[] namen = req.getParameter("student_name").split(" ");
            customer.setFirstname(namen[0]);
            customer.setLastname(namen[1]);
            customer = customerService.registerCustomer(customer);
        }
        out.println("</form>");

        out.println("success");

/*        List<Student> all = studentService.getAllStudents();
        out.println("<table>");
        for(Student stud : all) {
            out.println("<tr>");
            out.println("<td>" + stud.getMatrikelNr() + "</td>");
            out.println("<td>" + stud.getName() + "</td>");
            out.println("<td>" + stud.getStudiengang() + "</td>");
            out.println("<td><form method=\"post\"><input type=\"hidden\" name=\"matrikelnr\" value=\"" + stud.getMatrikelNr() + "\"><input type=\"submit\" name=\"action\" value=\"laden\">&nbsp;<input type=\"submit\" name=\"action\" value=\"loeschen\"></form></td>");
            out.println("<tr>");
        }
        out.println("</table>");*/



        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}