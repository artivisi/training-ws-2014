/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.muhardin.endy.training.ws.aplikasi.absen.rest.client;

import java.util.HashMap;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author endy
 */
public class AbsenRestClient {
    private RestTemplate restTemplate = new RestTemplate();
    
    public Karyawan cariKaryawanByNip(Integer nip){
        String url = "http://localhost:8080/aplikasi-absen-rest/api/rest/karyawan/"+nip;
        Karyawan k = restTemplate
                .getForObject(url, Karyawan.class, new HashMap<Object, Object>());
        return k;
    }
    
    public static void main(String[] args) {
        AbsenRestClient arc = new AbsenRestClient();
        Karyawan k = arc.cariKaryawanByNip(999);
        System.out.println("NIP : "+ k.getNip());
        System.out.println("Nama : "+ k.getNama());
        System.out.println("Tanggal Lahir : "+ k.getTanggalLahir());
    }
}
