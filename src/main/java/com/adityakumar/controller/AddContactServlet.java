package com.adityakumar.controller;

import com.adityakumar.dao.ContactUsAppDao;
import com.adityakumar.model.Contact;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/contactus")
public class AddContactServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("contactus.jsp").forward(req,resp);
    }

    @Override
    protected void  doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //Collect all form data
        String fullName = request.getParameter("name");
        String email = request.getParameter("email");
        String message  = request.getParameter("message");

        //Fill in Contact object
        Contact contact = new Contact(fullName, email, message);

        //Call the Dao layer and add data to DB
        ContactUsAppDao  contactUsDao = new ContactUsAppDao();
        int rows =   contactUsDao.addContactToDb(contact);

        String insertMsg = "";
        if(rows==0){
            insertMsg = "Sorry an error occurred while  adding data";
        }else{
            insertMsg = "Success, data added successfully";
        }

        response.getWriter().println(insertMsg);
    }
}