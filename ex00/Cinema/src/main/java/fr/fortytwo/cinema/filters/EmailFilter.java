package fr.fortytwo.cinema.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({"/signIn", "/signUp"})
public class EmailFilter implements Filter {
    private boolean isValidEmail(String email) {
        int index = email.indexOf('@');
        if (index <= 0 || index == (email.length() - 1))
            return false;
        return true;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (httpRequest.getMethod().equalsIgnoreCase("POST")) {
            String email = httpRequest.getParameter("email");
            if (email == null || email.isEmpty() || !isValidEmail(email))
                httpResponse.setStatus(400);
            else
                chain.doFilter(request, response);
        } else
            chain.doFilter(request, response);
    }
}
