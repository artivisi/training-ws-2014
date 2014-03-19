package com.muhardin.endy.training.ws.aplikasi.absen.rest;

import com.muhardin.endy.training.ws.aplikasi.absen.Karyawan;
import com.muhardin.endy.training.ws.aplikasi.absen.service.AbsenService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AbsenController {
    
    private AbsenService absenService = new AbsenService();
    
    @RequestMapping("/karyawan/info")
    public ModelMap infoKaryawan(){
        ModelMap mm = new ModelMap();
        mm.addAttribute("karyawan", absenService.cariByNip(100));
        return mm;
    }
    
    @RequestMapping("/karyawan/list")
    public ModelMap daftarKaryawan(){
        ModelMap mm = new ModelMap();
        mm.addAttribute("daftarKaryawan", absenService.cariByNama("100"));
        return mm;
    }
    
    @RequestMapping(value="/karyawan/form", method = RequestMethod.GET)
    public void displayFormEditKaryawan(){
    }
    
    @RequestMapping(value="/karyawan/form", method = RequestMethod.POST)
    public String prosesFormKaryawan(@ModelAttribute Karyawan k){
        System.out.println("NIP : "+k.getNip());
        System.out.println("Nama : "+k.getNama());
        System.out.println("Email : "+k.getEmail());
        System.out.println("Tanggal Lahir : "+k.getTanggalLahir());
        return "redirect:list";
    }
}
