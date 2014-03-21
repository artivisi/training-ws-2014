<%-- 
    Document   : list
    Created on : Mar 19, 2014, 10:55:35 AM
    Author     : endy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

        <title>Daftar Karyawan</title>
    </head>
    <body>
        <div class="container">

            <h1>Daftar Karyawan</h1>

            <table class="table table-condensed table-striped">
                <thead>
                    <tr>
                        <th>Nama</th>
                        <th>Email</th>
                        <th>Tanggal Lahir</th>
                        <th>Telp</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="k" items="${daftarKaryawan}">
                        <tr>
                            <td>${k.nama}</td>
                            <td>${k.email}</td>
                            <td>${k.tanggalLahir}</td>
                            <td>
                                <ul>
                                    <c:forEach var="t" items="${k.telp}">
                                        <li>${t}</li>
                                    </c:forEach>
                                </ul>
                                
                            </td>
                            <td>
                                <a href="info?nip=${k.nip}" class="btn btn-default"><span class="glyphicon glyphicon-search"></span>&nbsp;lihat</a>
                                <a href="form?nip=${k.nip}" class="btn btn-default"><span class="glyphicon glyphicon-pencil"></span>&nbsp;edit</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>


        </div>
    </body>
</html>
