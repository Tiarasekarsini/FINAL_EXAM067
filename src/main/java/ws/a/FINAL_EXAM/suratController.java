/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.a.FINAL_EXAM;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpEntity;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ASUS
 */

@RestController
public class suratController {
    
    //menginisialisasi kelas Surat
    Surat surat = new Surat();
    //menginisialisasi Jpa Controller
    SuratJpaController jpa = new SuratJpaController();
    
    //menentukan url permintaan untuk mengakses REST endpoint pada method GET
    @GetMapping(value = "/GET", produces = APPLICATION_JSON_VALUE)
    //membuat method untuk menampilkan surat
    public List<Surat> showSurat(){
       List<Surat> sr = new ArrayList<>(); //declare new list Variable
       sr = jpa.findSuratEntities();//menginisialisasikan isi sr menjadi variable untuk menampilkan semua data
       return sr;
    }
    
    //Menentukan Url permintaan untuk mengakses REST endpoint pada method POST
    @PostMapping(value = "/POST", consumes = APPLICATION_JSON_VALUE)
    //method untuk menambahkan data
    public String tambahData(HttpEntity<String> datasend) throws JsonProcessingException, Exception {

        //membuat variabel sebagai keluaran ketika data tidak berhasil disimpan
        String Respon = "Tidak Ada Perubahan";

        ObjectMapper maper = new ObjectMapper();
        //readValue()untuk menerima bentuk input lain
        surat = maper.readValue(datasend.getBody(), Surat.class);
        try {
            jpa.create(surat);
            Respon = surat.getNoSurat() + " Berhasil Disimpan";
        } catch (Exception error) {
            Respon = error.getMessage();
        }
        return Respon;
    }
    
}
