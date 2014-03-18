package com.muhardin.endy.training.ws.aplikasi.absen.rest;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class KalkulatorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String num1 = req.getParameter("x");
        String num2 = req.getParameter("y");
        
        Integer hasil = Integer.valueOf(num1) + Integer.valueOf(num2);
        
        resp.setContentType("text/html");
        PrintWriter output = resp.getWriter();
        
        output.println("<b>Hello</b> World <br>");
        output.println(num1 + " + " +num2 + " = "+hasil);
        
        
        output.close();
    }
    
}
