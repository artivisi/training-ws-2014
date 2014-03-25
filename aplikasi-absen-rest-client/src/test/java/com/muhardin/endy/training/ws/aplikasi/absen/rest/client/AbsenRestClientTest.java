package com.muhardin.endy.training.ws.aplikasi.absen.rest.client;

import com.muhardin.endy.training.ws.aplikasi.absen.Karyawan;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class AbsenRestClientTest {

    AbsenRestClient absenRestClient = new AbsenRestClient();

    @Test
    public void testCariByNip() {
        Karyawan k = absenRestClient.cariKaryawanByNip(999);
        assertNotNull(k);
        displayKaryawan(k);
    }

    @Test
    public void testCariSemuaKaryawan() {
        List<Karyawan> semua = absenRestClient.semuaKaryawan();
        assertNotNull(semua);
        assertFalse(semua.isEmpty());
        for (Karyawan karyawan : semua) {
            displayKaryawan(karyawan);
        }
    }
    
    //@Test
    public void testSimpanBaru(){
        Karyawan kx = new Karyawan();
        kx.setNip(777);
        kx.setNama("Dadang");
        kx.setEmail("dadang@artivisi.com");
        kx.setTanggalLahir(new Date());
        kx.setAktif(true);
        
        absenRestClient.simpan(kx);
        
        Karyawan k = absenRestClient.cariKaryawanByNip(777);
        assertNotNull(k);
        displayKaryawan(k);
    }
    
    @Test
    public void testUpdate(){
        Karyawan k = absenRestClient.cariKaryawanByNip(999);
        assertNotNull(k);
        
        k.setNama("Mohammad Sholihin");
        absenRestClient.update(k);
        
        Karyawan kx = absenRestClient.cariKaryawanByNip(999);
        assertNotNull(kx);
        assertEquals("Mohammad Sholihin", kx.getNama());
    }

    private void displayKaryawan(Karyawan k) {
        System.out.println("NIP : " + k.getNip());
        System.out.println("Nama : " + k.getNama());
        System.out.println("Tanggal Lahir : " + k.getTanggalLahir());
    }
}
