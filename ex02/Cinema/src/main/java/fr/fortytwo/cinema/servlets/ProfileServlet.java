package fr.fortytwo.cinema.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/profile.jsp");
        dispatcher.forward(request, response);
    }

    public String getFileType(String fileName) {
        int pointIndex = fileName.indexOf('.');
        if (pointIndex == -1)
            return null;
        String type = fileName.substring(pointIndex + 1);
        if (type.equals("jpeg") || type.equals("jpg"))
            return "image/jpeg";
        else if (type.equals("png"))
            return "image/png";
        else if (type.equals("svg"))
            return "image/svg";
        else if (type.equals("avif"))
            return "image/avif";
        else
            return null;
    }
}
