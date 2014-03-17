package com.muhardin.endy.training.ws.aplikasi.absen.ws.client;

public class Main {
    private AbsenWS_Service service = new AbsenWS_Service();
    
    public void tambah(){
        try { // Call Web Service Operation
            AbsenWS port = service.getAbsenWSPort();
            // TODO initialize WS operation arguments here
            Integer num1 = 5;
            Integer num2 = 6;
            // TODO process result here
            Integer result = port.tambah(num1, num2);
            System.out.println("Result = "+result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        
        Main m = new Main();
        m.tambah();

    }
}
