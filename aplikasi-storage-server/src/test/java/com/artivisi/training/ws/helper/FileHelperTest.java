package com.artivisi.training.ws.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.Test;

public class FileHelperTest {

    @Test
    public void testShaSum() {
        File f = new File("src/test/resources/mouse.pdf");
        String sum = FileHelper.shaSum(f);
        System.out.println("SHA sum : " + sum);
    }

    @Test
    public void testVerifySum() {
        File f = new File("src/test/resources/mouse.pdf");
        String sum = "7f18e8dc3407dfdb63b8afd3125ee3b82ccfd6cb";
        Assert.assertTrue("Verify SHA Sum", FileHelper.verifySum(f, sum));
    }

    @Test
    public void testJoinFile() throws IOException {
        Path currentRelativePath = Paths.get("");
        String currentFolder = currentRelativePath.toAbsolutePath().toString() + File.separator;
        File hasil = FileHelper.joinFiles(currentFolder+"target", currentFolder+"src/test/resources", "mouse.pdf.");
        String sum = "7f18e8dc3407dfdb63b8afd3125ee3b82ccfd6cb";
        Assert.assertTrue("Verify SHA Sum", FileHelper.verifySum(hasil, sum));
    }
}
