package com.uasJava.uasJavaAnggi.controller;

import com.uasJava.uasJavaAnggi.dto.KelasDto;
import com.uasJava.uasJavaAnggi.model.Kelas;
import com.uasJava.uasJavaAnggi.repository.GuruRepository;
import com.uasJava.uasJavaAnggi.repository.KelasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kelas")
public class KelasController {
    KelasRepository kelasRepository;
    GuruRepository guruRepository;

    @Autowired
    public KelasController(KelasRepository kelasRepository, GuruRepository guruRepository) {
        this.kelasRepository = kelasRepository;
        this.guruRepository = guruRepository;
    }

    @GetMapping("/select-kelas-and-wali")
    public Object selectKelasAndWali() {
        return kelasRepository.selectKelasAndWali();
    }

    @GetMapping("/find-all")
    public List<Kelas> findAll() {
        return kelasRepository.findAll();
    }

    @GetMapping("/find-by-id/{id}")
    public Kelas findById(@PathVariable("id") Integer id) {
        return kelasRepository.findById(id).orElse(null);
    }

    @GetMapping("/find-all-kelas-mata-pelajaran")
    public List<Map<String, Object>> findAllKelasMataPelajaran() {
        List<Map<String, Object>> kelasMataPelajarans = new ArrayList<>();
        kelasRepository.findAll().forEach(kelas -> {
            Map<String, Object> kelasMataPelajaran = new HashMap<>();
            kelasMataPelajaran.put("kelas", kelas);
            kelasMataPelajaran.put("mata pelajaran", kelas.getMataPelajaran());
            kelasMataPelajarans.add(kelasMataPelajaran);
        });
        return kelasMataPelajarans;
    }

    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody KelasDto dto) {

        if (kelasRepository.findById(dto.getIdWali()).isPresent()) {
            Kelas kelas = new Kelas();
            kelas.setNama(dto.getNama());
            kelas.setWali(guruRepository.findById(dto.getIdWali()).orElse(null));
            return ResponseEntity.ok(kelasRepository.save(kelas));
        } else {
            return ResponseEntity.badRequest().body("Id Guru Tidak Ada!");
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Integer id,
                                         @RequestBody KelasDto dto) {
        Kelas kelas = kelasRepository.findById(id).orElse(null);
        if(kelas == null) {
            return ResponseEntity.badRequest().body("id tidak ditemukan!");
        }
        kelas.setNama(dto.getNama());
        kelas.setWali(guruRepository.findById(dto.getIdWali()).orElse(null));

        if (guruRepository.findById(dto.getIdWali()).isPresent()) {
            kelas.setWali(guruRepository.findById(dto.getIdWali()).orElse(null));
            return ResponseEntity.ok(kelasRepository.save(kelas));
        } else {
            return ResponseEntity.badRequest().body("Id Guru Tidak Ada!");
        }
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
//        Kelas kelas = kelasRepository.findById(id).orElse(null);
//        if (kelas == null) {
//            return ResponseEntity.badRequest().body("id tidak tersedia");
//        }
//
//        kelasRepository.delete(kelas);
//        return null;
//    }


}
