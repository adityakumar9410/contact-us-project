package com.adityakumar.controller;

import com.adityakumar.dao.ContactUsAppDao;
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
        ContactUsAppDao contactDao = new ContactUsAppDao();
        List<Request> requests = contactDao.getAllRequests();

        HttpSession session = request.getSession();
        session.setAttribute("requests", requests);
        request.getRequestDispatcher("requests.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean isUpdated;
        int  contactId =Integer.parseInt(request.getParameter("requestId"));
        String contactState = request.getParameter("isActive");
        ContactUsAppDao dao = new ContactUsAppDao();
        if (contactState.equals("true")) {
            isUpdated = dao.updateContact(contactId, false);
        } else {
            isUpdated = dao.updateContact(contactId, true);
        }
        if (isUpdated) {
            response.sendRedirect("requests");
        }
        response.getWriter().println("Error while updating");
    }
}


