package com.artivisi.training.ws.domain;

public class InfoFile {
    private String nama;
    private String contentType;
    private String sha1Sum;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getSha1Sum() {
        return sha1Sum;
    }

    public void setSha1Sum(String sha1Sum) {
        this.sha1Sum = sha1Sum;
    }
}
