package com.muhardin.endy.training.ws.aplikasi.absen.rest;

import com.muhardin.endy.training.ws.aplikasi.absen.Karyawan;
import com.muhardin.endy.training.ws.aplikasi.absen.service.AbsenService;
import java.util.List;
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
    
    @RequestMapping("/rest/karyawan/")
    @ResponseBody
    public List<Karyawan> daftarKaryawan(
            @RequestParam(required = false)Integer start,
            @RequestParam(required = false)Integer rows
            ){
        
        if(start == null){
            start = 0;
        }
        
        if(rows == null){
            rows = 10;
        }
        
        return absenService.semuaKaryawan(start, rows);
    }
}
