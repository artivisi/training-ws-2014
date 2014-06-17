package com.artivisi.training.ws;

import java.io.File;
import org.junit.Test;

public class StorageClientTest {
    
    @Test
    public void testUpload(){
        File f = new File("src/test/resources/curl-upload.pdf");
        StorageClient client = new StorageClient();
        client.uploadFile(f);
    }
}
