package com.artivisi.training.ws.storage.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FolderController {
    
    @RequestMapping(method = RequestMethod.GET, value = "/folders/")
    @ResponseBody
    public List<String> daftarFolder(){
        List<String> hasil = new ArrayList<String>();
        hasil.add("/dokumen");
        hasil.add("/dokumen/kantor");
        hasil.add("/dokumen/pribadi");
        hasil.add("/foto");
        hasil.add("/musik");
        hasil.add("/video");
        return hasil;
    }
}
