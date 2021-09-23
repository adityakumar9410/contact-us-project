package com.adityakumar.controller;

import com.adityakumar.dao.ContactUsDao;
import com.adityakumar.model.Request;
import com.adityakumar.model.User;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/contactus")
public class ContactUsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("contactus.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Collect all form data
        String fullName = request.getParameter("name").trim();
        String email = request.getParameter("email").trim();
        String message = request.getParameter("message").trim();

        String insertMsg = "";
         if(fullName.length() == 0 || email.length() ==0  || message.length() == 0){
              insertMsg = "Please enter all fields ";
         }

         if(fullName.length() >0 && email.length()> 0 && message.length()>0){
             //Fill in Request object
             Request  contactRequest = new Request();
             contactRequest.setFullName(fullName);
             contactRequest.setEmail(email);
             contactRequest.setMessage(message);
             //Call the Dao layer and add data to DB
             ContactUsDao contactUsDao = new ContactUsDao();
             int rowsAdded = contactUsDao.saveContact(contactRequest);
             if (rowsAdded == 0) {
                 insertMsg = "Sorry an error occurred while  adding data";
             } else {
                 insertMsg = "Success, data added successfully";
             }
         }

        request.setAttribute("insertMsg", insertMsg);
        request.getRequestDispatcher("contactus.jsp").forward(request, response);
    }
}