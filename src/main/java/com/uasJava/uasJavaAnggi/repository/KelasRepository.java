package com.uasJava.uasJavaAnggi.repository;

import com.uasJava.uasJavaAnggi.model.Kelas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KelasRepository extends JpaRepository<Kelas, Integer> {
    @Query("SELECT k.nama, g.nama FROM Kelas k LEFT JOIN Guru g ON k.wali.id = g.id")
    List<Object> selectKelasAndWali();
}
