package com.muhardin.endy.training.ws.aplikasi.absen.rest;

import com.muhardin.endy.training.ws.aplikasi.absen.exception.DataSudahAdaException;
import com.muhardin.endy.training.ws.aplikasi.absen.exception.DataTidakDitemukanException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AbsenExceptionHandler {
    @ExceptionHandler(DataTidakDitemukanException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Map<String, Object> handleDataTidakDitemukan(){
        Map<String, Object> hasil = new LinkedHashMap<>();
        hasil.put("success", false);
        hasil.put("keterangan", "Data tidak ditemukan");
        return hasil;
    }
    
    @ExceptionHandler(DataSudahAdaException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public Map<String, Object> handleDataSudahAda(){
        Map<String, Object> hasil = new LinkedHashMap<>();
        hasil.put("success", false);
        hasil.put("keterangan", "Data sudah ada dalam database");
        return hasil;
    }
}
