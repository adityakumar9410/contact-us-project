package com.adityakumar.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          HttpSession session = request.getSession();
          session.removeAttribute("username");
          HttpSession httpSession = request.getSession();
          String logoutMessage = "Logged out successfully";
          httpSession.setAttribute("logoutMsg", logoutMessage);
          response.sendRedirect("login.jsp");
    }
}
