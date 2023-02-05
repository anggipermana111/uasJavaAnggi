package com.uasJava.uasJavaAnggi.controller;


import com.uasJava.uasJavaAnggi.dto.EskulDto;
import com.uasJava.uasJavaAnggi.model.Eskul;
import com.uasJava.uasJavaAnggi.model.Siswa;
import com.uasJava.uasJavaAnggi.repository.EskulRepository;
import com.uasJava.uasJavaAnggi.repository.KelasRepository;
import com.uasJava.uasJavaAnggi.repository.SiswaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eskul")
public class EskulController {
    EskulRepository eskulRepository;
    SiswaRepository siswaRepository;
    KelasRepository kelasRepository;
    @Autowired
    public EskulController(EskulRepository eskulRepository, SiswaRepository siswaRepository, KelasRepository kelasRepository) {
        this.eskulRepository = eskulRepository;
        this.siswaRepository = siswaRepository;
        this.kelasRepository = kelasRepository;
    }


    @GetMapping("/find-by-id/{id}")
    public Eskul findById(@PathVariable("id") Integer id) {
        return eskulRepository.findById(id).orElse(null);
    }


    @GetMapping("/find-all")
    public List<Eskul> findAll() {
        return eskulRepository.findAll();
    }

    @GetMapping("/find-all-eskul-siswa")
    public List<Map<String, Object>> findAllEskulSiswa() {
        List<Map<String, Object>> eskulSiswas = new ArrayList<>();
        eskulRepository.findAll().forEach(eskul -> {
            Map<String, Object> eskulSiswa = new HashMap<>();
            eskulSiswa.put("eskul", eskul);
            eskulSiswa.put("siswa", eskul.getSiswaSet());
            eskulSiswas.add(eskulSiswa);
        });
        return eskulSiswas;
    }

    @PostMapping("/add-eskul/{id-siswa}/{id-eskul}")
    public void addEskul(@PathVariable("id-siswa") Integer idSiswa, @PathVariable("id-eskul") Integer idEskul) {
        Siswa siswa = siswaRepository.findById(idSiswa).orElse(null);
        anggi(idEskul, siswa, eskulRepository, siswaRepository);
    }

    static void anggi(@PathVariable("id-eskul") Integer idEskul, Siswa siswa, EskulRepository eskulRepository, SiswaRepository siswaRepository) {
        Eskul eskul = eskulRepository.findById(idEskul).orElse(null);
        if(siswa != null && eskul != null) {
            siswa.getEskulSet().add(eskul);
            eskul.getSiswaSet().add(siswa);
            siswaRepository.save(siswa);
            eskulRepository.save(eskul);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Eskul> create(@RequestBody EskulDto dto) {
        Eskul eskul = new Eskul();
        eskul.setNama(dto.getNama());

        return  ResponseEntity.ok(eskulRepository.save(eskul));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Integer id,
                         @RequestBody EskulDto dto) {
        Eskul eskul = eskulRepository.findById(id).orElse(null);
        if(eskul == null) {
            return ResponseEntity.badRequest().body("id tidak ditemukan!");
        }
        eskul.setNama(dto.getNama());

        return  ResponseEntity.ok(eskulRepository.save(eskul));
    }

}
