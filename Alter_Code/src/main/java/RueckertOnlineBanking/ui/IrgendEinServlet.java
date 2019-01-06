package RueckertOnlineBanking.ui;

import RueckertOnlineBanking.entity.Address;
import RueckertOnlineBanking.entity.Customer;
import RueckertOnlineBanking.entity.EMailAddress;
import RueckertOnlineBanking.service.CustomerService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(urlPatterns = {"/IrgendEinServlet2"})
public class IrgendEinServlet extends HttpServlet {

    @Inject
    private CustomerService customerService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        ServletOutputStream out = resp.getOutputStream();




        out.println("<html>");
        out.println("<head/>");
        out.println("<body>");
        out.println("<h1>Kundenverwaltung</h1>");



        // ---------- GET CUSTOMER DATA ---------- //
        out.println("<form method=\"post\">");

        out.println("<h3>Allgemeine Informationen</h3>");

        out.println("Vorname:");
        out.println("<input type=\"text\" name=\"firstname\" value=\"");
        out.println("\"/><br/>");
        out.println("Nachnahme:");
        out.println("<input type=\"text\" name=\"lastname\" value=\"");
        out.println("\"/><br/>");
        out.println("E-Mail Adresse:");
        out.println("<input type=\"email\" name=\"email\" value=\"");
        out.println("\"/><br/>");
        out.println("Telefonnummer:");
        out.println("<input type=\"number\" name=\"phoneNumber\" value=\"");
        out.println("\"/><br/>");
        out.println("Geburtsdatum:");
        out.println("<input type=\"date\" name=\"dateOfBirth\" value=\"");
        out.println("\"/><br/>");

        out.println("<h3>Adressdaten</h3>");

        out.println("Straße:");
        out.println("<input type=\"text\" name=\"street\" value=\"");
        out.println("\"/><br/>");
        out.println("Hausnummer:");
        out.println("<input type=\"text\" name=\"houseNumber\" value=\"");
        out.println("\"/><br/>");
        out.println("Postleitzahl:");
        out.println("<input type=\"number\" name=\"postalcode\" value=\"");
        out.println("\"/><br/>");
        out.println("Ort:");
        out.println("<input type=\"text\" name=\"place\" value=\"");
        out.println("\"/><br/>");


        out.println("<input type=\"submit\" value=\"Registrieren\" name=\"action\"/><br/>");
        out.println("<input type=\"submit\" value=\"Alle Kunden laden\" name=\"action\"/><br/>");
        out.println("</form>");


        if("Registrieren".equals(req.getParameter("action"))) {
            out.println("innerhalb der Registrieren Methode!");
            EMailAddress eMailAddress = new EMailAddress(req.getParameter("email"));

            Address address = new Address(req.getParameter("street"), req.getParameter("houseNumber"), Integer.parseInt(req.getParameter("postalcode")), req.getParameter("place"));

            String firstname = req.getParameter("firstname");
            String lastname = req.getParameter("lastname");
            Integer phoneNumber = Integer.parseInt(req.getParameter("phoneNumber"));
            Date dateOfBirth = null;
            try {
                dateOfBirth = new SimpleDateFormat("yyyy-MM-DD").parse(req.getParameter("dateOfBirth"));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Customer customer = new Customer(firstname, lastname, eMailAddress, phoneNumber, dateOfBirth, address);

            customer = customerService.registerCustomer(eMailAddress, address, customer);

            out.println("successful created customer!");
            out.println(customer.toString());
        } else if("Alle Kunden laden".equals(req.getParameter("action"))){
            List<Customer> all = customerService.getCustomers();
            out.println("<table>");
            for(Customer c : all) {
                out.println("<tr>");
                out.println("<td>" + c.getFirstname() + "</td>");
                out.println("<td>" + c.getLastname() + "</td>");
                out.println("<td><form method=\"post\">" +
                        "<input type=\"hidden\" name=\"firstname\" value=\"" + c.getFirstname() + "\">" +
                        "<input type=\"submit\" name=\"action\" value=\"laden\">&nbsp;" +
                        "<input type=\"submit\" name=\"action\" value=\"loeschen\"></form></td>");
                out.println("<tr>");
            }
            out.println("</table>");

        } else {
            out.println("ausserhalb der Registrieren Methode!");
        }
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