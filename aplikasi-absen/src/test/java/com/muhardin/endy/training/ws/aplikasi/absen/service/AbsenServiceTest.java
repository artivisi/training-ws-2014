package com.muhardin.endy.training.ws.aplikasi.absen.service;

import com.muhardin.endy.training.ws.aplikasi.absen.Karyawan;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import org.junit.Before;

@ContextConfiguration(locations = "classpath:absen-ctx.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class AbsenServiceTest {
    
    @Autowired private AbsenService absenService;
    @Autowired private DataSource dataSource;
    
    @Before
    public void resetIsiDatabase() throws Exception {
        Connection connection = dataSource.getConnection();
        DatabaseOperation.CLEAN_INSERT.execute(new DatabaseConnection(connection), 
                new FlatXmlDataSetBuilder()
                        .build(this.getClass().getResourceAsStream("/karyawan.xml")));
    }
    
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
        assertEquals(Long.valueOf(2), Long.valueOf(rs.getLong(1)));
    }
    
    @Test
    public void testCariByNip(){
        // test query yang ada di sample data
        Karyawan k1 = absenService.cariByNip(999);
        
        assertNotNull(k1); // harusnya gak null
        assertEquals("sholihin", k1.getNama()); // cek datanya
        assertTrue(k1.getTelp().size() == 2); // cek relasi ke tabel lain
        
        // test query data yang tidak ada
        assertNull(absenService.cariByNip(888));
    }
    
    @Test
    public void testCariByNama(){
        List<Karyawan> hasil = absenService.cariByNama("shol");
        assertNotNull(hasil);
        assertFalse(hasil.isEmpty());
        
        List<Karyawan> hasil2 = absenService.cariByNama("endy");
        assertNotNull(hasil2);
        assertTrue(hasil2.isEmpty());
    }
}
