<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">

        <!-- Optional theme -->
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">

        <!-- Latest compiled and minified JavaScript -->
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
        <title>Hasil Penjumlahan</title>
    </head>
    <body>
        <div class="container">

            <div class="jumbotron">
                <h1>Hasil Penjumlahan</h1>



                <%= request.getAttribute("num1")%> 
                + <%= request.getAttribute("num2")%> 
                = <%= request.getAttribute("hasil")%>

            </div>
        </div>
    </body>
</html>
