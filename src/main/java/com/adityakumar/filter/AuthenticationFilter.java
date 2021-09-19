package com.adityakumar.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest  httpServletRequest = (HttpServletRequest) request;

        if(httpServletRequest.getRequestURI().startsWith("/ContactUsProject/requests")){
            HttpSession session = httpServletRequest.getSession();
            if(session.getAttribute("username")==null){
                 httpServletRequest.getRequestDispatcher("login.jsp").forward(httpServletRequest, response);
            }
        }

        chain.doFilter(httpServletRequest, response);
    }
}
