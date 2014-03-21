package com.muhardin.endy.training.ws.aplikasi.absen.service;

import com.muhardin.endy.training.ws.aplikasi.absen.Karyawan;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service 
@Transactional(readOnly = true)
public class AbsenServiceImpl implements AbsenService {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Transactional(readOnly = false)
    public void simpan(Karyawan k){
        sessionFactory.getCurrentSession().saveOrUpdate(k);
    }
    
    public Karyawan cariByNip(Integer nip){
        return (Karyawan) sessionFactory.getCurrentSession().get(Karyawan.class, nip);
    }
    
    public List<Karyawan> cariByNama(String nama){
        return sessionFactory.getCurrentSession()
                .createQuery("select k from Karyawan k where k.nama like :nama ")
                .setParameter("nama", "%"+nama+"%")
                .list();
    }
}
