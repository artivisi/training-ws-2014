package com.muhardin.endy.training.ws.aplikasi.absen.exception;

public class DataTidakDitemukanException extends RuntimeException {

    public DataTidakDitemukanException() {
    }

    public DataTidakDitemukanException(String string) {
        super(string);
    }

    public DataTidakDitemukanException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public DataTidakDitemukanException(Throwable thrwbl) {
        super(thrwbl);
    }

    public DataTidakDitemukanException(String string, Throwable thrwbl, boolean bln, boolean bln1) {
        super(string, thrwbl, bln, bln1);
    }
    
}
