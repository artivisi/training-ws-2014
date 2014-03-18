<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hasil Penjumlahan</title>
    </head>
    <body>
        <h1>Hasil Penjumlahan</h1>
        
        <%= request.getAttribute("num1") %> 
        + <%= request.getAttribute("num2") %> 
        = <%= request.getAttribute("hasil") %>
        
    </body>
</html>
