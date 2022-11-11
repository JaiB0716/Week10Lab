package filters;

import dataaccess.UserDB;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;


@WebFilter(filterName = "AdminFilter", servletNames = {"AdminServlet"})
public class AdminFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

            //Retrieve the user email to compare credentials to the server
            
             HttpServletRequest httpRequest = (HttpServletRequest)request;
             HttpSession session = httpRequest.getSession();
             String email = (String)session.getAttribute("email");
             
             //access the user database 
             
             UserDB userDB = new UserDB();
             
             //Create a new user and and get the email
             
             User user = userDB.get(email);
             
             // varify that the role to the entered email is a system admin and redirect as needed 
             
             if(user.getRole().getRoleId() == 2) {
               HttpServletResponse httpResponse = (HttpServletResponse) response; 
               httpResponse.sendRedirect("notes");
               return;
             }
             
            chain.doFilter(request, response);

        }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       
    }

    @Override
    public void destroy() {
        
    }
    
}
