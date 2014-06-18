package com.artivisi.training.ws.storage.android.service;

import android.util.Base64;
import android.util.Log;

import com.google.common.hash.Hashing;
import com.google.common.io.Files;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StorageService {
    private static final String URL_UPLOAD = "/aplikasi-storage-server/api/upload/";
    private static final Long UKURAN_CHUNK = 5000L;

    private RestTemplate restTemplate;
    private String serverUrl;

    public StorageService(String url){
        this.serverUrl = url;
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
    }

    public void upload(File f){
        try {
            Map<String, String> data = new HashMap<String, String>();
            data.put("nama", "tutorial.pdf");
            data.put("Content-Type", "application/pdf");
            data.put("sha1Sum", Files.hash(f, Hashing.sha1()).toString());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<Map> requestData = new HttpEntity<Map>(data);

            // dapatkan URL dan upload_id
            String requestUrl = serverUrl + URL_UPLOAD;
            Log.i("Aplikasi Storage","Request URL : "+requestUrl);

            URI urlPutUpload = restTemplate.postForLocation(serverUrl+URL_UPLOAD, requestData);

            Log.i("Aplikasi Storage", "URL upload : " + urlPutUpload.toString());

            Long jumlahFile = ((f.length() / UKURAN_CHUNK) + 1);
            Log.i("Aplikasi Storage", "Jumlah file " + jumlahFile);
            FileInputStream fis = new FileInputStream(f);
            for (int i = 0; i < jumlahFile; i++) {

                Integer ukuranBaca = UKURAN_CHUNK.intValue();

                // khusus potongan terakhir, lebih kecil dari ukuran chunk
                if(i + 1 >= jumlahFile){
                    ukuranBaca = (int)((f.length() % UKURAN_CHUNK));
                }

                Log.i("Aplikasi Storage", "Ukuran baca array : " + ukuranBaca);

                byte[] chunk = new byte[ukuranBaca];
                fis.read(chunk);

                // konversi file menjadi base64
                String strData = Base64.encodeToString(chunk,Base64.DEFAULT);

                HttpHeaders uploadHeader = new HttpHeaders();

                uploadHeader.add("Content-Range", i + "-" + (i+1) + "/" + jumlahFile);
                uploadHeader.setContentType(MediaType.TEXT_PLAIN);

                HttpEntity<String> uploadData = new HttpEntity<String>(strData, uploadHeader);

                // upload satu persatu
                restTemplate.put(urlPutUpload, uploadData);
            }
        } catch (Exception err){
            err.printStackTrace();
        }
    }
}
