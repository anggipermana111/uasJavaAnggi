package com.uasJava.uasJavaAnggi.controller;

import com.uasJava.uasJavaAnggi.dto.SiswaDto;
import com.uasJava.uasJavaAnggi.model.Siswa;
import com.uasJava.uasJavaAnggi.repository.EskulRepository;
import com.uasJava.uasJavaAnggi.repository.KelasRepository;
import com.uasJava.uasJavaAnggi.repository.SiswaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/siswa")
public class SiswaController {
    SiswaRepository siswaRepository;
    KelasRepository kelasRepository;
    EskulRepository eskulRepository;

    public SiswaController(SiswaRepository siswaRepository, KelasRepository kelasRepository, EskulRepository eskulRepository) {
        this.siswaRepository = siswaRepository;
        this.kelasRepository = kelasRepository;
        this.eskulRepository = eskulRepository;
    }

    @GetMapping("/find-all")
    public List<Siswa> findAll() {
        return siswaRepository.findAll();
    }

    @GetMapping("/find-by-id/{id}")
    public Siswa findById(@PathVariable("id") Integer id) {
        return siswaRepository.findById(id).orElse(null);
    }

    @GetMapping("/find-all-eskul-siswa")
    public List<Map<String, Object>> findAllEskulSiswa() {
        List<Map<String, Object>> eskulSiswas = new ArrayList<>();
        siswaRepository.findAll().forEach(siswa -> {
            Map<String, Object> eskulSiswa = new HashMap<>();
            eskulSiswa.put("siswa", siswa);
            eskulSiswas.add(eskulSiswa);
        });
        return eskulSiswas;
    }

    @GetMapping("/find-siswa-by-kelas")
    public Object findSiswaByKelas(@RequestParam("kelasId") Integer kelasId) {
        return siswaRepository.findSiswaByKelas(kelasId);
    }

    @PostMapping("/add-eskul/{id-siswa}/{id-eskul}")
    public void addEskul(@PathVariable("id-siswa") Integer idSiswa, @PathVariable("id-eskul") Integer idEskul) {
        Siswa siswa = findById(idSiswa);
        EskulController.anggi(idEskul, siswa, eskulRepository, siswaRepository);
    }

    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody SiswaDto dto) {
        Siswa siswa = new Siswa();

        if (kelasRepository.findById(dto.getIdKelas()).isPresent()) {
            siswa.setNis(dto.getNis());
            siswa.setNama(dto.getNama());
            siswa.setJenisKelamin(dto.getJenisKelamin());
            siswa.setAlamat(dto.getAlamat());
            siswa.setKelas(kelasRepository.findById(dto.getIdKelas()).orElse(null));
            return ResponseEntity.ok(siswaRepository.save(siswa));
        } else {
            return ResponseEntity.badRequest().body("Id Kelas Tidak Ada!");
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Integer id,
                                         @RequestBody SiswaDto dto) {
        Siswa siswa = siswaRepository.findById(id).orElse(null);
        if(siswa == null) {
            return ResponseEntity.badRequest().body("id tidak ditemukan!");
        }

        siswa.setNis(dto.getNis());
        siswa.setNama(dto.getNama());
        siswa.setJenisKelamin(dto.getJenisKelamin());
        siswa.setAlamat(dto.getAlamat());
        if (kelasRepository.findById(dto.getIdKelas()).isPresent()) {
            siswa.setKelas(kelasRepository.findById(dto.getIdKelas()).orElse(null));
            return ResponseEntity.ok(siswaRepository.save(siswa));
        } else {
            return ResponseEntity.badRequest().body("Id Kelas Tidak Ada!");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        Siswa siswa = siswaRepository.findById(id).orElse(null);
        if (siswa == null) {
            return ResponseEntity.badRequest().body("id tidak tersedia");
        }

        siswaRepository.delete(siswa);
        return null;
    }
}
