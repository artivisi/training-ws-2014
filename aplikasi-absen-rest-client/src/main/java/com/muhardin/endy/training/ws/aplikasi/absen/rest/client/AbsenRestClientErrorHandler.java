package com.muhardin.endy.training.ws.aplikasi.absen.rest.client;

import com.muhardin.endy.training.ws.aplikasi.absen.exception.DataSudahAdaException;
import com.muhardin.endy.training.ws.aplikasi.absen.exception.DataTidakDitemukanException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class AbsenRestClientErrorHandler implements ResponseErrorHandler {

    List<HttpStatus> statusSukses = Arrays.asList(HttpStatus.OK, HttpStatus.CREATED);
    
    @Override
    public boolean hasError(ClientHttpResponse chr) throws IOException {
        return !statusSukses.contains(chr.getStatusCode());
    }

    @Override
    public void handleError(ClientHttpResponse chr) throws IOException {
        if(HttpStatus.CONFLICT.equals(chr.getStatusCode())) {
            throw new DataSudahAdaException();
        }
        if(HttpStatus.NOT_FOUND.equals(chr.getStatusCode())){
            throw new DataTidakDitemukanException();
        }
    }
    
}
