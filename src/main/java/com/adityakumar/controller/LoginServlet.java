package com.adityakumar.controller;

import com.adityakumar.dao.LoginDao;
import com.adityakumar.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Collect form data from login form
        String userName = request.getParameter("username");
        String password = request.getParameter("password");

        //Call DAO  for admin validation
        LoginDao loginDao = new LoginDao();
        User user = new User();
        user.setUsername(userName);
        user.setPassword(password);
        boolean isValidAdmin = loginDao.validateUser(user);

        //Check if admin is valid
        if (isValidAdmin) {
            HttpSession session = request.getSession();
            session.setAttribute("username", userName);
            //Redirect to  the requests page
            response.sendRedirect("requests");
        } else {
            String errorMessage = "Invalid login credentials";
            request.setAttribute("error", errorMessage);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
