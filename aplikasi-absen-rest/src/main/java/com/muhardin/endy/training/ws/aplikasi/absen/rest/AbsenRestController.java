package com.muhardin.endy.training.ws.aplikasi.absen.rest;

import com.muhardin.endy.training.ws.aplikasi.absen.Karyawan;
import com.muhardin.endy.training.ws.aplikasi.absen.exception.DataSudahAdaException;
import com.muhardin.endy.training.ws.aplikasi.absen.exception.DataTidakDitemukanException;
import com.muhardin.endy.training.ws.aplikasi.absen.service.AbsenService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class AbsenRestController {

    @Autowired
    private AbsenService absenService;

    @RequestMapping("/rest/karyawan/{nip}")
    @ResponseBody
    public Karyawan infoKaryawan(@PathVariable Integer nip) {
        Karyawan k = absenService.cariByNip(nip);
        if (k == null) {
            throw new DataTidakDitemukanException();
        }
        return k;
    }

    @RequestMapping(value = "/rest/karyawan/", method = RequestMethod.GET)
    @ResponseBody
    public List<Karyawan> daftarKaryawan(
            @RequestParam(required = false) Integer start,
            @RequestParam(required = false) Integer rows
    ) {

        if (start == null) {
            start = 0;
        }

        if (rows == null) {
            rows = 10;
        }

        return absenService.semuaKaryawan(start, rows);
    }

    @RequestMapping(value = "/rest/karyawan/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void simpan(@RequestBody @Valid Karyawan k) {
        Karyawan kx = absenService.cariByNip(k.getNip());
        if(kx != null){
            throw new DataSudahAdaException();
        }
        absenService.simpan(k);
    }

    @RequestMapping(value = "/rest/karyawan/{nip}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Integer nip, @RequestBody @Valid Karyawan k) {
        Karyawan kx = absenService.cariByNip(nip);
        if (kx == null) {
            throw new DataTidakDitemukanException();
        }
        absenService.simpan(k);
    }

    @RequestMapping(value = "/rest/karyawan/{nip}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void hapus(@PathVariable Integer nip) {
        Karyawan kx = absenService.cariByNip(nip);
        if (kx == null) {
            throw new DataTidakDitemukanException();
        }
        absenService.hapusKaryawan(nip);
    }
}
