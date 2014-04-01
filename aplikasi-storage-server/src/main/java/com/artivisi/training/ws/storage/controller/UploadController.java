package com.artivisi.training.ws.storage.controller;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    
    
    @RequestMapping(value = "/upload/", method = RequestMethod.PUT)
    public ResponseEntity<String> upload(@RequestParam("upload_id") String id, 
            @RequestHeader("Content-Length") Long contentLength, 
            @RequestHeader("Content-Range") String contentRange
            ){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Range", "0-1000");

        System.out.println("Content-Length : "+contentLength);
        System.out.println("Content-Range : "+contentRange);
        
        // parsing content range
        String[] partTotal = contentRange.split("/");
        if(partTotal.length != 2){
            ResponseEntity<String> response = new ResponseEntity<String>(null, 
                responseHeaders, HttpStatus.BAD_REQUEST);
            return response;
        }
        String[] startEnd = partTotal[0].split("-");
        if(startEnd.length != 2){
            ResponseEntity<String> response = new ResponseEntity<String>(null, 
                responseHeaders, HttpStatus.BAD_REQUEST);
            return response;
        }
        
        Long start = Long.valueOf(startEnd[0]);
        Long end = Long.valueOf(startEnd[1]);
        
        System.out.println("Client upload start at byte : "+start);
        System.out.println("Client upload end at byte : "+end);
        System.out.println("Client upload size : "+contentLength);
        System.out.println("Client upload total size : "+partTotal[1]);
        
        ResponseEntity<String> response = new ResponseEntity<String>(null, 
                responseHeaders, HttpStatus.RESUME_INCOMPLETE);
        return response;
    }
}