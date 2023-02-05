package com.uasJava.uasJavaAnggi.repository;

import com.uasJava.uasJavaAnggi.model.Guru;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GuruRepository extends JpaRepository<Guru, Integer> {
    @Query("SELECT g.nama, k.nama FROM Guru g LEFT JOIN Kelas k ON k.wali.id = g.id")
    List<Object> selectKelasAndWali();
}
