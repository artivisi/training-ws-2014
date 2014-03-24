package com.muhardin.endy.training.ws.aplikasi.absen.rest;

import com.muhardin.endy.training.ws.aplikasi.absen.Karyawan;
import com.muhardin.endy.training.ws.aplikasi.absen.service.AbsenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AbsenRestController {
    @Autowired
    private AbsenService absenService;
    
    @RequestMapping("/rest/karyawan/{nip}")
    @ResponseBody
    public Karyawan infoKaryawan(@PathVariable Integer nip){
        return absenService.cariByNip(nip);
    }
}
