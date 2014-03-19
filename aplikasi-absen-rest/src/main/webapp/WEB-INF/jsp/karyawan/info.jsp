<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">

        <!-- Optional theme -->
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">

        <!-- Latest compiled and minified JavaScript -->
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>

    </head>
    <body>
        <div class="container">

            <h1>Info Karyawan</h1>

            <table class="table table-striped table-bordered table-condensed">
                <tbody>
                    <tr>
                        <td>NIP</td>
                        <td>${karyawan.nip}</td>
                    </tr>
                    <tr>
                        <td>Nama</td>
                        <td>${karyawan.nama}</td>
                    </tr>
                    <tr>
                        <td>Email</td>
                        <td>${karyawan.email}</td>
                    </tr>
                    <tr>
                        <td>Tanggal Lahir</td>
                        <td>${karyawan.tanggalLahir}</td>
                    </tr>
                </tbody>
            </table>
        </div>

    </body>
</html>
