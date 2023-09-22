package fr.fortytwo.cinema.servlets;

import fr.fortytwo.cinema.models.User;
import fr.fortytwo.cinema.services.UsersService;
import org.springframework.context.ApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet("/signIn")
public class SignInServlet extends HttpServlet {
    private UsersService usersService;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        ApplicationContext applicationContext = (ApplicationContext) servletContext.getAttribute("springContext");
        this.usersService = applicationContext.getBean(UsersService.class);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/signIn.html");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Optional<User> optionalUser = this.usersService.signIn(email, password);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            if (session.getAttribute("auths") == null)
                session.setAttribute("auths", new ArrayList<String>());

            List<String> auths = (List<String>) session.getAttribute("auths");
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
            DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH:mm");
            auths.add(dateFormatter.format(currentTime) + "-" + hourFormatter.format(currentTime) + "-" + request.getRemoteAddr());
            response.sendRedirect("/profile");
        } else
            response.sendRedirect("/signIn");
    }
}
