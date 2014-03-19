package com.muhardin.endy.training.ws.aplikasi.absen.rest;

import com.muhardin.endy.training.ws.aplikasi.absen.Karyawan;
import com.muhardin.endy.training.ws.aplikasi.absen.service.AbsenService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class AbsenController {
    
    private AbsenService absenService = new AbsenService();
    
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class, 
                new CustomDateEditor(new SimpleDateFormat("dd-MM-yyyy"), true));
    }
    
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
    public String prosesFormKaryawan(@ModelAttribute @Valid Karyawan k, 
            BindingResult errors, 
            SessionStatus status){
        System.out.println("NIP : "+k.getNip());
        System.out.println("Nama : "+k.getNama());
        System.out.println("Email : "+k.getEmail());
        System.out.println("Tanggal Lahir : "+k.getTanggalLahir());
        
        if(errors.hasErrors()) {
            List<FieldError> daftarError = errors.getFieldErrors();
            for (FieldError fieldError : daftarError) {
                System.out.println(fieldError.getField() +" : " +fieldError.getDefaultMessage());
            }
            return "karyawan/form";
        }
        
        status.setComplete();
        return "redirect:list";
    }
}
