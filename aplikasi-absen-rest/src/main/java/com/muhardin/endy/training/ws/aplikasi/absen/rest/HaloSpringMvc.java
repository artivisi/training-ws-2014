package com.muhardin.endy.training.ws.aplikasi.absen.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HaloSpringMvc {
    
    @RequestMapping("/halo")
    public ModelMap halo(){
        ModelMap mm = new ModelMap();
        mm.addAttribute("halo", "Hallo Spring MVC");
        return mm;
    }
}
