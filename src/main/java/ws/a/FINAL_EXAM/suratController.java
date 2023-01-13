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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    
    @PutMapping(value = "/PUT", consumes = APPLICATION_JSON_VALUE)
    public String ubahData(HttpEntity<String> datasend) throws JsonProcessingException, Exception {
        //membuat variabel sebagai keluaran ketika data tidak berhasil disimpan
        String Respon = "Tidak Ada Perubahan";

        ObjectMapper maper = new ObjectMapper();
        //readValue()untuk menerima bentuk input lain
        surat = maper.readValue(datasend.getBody(), Surat.class);
        try {
            jpa.edit(surat);
            Respon = surat.getNoSurat()+ " Berhasil Diedit";
        } catch (Exception error) {
            Respon = error.getMessage();
        }
        return Respon;
    }
    //Menentukan Url permintaan untuk mengakses REST endpoint pada method DELETE
    @DeleteMapping(value = "/DELETE", consumes = APPLICATION_JSON_VALUE)
    //method untuk menghapus data 
    public String delData(HttpEntity<String> datasend) throws JsonProcessingException, Exception {
        //membuat variabel sebagai keluaran ketika data tidak berhasil disimpan
        String feedback = "Tidak Ada Perubahan";

        ObjectMapper maper = new ObjectMapper();
        //readValue()untuk menerima bentuk input lain
        surat = maper.readValue(datasend.getBody(), Surat.class);
        try {
            jpa.destroy(surat.getId()); //delete data berdasarkan id 
            //menampilkan keluaran ketika data berhasil di deleted
            feedback = "Berhasil di-Deleted";
        } catch (Exception error) {
            //menampilkan keluaran pesan error jika data tidak berhasil ditambahkan
            feedback = error.getMessage();
        }

        //menampilkan keluaran dari variabel feedback (tergantung berhasil atau eror)
        return feedback;

    }
}
