package com.muhardin.endy.training.ws.aplikasi.absen.service;

import com.muhardin.endy.training.ws.aplikasi.absen.Karyawan;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import javax.sql.DataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@ContextConfiguration(locations = "classpath:absen-ctx.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class AbsenServiceTest {
    
    @Autowired private AbsenService absenService;
    @Autowired private DataSource dataSource;
    
    
    @Test
    public void testSimpan() throws Exception {
        Karyawan k = new Karyawan();
        k.setNip(123);
        k.setNama("Endy");
        k.setAktif(Boolean.TRUE);
        k.setTanggalLahir(new Date());
        k.getTelp().add("123456");
        k.getTelp().add("987654");
        
        absenService.simpan(k);
        
        Connection conn = dataSource.getConnection();
        ResultSet rs = conn.createStatement().executeQuery("select count(*) from m_karyawan");
        assertTrue(rs.next());
        assertEquals(Long.valueOf(1), Long.valueOf(rs.getLong(1)));
    }
}
