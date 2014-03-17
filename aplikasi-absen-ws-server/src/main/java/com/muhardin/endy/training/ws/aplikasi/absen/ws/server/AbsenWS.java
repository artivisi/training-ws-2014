package com.muhardin.endy.training.ws.aplikasi.absen.ws.server;

import com.muhardin.endy.training.ws.aplikasi.absen.Karyawan;
import com.muhardin.endy.training.ws.aplikasi.absen.service.AbsenService;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author endy
 */
@WebService(serviceName = "AbsenWS")
public class AbsenWS {
    private AbsenService absenService = new AbsenService();

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
    @WebMethod(operationName = "simpanKaryawan")
    public void simpanKaryawan(@WebParam(name="karyawan") Karyawan k){
        absenService.simpan(k);
    }
    
    @WebMethod(operationName = "cariKaryawanByNip")
    public Karyawan cariKaryawanByNip(@WebParam(name = "nip") Integer nip){
        return absenService.cariByNip(nip);
    }
    
    @WebMethod(operationName = "cariKaryawanByNama")
    public List<Karyawan> cariKaryawanByNama
            (@WebParam(name = "nama") String nama){
        return absenService.cariByNama(nama);
    }
}
