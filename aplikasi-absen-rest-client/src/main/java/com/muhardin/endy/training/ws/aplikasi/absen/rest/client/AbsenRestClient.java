/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.muhardin.endy.training.ws.aplikasi.absen.rest.client;

import com.muhardin.endy.training.ws.aplikasi.absen.Karyawan;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author endy
 */
public class AbsenRestClient {

    private static final String SERVER_URL = "https://localhost:8443/aplikasi-absen-rest/api/rest";
    private RestTemplate restTemplate = null;

    public AbsenRestClient() {
        try {
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    builder.build());
            
            CredentialsProvider provider = new BasicCredentialsProvider();
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("endy", "123");
            provider.setCredentials(AuthScope.ANY, credentials);
            HttpClient client = HttpClientBuilder.create()
                    .setDefaultCredentialsProvider(provider)
                    .setSSLSocketFactory(sslsf)
                    .build();
            
            restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(client));
            restTemplate.setErrorHandler(new AbsenRestClientErrorHandler());
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException ex) {
            Logger.getLogger(AbsenRestClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Karyawan cariKaryawanByNip(Integer nip) {
        String url = SERVER_URL + "/karyawan/" + nip;
        Karyawan k = restTemplate
                .getForObject(url, Karyawan.class, new HashMap<Object, Object>());
        return k;
    }

    public List<Karyawan> semuaKaryawan() {
        ParameterizedTypeReference<List<Karyawan>> typeRef
                = new ParameterizedTypeReference<List<Karyawan>>() {
                };

        ResponseEntity<List<Karyawan>> response = restTemplate
                .exchange(SERVER_URL + "/karyawan/",
                        HttpMethod.GET, null,
                        typeRef);

        System.out.println("Response Status : " + response.getStatusCode());

        return response.getBody();
    }

    public void simpan(Karyawan k) {
        String url = SERVER_URL + "/karyawan/";
        restTemplate.postForObject(url, k, Object.class);
    }

    public void update(Karyawan k) {
        String url = SERVER_URL + "/karyawan/{nip}";
        restTemplate.put(url, k, k.getNip());
    }

    public void delete(Karyawan k) {
        String url = SERVER_URL + "/karyawan/{nip}";

        Map<String, Object> pathVariable = new HashMap<>();
        pathVariable.put("nip", k.getNip());

        restTemplate.delete(url, pathVariable);
    }
}
