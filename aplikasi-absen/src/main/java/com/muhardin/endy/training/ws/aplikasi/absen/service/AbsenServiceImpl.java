package com.muhardin.endy.training.ws.aplikasi.absen.service;

import com.muhardin.endy.training.ws.aplikasi.absen.Karyawan;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service 
@Transactional(readOnly = true)
public class AbsenServiceImpl implements AbsenService {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    @Transactional(readOnly = false)
    public void simpan(Karyawan k){
        sessionFactory.getCurrentSession().saveOrUpdate(k);
    }
    
    @Override
    @Transactional(readOnly = false)
    public void hapusKaryawan(Integer nip){
        Karyawan k = (Karyawan) sessionFactory.getCurrentSession().load(Karyawan.class, nip);
        if(k != null){
            sessionFactory.getCurrentSession().delete(k);
        }
    }
    
    @Override
    public List<Karyawan> semuaKaryawan(Integer start, Integer rows){
        List<Karyawan> hasil = sessionFactory.getCurrentSession()
                .createQuery("select k from Karyawan k order by k.nip")
                .setFirstResult(start)
                .setMaxResults(rows)
                .list();
        for (Karyawan karyawan : hasil) {
            Hibernate.initialize(karyawan.getTelp());
        }
        return hasil;
    }
    
    @Override
    public Karyawan cariByNip(Integer nip){
        Karyawan k = (Karyawan) sessionFactory.getCurrentSession().get(Karyawan.class, nip);
        if(k != null){
            Hibernate.initialize(k.getTelp());
        }
        return k;
    }
    
    @Override
    public List<Karyawan> cariByNama(String nama){
        return sessionFactory.getCurrentSession()
                .createQuery("select k from Karyawan k where k.nama like :nama ")
                .setParameter("nama", "%"+nama+"%")
                .list();
    }
}
