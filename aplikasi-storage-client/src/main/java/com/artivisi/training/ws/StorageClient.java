package com.artivisi.training.ws;

import com.google.common.io.Files;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.DatatypeConverter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class StorageClient {

    private static final String URL_UPLOAD = "http://localhost:8080/aplikasi-storage-server/api/upload/";
    private static final Long UKURAN_CHUNK = 5000L;

    private final RestTemplate restTemplate = new RestTemplate();

    public void uploadFile(File f) {
        try {
            Map<String, String> data = new HashMap<String, String>();
            data.put("nama", "tutorial.pdf");
            data.put("Content-Type", "application/pdf");
            data.put("sha1Sum", "6cc581c57aaf14b5b2349d6bf1c4c4efb1618694");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<Map> requestData = new HttpEntity<Map>(data);

            // dapatkan URL dan upload_id
            URI urlPutUpload = restTemplate.postForLocation(URL_UPLOAD, requestData);
            System.out.println("URL upload : " + urlPutUpload.toString());

            Long jumlahFile = ((f.length() / UKURAN_CHUNK) + 1);
            System.out.println("Jumlah file "+jumlahFile);
            FileInputStream fis = new FileInputStream(f);
            for (int i = 0; i < jumlahFile; i++) {
                
                Integer ukuranBaca = UKURAN_CHUNK.intValue();
                
                // khusus potongan terakhir, lebih kecil dari ukuran chunk
                if(i + 1 >= jumlahFile){
                    ukuranBaca = (int)((f.length() % UKURAN_CHUNK));
                }
                
                System.out.println("Ukuran baca array : "+ukuranBaca);
                
                byte[] chunk = new byte[ukuranBaca];
                fis.read(chunk);
                
                Files.write(chunk, new File("target/chunk-"+i));
                
                // konversi file menjadi base64
                String strData = DatatypeConverter.printBase64Binary(chunk);
                
                HttpHeaders uploadHeader = new HttpHeaders();

                uploadHeader.add("Content-Range", i + "-" + (i+1) + "/" + jumlahFile);
                uploadHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);

                HttpEntity<String> uploadData = new HttpEntity<String>(strData, uploadHeader);

                // upload satu persatu
                restTemplate.put(urlPutUpload, uploadData);
            }

        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
