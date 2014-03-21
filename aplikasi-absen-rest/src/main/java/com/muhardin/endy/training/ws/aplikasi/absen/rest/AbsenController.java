package com.muhardin.endy.training.ws.aplikasi.absen.rest;

import com.muhardin.endy.training.ws.aplikasi.absen.Karyawan;
import com.muhardin.endy.training.ws.aplikasi.absen.service.AbsenService;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AbsenController {
    
    @Autowired
    private AbsenService absenService;
    
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class, 
                new CustomDateEditor(new SimpleDateFormat("dd-MM-yyyy"), true));
    }
    
    @RequestMapping("/karyawan/info")
    public ModelMap infoKaryawan(){
        ModelMap mm = new ModelMap();
        mm.addAttribute("karyawan", absenService.cariByNip(999));
        return mm;
    }
    
    @RequestMapping("/karyawan/list")
    public ModelMap daftarKaryawan(){
        ModelMap mm = new ModelMap();
        mm.addAttribute("screenAktif", "karyawan");
        mm.addAttribute("daftarKaryawan", absenService.cariByNama("100"));
        return mm;
    }
    
    @RequestMapping(value="/karyawan/form", method = RequestMethod.GET)
    public ModelMap displayFormEditKaryawan(){
        return new ModelMap("karyawan", new Karyawan());
    }
    
    @RequestMapping(value="/karyawan/form", method = RequestMethod.POST)
    public String prosesFormKaryawan(@ModelAttribute @Valid Karyawan k, 
            BindingResult errors, 
            SessionStatus status,
            @RequestParam("foto") MultipartFile foto) throws Exception {
        System.out.println("NIP : "+k.getNip());
        System.out.println("Nama : "+k.getNama());
        System.out.println("Email : "+k.getEmail());
        System.out.println("Tanggal Lahir : "+k.getTanggalLahir());
        
        if(errors.hasErrors()) {
            List<FieldError> daftarError = errors.getFieldErrors();
            for (FieldError fieldError : daftarError) {
                System.out.println(fieldError.getField() +" : " +fieldError.getCode());
            }
            return "karyawan/form";
        }
        
        System.out.println("Nama file : "+foto.getName());
        System.out.println("Nama asli file : "+foto.getOriginalFilename());
        System.out.println("Ukuran : "+foto.getSize());
        
        // simpan file
        String tempFolder = System.getProperty("java.io.tmpdir");
        String tujuan = tempFolder + File.separator + foto.getOriginalFilename();
        foto.transferTo(new File(tujuan));
        
        System.out.println("Foto telah disimpan di "+tujuan);
        
        status.setComplete();
        return "redirect:list";
    }
}
