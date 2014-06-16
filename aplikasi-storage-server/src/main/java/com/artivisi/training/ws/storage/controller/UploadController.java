package com.artivisi.training.ws.storage.controller;

import com.artivisi.training.ws.domain.InfoFile;
import com.artivisi.training.ws.helper.FileHelper;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
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
    public ResponseEntity<Object> generateUploadId(
            @RequestBody Map<String, String> metadata, 
            HttpServletRequest request
            ) {
        
        // tampilkan metadata
        System.out.println("==== Metadata Start ====");
        for (String key : metadata.keySet()) {
            System.out.println(key + " => " + metadata.get(key));
        }
        System.out.println("==== Metadata End ====");
        
        Map<String, Object> responseBody = new HashMap<String, Object>();
        
        // ambil nama file dan content type
        if(metadata.get("nama") == null){
            responseBody.put("success", false);
            responseBody.put("message", "nama harus diisi");
            return new ResponseEntity<Object>(responseBody, HttpStatus.BAD_REQUEST);
        }
        
        if(metadata.get("Content-Type") == null){
            responseBody.put("success", false);
            responseBody.put("message", "Content-Type harus diisi");
            return new ResponseEntity<Object>(responseBody,HttpStatus.BAD_REQUEST);
        }
        
        if(metadata.get("sha1Sum") == null){
            responseBody.put("success", false);
            responseBody.put("message", "sha1Sum harus diisi");
            return new ResponseEntity<Object>(responseBody, HttpStatus.BAD_REQUEST);
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

        responseBody.put("success", true);
        responseBody.put("upload_id", uploadId);
        responseBody.put("upload_url", request.getRequestURL()+"?upload_id="+uploadId);
        ResponseEntity<Object> response = new ResponseEntity<Object>(responseBody, responseHeaders, HttpStatus.OK);
        return response;

    }
    
    
    @RequestMapping(value = "/upload/", method = RequestMethod.PUT)
    public ResponseEntity<Object> upload(@RequestParam("upload_id") String id, 
            @RequestHeader("Content-Length") Long contentLength, 
            @RequestHeader("Content-Range") String contentRange,
            @RequestBody(required = false) String data, // file binary diencode dengan Base64
            HttpServletRequest request
            ) throws IOException{
        
        Map<String, Object> responseBody = new HashMap<String, Object>();
        
        
        System.out.println("Content-Length : "+contentLength);
        System.out.println("Content-Range : "+contentRange);
        
        // parsing content range
        String[] partTotal = contentRange.split("/");
        if(partTotal.length != 2){
            responseBody.put("success", false);
            responseBody.put("message", "Content-Range format : start-end/total");
            return new ResponseEntity<Object>(responseBody,HttpStatus.BAD_REQUEST);
        }
        String[] startEnd = partTotal[0].split("-");
        if(startEnd.length != 2){
            responseBody.put("success", false);
            responseBody.put("message", "Content-Range format : start-end/total");
            return new ResponseEntity<Object>(responseBody,HttpStatus.BAD_REQUEST);
        }
        
        Long start = Long.valueOf(startEnd[0]);
        Long end = Long.valueOf(startEnd[1]);
        
        System.out.println("Client upload start at byte : "+start);
        System.out.println("Client upload end at byte : "+end);
        System.out.println("Client upload size : "+contentLength);
        System.out.println("Client upload total size : "+partTotal[1]);
        
        InfoFile info = databaseInfoFile.get(id);
        if(info == null){
            responseBody.put("success", false);
            responseBody.put("message", "Invalid upload_id");
            return new ResponseEntity<Object>(responseBody,HttpStatus.BAD_REQUEST);
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
                responseBody.put("success", true);
                responseBody.put("message", "File joined at "+hasil.getAbsolutePath());
                return new ResponseEntity<Object>(responseBody,HttpStatus.CREATED);
            } else {
                responseBody.put("success", false);
                responseBody.put("message", "Checksum mismatch, harusnya "+info.getSha1Sum()+", ternyata "+FileHelper.shaSum(hasil));
                return new ResponseEntity<Object>(responseBody,HttpStatus.BAD_REQUEST);
            }
            
        } 
        
        responseBody.put("success", true);
        responseBody.put("message", "File diterima, silahkan lanjutkan upload");
        return new ResponseEntity<Object>(responseBody,HttpStatus.OK);
    }
}
