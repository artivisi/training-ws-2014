package com.artivisi.training.ws.storage.controller;

import com.artivisi.training.ws.domain.InfoFile;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UploadController {
    
    private final SecureRandom random = new SecureRandom();
    private static final String folderUpload = "/data";
    
    private final Map<String, InfoFile> databaseInfoFile 
            = new ConcurrentHashMap<String, InfoFile>();

    @RequestMapping(value = "/upload/", method = RequestMethod.POST)
    public ResponseEntity<String> generateUploadId(
            @RequestBody Map<String, String> metadata, 
            HttpServletRequest request
            ) {
        
        // tampilkan metadata
        System.out.println("==== Metadata Start ====");
        for (String key : metadata.keySet()) {
            System.out.println(key + " => " + metadata.get(key));
        }
        System.out.println("==== Metadata End ====");
        
        // ambil nama file dan content type
        if(metadata.get("nama") == null){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        
        if(metadata.get("Content-Type") == null){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        
        InfoFile info = new InfoFile();
        info.setNama(metadata.get("nama"));
        info.setContentType(metadata.get("Content-Type"));
        
        String uploadId = new BigInteger(130, random).toString(32);
        System.out.println("Generate random upload id : "+uploadId);
        
        databaseInfoFile.put(uploadId, info);
        
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", request.getRequestURL()+"?upload_id="+uploadId);

        ResponseEntity<String> response = new ResponseEntity<String>(null, responseHeaders, HttpStatus.OK);
        return response;

    }
    
    
    @RequestMapping(value = "/upload/", method = RequestMethod.PUT)
    public ResponseEntity<String> upload(@RequestParam("upload_id") String id, 
            @RequestHeader("Content-Length") Long contentLength, 
            @RequestHeader("Content-Range") String contentRange,
            @RequestBody(required = false) String data, // file binary diencode dengan Base64
            HttpServletRequest request
            ) throws IOException{
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Range", "0-1000");

        System.out.println("Content-Length : "+contentLength);
        System.out.println("Content-Range : "+contentRange);
        
        // parsing content range
        String[] partTotal = contentRange.split("/");
        if(partTotal.length != 2){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        String[] startEnd = partTotal[0].split("-");
        if(startEnd.length != 2){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        
        Long start = Long.valueOf(startEnd[0]);
        Long end = Long.valueOf(startEnd[1]);
        
        System.out.println("Client upload start at byte : "+start);
        System.out.println("Client upload end at byte : "+end);
        System.out.println("Client upload size : "+contentLength);
        System.out.println("Client upload total size : "+partTotal[1]);
        
        InfoFile info = databaseInfoFile.get(id);
        if(info == null){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        
        String fileTujuan = request.getServletContext()
                .getRealPath(folderUpload)
                + File.separator + id
                + File.separator + info.getNama();
        
        if(data != null && data.trim().length() > 0){
            System.out.println("Data size : "+data.length());
            
            // konversi balik (decode) dari String ke byte[] dengan Base64
            byte[] hasil = DatatypeConverter.parseBase64Binary(data);
            
            File tujuan = new File(fileTujuan);
            FileUtils.writeByteArrayToFile(tujuan, hasil);
            System.out.println("Menulis hasil upload ke "+fileTujuan);
        }
        
        ResponseEntity<String> response = new ResponseEntity<String>(null, 
                responseHeaders, HttpStatus.RESUME_INCOMPLETE);
        return response;
    }
}
