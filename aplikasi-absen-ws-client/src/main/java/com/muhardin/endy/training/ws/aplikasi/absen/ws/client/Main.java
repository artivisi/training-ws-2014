package com.muhardin.endy.training.ws.aplikasi.absen.ws.client;

import java.util.List;

public class Main {
    private AbsenWS_Service service = new AbsenWS_Service();
    private AbsenWS port = service.getAbsenWSPort();
    
    public void tambah(){
        try { 
            
            Integer num1 = 5;
            Integer num2 = 6;

            Integer result = port.tambah(num1, num2);
            System.out.println("Result = "+result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void cariKaryawanByNip(){
        Karyawan k = port.cariKaryawanByNip(123);
        System.out.println("NIP : "+k.getNip());
        System.out.println("Nama : "+k.getNama());
        System.out.println("Email : "+k.getEmail());
        System.out.println("Tanggal Lahir : "+k.getTanggalLahir());
        
        List<String> daftarTelp = k.getTelp();
        for (String telp : daftarTelp) {
            System.out.println("Telp : " +telp);
        }
    }
    
    public static void main(String[] args) {
        
        Main m = new Main();
        m.tambah();
        m.cariKaryawanByNip();

    }
}
