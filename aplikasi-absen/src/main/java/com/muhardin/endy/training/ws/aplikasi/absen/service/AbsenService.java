package com.muhardin.endy.training.ws.aplikasi.absen.service;

import com.muhardin.endy.training.ws.aplikasi.absen.Karyawan;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AbsenService {
    public void simpan(Karyawan k){
        System.out.println("Simpan karyawan");
    }
    
    public Karyawan cariByNip(Integer nip){
        return createKaryawan();
    }
    
    public List<Karyawan> cariByNama(String nama){
        List<Karyawan> hasil = new ArrayList<>();
        hasil.add(createKaryawan());
        hasil.add(createKaryawan());
        return hasil;
    }
    
    private Karyawan createKaryawan(){
        Karyawan k = new Karyawan();
        k.setNip(987654321);
        k.setNama("Endy Muhardin");
        k.setTanggalLahir(new Date());
        k.setEmail("endy.muhardin@gmail.com");
        k.getTelp().add("+6281298000468");
        k.getTelp().add("+62218611859");
        return k;
    }
}
