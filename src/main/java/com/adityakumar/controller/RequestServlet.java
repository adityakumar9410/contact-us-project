package com.adityakumar.controller;

import com.adityakumar.dao.ContactUsDao;
import com.adityakumar.dao.RequestDao;
import com.adityakumar.model.Request;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/requests")
public class RequestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Get all requests data from database
        RequestDao requestDao = new RequestDao();
        List<Request> requests = requestDao.getAllRequests();

        HttpSession session = request.getSession();
        session.setAttribute("requests", requests);
        request.getRequestDispatcher("requests.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean isUpdated;
        int  requestId =Integer.parseInt(request.getParameter("requestId"));
        String isActive = request.getParameter("isActive");
        RequestDao requestDao = new RequestDao();
        if (isActive.equals("true")) {
            isUpdated = requestDao.updateRequest(requestId, false);
        } else {
            isUpdated = requestDao.updateRequest(requestId, true);
        }
        if (isUpdated) {
            response.sendRedirect("requests");
        }
        response.getWriter().println("Error while updating");
    }
}


