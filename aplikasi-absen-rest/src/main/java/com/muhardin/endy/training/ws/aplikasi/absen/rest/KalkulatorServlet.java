package com.muhardin.endy.training.ws.aplikasi.absen.rest;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class KalkulatorServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String num1 = req.getParameter("x");
        String num2 = req.getParameter("y");
        
        Integer hasil = Integer.valueOf(num1) + Integer.valueOf(num2);
        req.setAttribute("num1", num1);
        req.setAttribute("num2", num2);
        req.setAttribute("hasil", hasil);
        req.getRequestDispatcher("/hasil.jsp").forward(req, resp);
    }
    
}
