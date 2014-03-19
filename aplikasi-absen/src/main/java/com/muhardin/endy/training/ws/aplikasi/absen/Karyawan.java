package com.muhardin.endy.training.ws.aplikasi.absen;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class Karyawan {
    
    @NotNull @Min(100)
    private Integer nip;
    
    @NotNull @NotEmpty @Size(min = 4)
    private String nama;
    
    @NotNull @Past
    private Date tanggalLahir;
    
    @Email
    private String email;
    private List<String> telp = new ArrayList<String>();
    private Boolean aktif;
    
    // getter setter generate dengan Netbeans 
    
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
    
}
