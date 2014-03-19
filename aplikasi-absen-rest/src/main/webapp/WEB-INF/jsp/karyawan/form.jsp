<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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

        <title>Edit Karyawan</title>
    </head>
    <body>
        <div class="container">
            <h1>Edit Karyawan</h1>

            <form class="form-horizontal" method="post">

                <s:bind path="karyawan.nip">
                    <div class="form-group <c:out value=" ${status.error ? 'has-error' : '' }" />">
                        <label for="nip" class="col-sm-2 control-label">NIP</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="nip" name="nip" placeholder="NIP">
                        </div>
                        <c:if test="${status.error}">
                            <span class="glyphicon glyphicon-remove form-control-feedback"></span>
                            <span class="help-inline">${status.errorMessage}</span>
                        </c:if>
                    </div>
                </s:bind>
                <s:bind path="karyawan.nama">
                    <div class="form-group <c:out value=" ${status.error ? 'has-error' : '' }" />">
                        <label for="nama" class="col-sm-2 control-label">Nama</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="nama" name="nama" placeholder="Nama">
                        </div>
                        <c:if test="${status.error}">
                            <span class="glyphicon glyphicon-remove form-control-feedback"></span>
                            <span class="help-inline">${status.errorMessage}</span>
                        </c:if>
                    </div>
                </s:bind>
                <s:bind path="karyawan.email">
                    <div class="form-group <c:out value=" ${status.error ? 'has-error' : '' }" />">
                        <label for="email" class="col-sm-2 control-label">Email</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="email" name="email" placeholder="Email">
                        </div>
                        <c:if test="${status.error}">
                            <span class="glyphicon glyphicon-remove form-control-feedback"></span>
                            <span class="help-inline">${status.errorMessage}</span>
                        </c:if>
                    </div>
                </s:bind>
                <s:bind path="karyawan.tanggalLahir">
                    <div class="form-group <c:out value=" ${status.error ? 'has-error' : '' }" />">
                        <label for="tanggalLahir" class="col-sm-2 control-label">Tanggal Lahir</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="tanggalLahir" name="tanggalLahir" placeholder="dd-MM-yyyy">
                        </div>
                        <c:if test="${status.error}">
                            <span class="glyphicon glyphicon-remove form-control-feedback"></span>
                            <span class="help-inline">${status.errorMessage}</span>
                        </c:if>
                    </div>
                </s:bind>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-6">
                        <button type="submit" class="btn btn-primary">Simpan</button>
                        <button type="reset" class="btn btn-default">Batal</button>
                    </div>
                </div>
            </form>

        </div>
    </body>
</html>
