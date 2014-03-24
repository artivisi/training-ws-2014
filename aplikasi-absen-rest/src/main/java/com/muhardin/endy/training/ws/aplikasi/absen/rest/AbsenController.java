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
    public ModelMap infoKaryawan(@RequestParam Integer nip){
        ModelMap mm = new ModelMap();
        mm.addAttribute("karyawan", absenService.cariByNip(nip));
        return mm;
    }
    
    @RequestMapping("/karyawan/list")
    public ModelMap daftarKaryawan(
            @RequestParam(required = false)Integer start,
            @RequestParam(required = false)Integer rows
            ){
        
        if(start == null){
            start = 0;
        }
        
        if(rows == null){
            rows = 10;
        }
        
        ModelMap mm = new ModelMap();
        mm.addAttribute("screenAktif", "karyawan");
        mm.addAttribute("daftarKaryawan", absenService.semuaKaryawan(start, rows));
        return mm;
    }
    
    @RequestMapping(value="/karyawan/form", method = RequestMethod.GET)
    public ModelMap displayFormEditKaryawan(@RequestParam(required = false) Integer nip){
        Karyawan k = null;
        
        if(nip != null){
            k = absenService.cariByNip(nip);
        }
        
        if(k == null){
            k = new Karyawan();
        }
        
        return new ModelMap("karyawan", k);
    }
    
    @RequestMapping(value="/karyawan/form", method = RequestMethod.POST)
    public String prosesFormKaryawan(@ModelAttribute @Valid Karyawan k, 
            BindingResult errors, 
            SessionStatus status,
            @RequestParam("fileFoto") MultipartFile foto) throws Exception {
        System.out.println("NIP : "+k.getNip());
        System.out.println("Nama : "+k.getNama());
        System.out.println("Email : "+k.getEmail());
        System.out.println("Tanggal Lahir : "+k.getTanggalLahir());
        
        // validasi foto kosong
        if(foto.isEmpty()){
            errors.rejectValue("foto", "foto.kosong");
            return "karyawan/form";
        }
        
        // kalau ada error, jangan dilanjutkan
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
        
        absenService.simpan(k);
        
        status.setComplete();
        return "redirect:list";
    }
    
    @RequestMapping("/karyawan/hapus")
    public String hapus(@RequestParam Integer nip){
        absenService.hapusKaryawan(nip);
        return "redirect:list";
    }
}
