package filters;

import dataaccess.UserDB;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;

public class AdminFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        HttpSession session = httpRequest.getSession();
        String email = (String) session.getAttribute("email");
        
        //if email is null, redirecct to login page
        if (email == null) {
            httpResponse.sendRedirect("login");
            return;
           
        }
        else {
            UserDB udb = new UserDB();
            User user = udb.get(email);
            
            //if user's role ID is not 1 (admin), redirect to /notes
            if (user.getRole().getRoleId() != 1) {
                httpResponse.sendRedirect("notes");
                return;
            }
        }
        chain.doFilter(request, response);
    }
    
     @Override
    public void destroy() {        
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {        
        
    }
}