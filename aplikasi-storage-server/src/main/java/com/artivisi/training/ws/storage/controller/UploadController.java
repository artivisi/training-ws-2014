package com.artivisi.training.ws.storage.controller;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UploadController {
    
    private final SecureRandom random = new SecureRandom();

    @RequestMapping(value = "/upload/", method = RequestMethod.POST)
    public ResponseEntity<String> generateUploadId(
            @RequestBody(required = false) Map<String, String> metadata, 
            HttpServletRequest request
            ) {
        // tampilkan metadata
        if(metadata != null){
            System.out.println("==== Metadata Start ====");
            for (String key : metadata.keySet()) {
                System.out.println(key + " => " + metadata.get(key));
            }
            System.out.println("==== Metadata End ====");
        }
        
        String uploadId = new BigInteger(130, random).toString(32);
        System.out.println("Generate random upload id : "+uploadId);
        
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", request.getRequestURL()+"?upload_id="+uploadId);

        ResponseEntity<String> response = new ResponseEntity<String>(null, responseHeaders, HttpStatus.OK);
        return response;

    }
}
