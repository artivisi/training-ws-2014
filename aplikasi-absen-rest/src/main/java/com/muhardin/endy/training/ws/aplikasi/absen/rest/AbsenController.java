package com.muhardin.endy.training.ws.aplikasi.absen.rest;

import com.muhardin.endy.training.ws.aplikasi.absen.service.AbsenService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AbsenController {
    
    private AbsenService absenService = new AbsenService();
    
    @RequestMapping("/karyawan/info")
    public ModelMap infoKaryawan(){
        ModelMap mm = new ModelMap();
        mm.addAttribute("karyawan", absenService.cariByNip(100));
        return mm;
    }
}
