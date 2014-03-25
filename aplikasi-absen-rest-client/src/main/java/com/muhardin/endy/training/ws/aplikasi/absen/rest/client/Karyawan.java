/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.muhardin.endy.training.ws.aplikasi.absen.rest.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author endy
 */
public class Karyawan {
    private Integer nip;
    private String nama;
    private Date tanggalLahir;
    private String email;
    private List<String> telp = new ArrayList<String>();
    private Boolean aktif;
    private String foto;

    public Integer getNip() {
        return nip;
    }

    public void setNip(Integer nip) {
        this.nip = nip;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Date getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getTelp() {
        return telp;
    }

    public void setTelp(List<String> telp) {
        this.telp = telp;
    }

    public Boolean isAktif() {
        return aktif;
    }

    public void setAktif(Boolean aktif) {
        this.aktif = aktif;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
    
    
}
