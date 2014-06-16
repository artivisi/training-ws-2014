package com.artivisi.training.ws.helper;

import java.io.File;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

public class FileHelperTest {
    @Test
    public void testShaSum(){
        File f = new File("src/test/resources/mouse.pdf");
        String sum = FileHelper.shaSum(f);
        System.out.println("SHA sum : "+sum);
    }
    
    @Test
    public void testVerifySum(){
        File f = new File("src/test/resources/mouse.pdf");
        String sum = "7f18e8dc3407dfdb63b8afd3125ee3b82ccfd6cb";
        Assert.assertTrue("Verify SHA Sum", FileHelper.verifySum(f, sum));
    }
    
    @Test
    public void testJoinFile() throws IOException{
        File hasil = FileHelper.joinFiles("target", "src/test/resources", "mouse.pdf.");
        String sum = "7f18e8dc3407dfdb63b8afd3125ee3b82ccfd6cb";
        Assert.assertTrue("Verify SHA Sum", FileHelper.verifySum(hasil, sum));
    }
}
