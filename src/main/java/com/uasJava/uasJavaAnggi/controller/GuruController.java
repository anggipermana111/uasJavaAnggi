package com.uasJava.uasJavaAnggi.controller;

import com.uasJava.uasJavaAnggi.dto.GuruDto;
import com.uasJava.uasJavaAnggi.model.Guru;
import com.uasJava.uasJavaAnggi.repository.GuruRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guru")
public class GuruController {
    GuruRepository guruRepository;
    @Autowired
    public GuruController(GuruRepository guruRepository) {
        this.guruRepository = guruRepository;
    }

    @GetMapping("/select-kelas-and-wali")
    public Object selectKelasAndWali() {
        return guruRepository.selectKelasAndWali();
    }

    @GetMapping("/find-all")
    public List<Guru> findAll() {
        return guruRepository.findAll();
    }

    @GetMapping("/find-by-id/{id}")
    public Guru findById(@PathVariable("id") Integer id) {
        return guruRepository.findById(id).orElse(null);
    }


    @PostMapping("/")
    public Object create(@RequestBody GuruDto dto) {
        Guru guru = new Guru();
        guru.setNama(dto.getNama());
        guru.setAlamat(dto.getAlamat());
        guru.setNoHp(dto.getNoHp());

        return  ResponseEntity.ok(guruRepository.save(guru));
    }

    @PutMapping("/{id}")
    public Object update(@PathVariable("id") Integer id,
                         @RequestBody GuruDto dto) {
        Guru guru = guruRepository.findById(id).orElse(null);
        if(guru == null) {
            return ResponseEntity.badRequest().body("id tidak ditemukan!");
        }
        guru.setNama(dto.getNama());
        guru.setAlamat(dto.getAlamat());
        guru.setNoHp(dto.getNoHp());

        return  ResponseEntity.ok(guruRepository.save(guru));
    }

    @DeleteMapping("/{id}")
    public Object delete(@PathVariable("id") Integer id) {
        Guru guru = guruRepository.findById(id).orElse(null);
        if (guru == null) {
            return ResponseEntity.badRequest().body("id tidak tersedia");
        }

        guruRepository.delete(guru);
        return null;
    }
}
