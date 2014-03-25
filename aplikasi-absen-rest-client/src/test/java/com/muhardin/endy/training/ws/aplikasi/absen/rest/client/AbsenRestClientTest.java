package com.muhardin.endy.training.ws.aplikasi.absen.rest.client;

import com.muhardin.endy.training.ws.aplikasi.absen.Karyawan;
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

    private void displayKaryawan(Karyawan k) {
        System.out.println("NIP : " + k.getNip());
        System.out.println("Nama : " + k.getNama());
        System.out.println("Tanggal Lahir : " + k.getTanggalLahir());
    }
}
