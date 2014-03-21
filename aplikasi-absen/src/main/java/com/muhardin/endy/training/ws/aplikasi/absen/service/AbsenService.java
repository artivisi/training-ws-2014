package com.muhardin.endy.training.ws.aplikasi.absen.service;

import com.muhardin.endy.training.ws.aplikasi.absen.Karyawan;
import java.util.List;

public interface AbsenService {
    public void simpan(Karyawan k);
    public Karyawan cariByNip(Integer nip);
    public List<Karyawan> cariByNama(String nama);
    public List<Karyawan> semuaKaryawan(Integer start, Integer rows);
}
