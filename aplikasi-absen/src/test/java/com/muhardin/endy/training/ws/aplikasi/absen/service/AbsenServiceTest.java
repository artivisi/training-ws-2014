package com.muhardin.endy.training.ws.aplikasi.absen.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = "classpath:absen-ctx.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class AbsenServiceTest {
    
    @Test
    public void testSimpan(){
        System.out.println("Hello World");
    }
}
