package com.artivisi.training.ws.helper;

import com.google.common.hash.Hashing;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileHelper {
    public static String shaSum(File f){
        try {
            return Files.hash(f, Hashing.sha1()).toString();
        } catch (IOException ex) {
            Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Boolean verifySum(File f, String sum){
        try {
            return sum.equalsIgnoreCase(Files.hash(f, Hashing.sha1()).toString());
        } catch (IOException ex) {
            Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static File joinFiles(String outputFolder, String inputFolder, String inputFileNamePrefix) throws IOException{
        try {
            File output = new File(outputFolder + File.separator + UUID.randomUUID().toString());
            
            // input folder
            Path inputLocation = Paths.get(".", inputFolder);
            
            // daftar nama file
            List<String> inputFiles = new ArrayList<String>();
            
            DirectoryStream<Path> daftarFile = java.nio.file.Files
                    .newDirectoryStream(inputLocation, inputFileNamePrefix+"*");
            for (Path path : daftarFile) {
                inputFiles.add(path.toString());
            }
            
            // urutkan nama file
            Collections.sort(inputFiles);
            
            ByteSink outputSink = Files.asByteSink(output, FileWriteMode.APPEND);
            
            for (String nama : inputFiles) {
                System.out.println("Nama file = "+nama);
                ByteSource src = Files.asByteSource(new File(nama));
                src.copyTo(outputSink);
            }
            
            return output;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } 
    }
}
