package com.uasJava.uasJavaAnggi.repository;

import com.uasJava.uasJavaAnggi.model.Siswa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SiswaRepository extends JpaRepository<Siswa, Integer> {
    @Query("select s from Siswa s where s.kelas.id =:kelasId")
    List<Siswa> findSiswaByKelas(Integer kelasId);
}
