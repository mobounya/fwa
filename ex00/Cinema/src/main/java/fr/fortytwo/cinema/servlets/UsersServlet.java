package fr.fortytwo.cinema.servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/users")
// do you really need spring-boot or spring ?????????????
public class UsersServlet extends HttpServlet {
    public UsersServlet() {

    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(
                "<html><head><title>Hello World Servlet</title></head>");
        out.println("<body>");
        ServletContext ctx = getServletConfig().getServletContext();
        if (ctx.getAttribute("springContext") != null)
            out.println("<h1> loaded </h1>");
        else
            out.println("<h1> not loaded </h1>");
        out.println("</body></html>");
        out.close();
    }
}
