package fr.fortytwo.cinema.filters;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/signUp")
@Order(3)
public class SignUpDataFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (httpRequest.getMethod().equalsIgnoreCase("POST")) {
            String email = httpRequest.getParameter("email");
            String firstName = httpRequest.getParameter("firstName");
            String lastName = httpRequest.getParameter("lastName");
            String phoneNumber = httpRequest.getParameter("phoneNumber");
            String password = httpRequest.getParameter("password");
            if (email == null || email.isEmpty() || firstName == null || firstName.isEmpty() || lastName == null ||
                    lastName.isEmpty() || phoneNumber == null || phoneNumber.isEmpty() || password == null || password.isEmpty() ||
                    !StringUtils.isNumeric(phoneNumber)) {
                httpResponse.setStatus(400);
            } else
                chain.doFilter(request, response);
        } else
            chain.doFilter(request, response);
    }
}
