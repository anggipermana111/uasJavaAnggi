package com.uasJava.uasJavaAnggi.controller;

import com.uasJava.uasJavaAnggi.dto.MataPelajaranDto;
import com.uasJava.uasJavaAnggi.model.MataPelajaran;
import com.uasJava.uasJavaAnggi.repository.GuruRepository;
import com.uasJava.uasJavaAnggi.repository.MataPelajaranRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mata-pelajaran")
public class MataPelajaranController {

    MataPelajaranRepository mataPelajaranRepository;
    GuruRepository guruRepository;

    @Autowired
    public MataPelajaranController(MataPelajaranRepository mataPelajaranRepository, GuruRepository guruRepository) {
        this.mataPelajaranRepository = mataPelajaranRepository;
        this.guruRepository = guruRepository;
    }

    @GetMapping("/find-all")
    public List<MataPelajaran> findAll() {
        return mataPelajaranRepository.findAll();
    }

    @GetMapping("/find-by-id/{id}")
    public MataPelajaran findById(@PathVariable("id") Integer id) {
        return mataPelajaranRepository.findById(id).orElse(null);
    }


    @GetMapping("/find-all-kelas-mata-pelajaran")
    public List<Map<String, Object>> findAllKelasMataPelajaran() {
        List<Map<String, Object>> kelasMataPelajarans = new ArrayList<>();
        mataPelajaranRepository.findAll().forEach(mataPelajaran -> {
            Map<String, Object> kelasMataPelajaran = new HashMap<>();
            kelasMataPelajaran.put("mata pelajaran", mataPelajaran);
            kelasMataPelajaran.put("kelas", mataPelajaran.getKelas());
            kelasMataPelajarans.add(kelasMataPelajaran);
        });
        return kelasMataPelajarans;
    }

    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody MataPelajaranDto dto) {
        MataPelajaran mataPelajaran = new MataPelajaran();
        mataPelajaran.setNama(dto.getNama());
        if (guruRepository.findById(dto.getIdPengajar()).isPresent()) {
            mataPelajaran.setPengajar(guruRepository.findById(dto.getIdPengajar()).orElse(null));
            return ResponseEntity.ok(mataPelajaranRepository.save(mataPelajaran));
        } else {
            return ResponseEntity.badRequest().body("Id Guru Tidak Ada!");
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Integer id,
                         @RequestBody MataPelajaranDto dto) {
        MataPelajaran mataPelajaran = mataPelajaranRepository.findById(id).orElse(null);
        if(mataPelajaran == null) {
            return ResponseEntity.badRequest().body("id tidak ditemukan!");
        }
        mataPelajaran.setNama(dto.getNama());
        mataPelajaran.setPengajar(guruRepository.findById(dto.getIdPengajar()).orElse(null));

        if (guruRepository.findById(dto.getIdPengajar()).isPresent()) {
            mataPelajaran.setPengajar(guruRepository.findById(dto.getIdPengajar()).orElse(null));
            return ResponseEntity.ok(mataPelajaranRepository.save(mataPelajaran));
        } else {
            return ResponseEntity.badRequest().body("Id Guru Tidak Ada!");
        }
    }
}
