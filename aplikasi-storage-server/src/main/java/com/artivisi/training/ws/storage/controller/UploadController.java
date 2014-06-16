package com.artivisi.training.ws.storage.controller;

import com.artivisi.training.ws.domain.InfoFile;
import com.artivisi.training.ws.helper.FileHelper;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
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
    private static final String folderUpload = "/upload";
    
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
        
        if(metadata.get("sha1Sum") == null){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        
        InfoFile info = new InfoFile();
        info.setNama(metadata.get("nama"));
        info.setContentType(metadata.get("Content-Type"));
        info.setSha1Sum(metadata.get("sha1Sum"));
        
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
        
        String folderTujuan = request.getServletContext()
                .getRealPath(folderUpload+File.separator+id);
        
        // buat dulu parent folder bila tidak ada
        new File(folderTujuan).mkdirs();
        
        String fileTujuan = folderTujuan
                + File.separator + info.getNama()
                + "-"
                + String.format("%09d", (start / 1000));
        
        if(data != null && data.trim().length() > 0){
            System.out.println("Data size : "+data.length());
            
            // konversi balik (decode) dari String ke byte[] dengan Base64
            byte[] hasil = DatatypeConverter.parseBase64Binary(data);
            Files.write(hasil, new File(fileTujuan));            
            System.out.println("Menulis hasil upload ke "+fileTujuan);
        }
        
        if(end.equals(Long.valueOf(partTotal[1]))) {
            System.out.println("File lengkap, gabungkan");
            File hasil = FileHelper.joinFiles(folderTujuan, folderTujuan, info.getNama());
            System.out.println("File hasil gabungan : "+hasil.getAbsolutePath());
            Boolean checksumOk = FileHelper.verifySum(hasil, info.getSha1Sum());
            System.out.println("Hasil verifikasi checksum : "+checksumOk);
            if(checksumOk){
                return new ResponseEntity<String>(null, 
                responseHeaders, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<String>(null, 
                responseHeaders, HttpStatus.BAD_REQUEST);
            }
            
        } 
        
        return new ResponseEntity<String>(null, 
                responseHeaders, HttpStatus.ACCEPTED);
    }
}
