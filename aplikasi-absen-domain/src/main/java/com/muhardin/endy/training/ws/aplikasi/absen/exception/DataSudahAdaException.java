package com.muhardin.endy.training.ws.aplikasi.absen.exception;

public class DataSudahAdaException extends RuntimeException {

    public DataSudahAdaException() {
    }

    public DataSudahAdaException(String string) {
        super(string);
    }

    public DataSudahAdaException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public DataSudahAdaException(Throwable thrwbl) {
        super(thrwbl);
    }

    public DataSudahAdaException(String string, Throwable thrwbl, boolean bln, boolean bln1) {
        super(string, thrwbl, bln, bln1);
    }
    
}
