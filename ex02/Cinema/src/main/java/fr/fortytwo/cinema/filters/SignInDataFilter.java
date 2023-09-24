package fr.fortytwo.cinema.filters;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/signIn")
@Order(3)
public class SignInDataFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (httpRequest.getMethod().equalsIgnoreCase("POST")) {
            String email = httpRequest.getParameter("email");
            String password = httpRequest.getParameter("password");
            if (email == null || email.isEmpty() || password == null || password.isEmpty())
                httpResponse.setStatus(400);
            else
                chain.doFilter(request, response);
        } else
            chain.doFilter(request, response);
    }
}
