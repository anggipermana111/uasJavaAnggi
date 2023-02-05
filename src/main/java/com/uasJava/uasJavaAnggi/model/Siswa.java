package com.uasJava.uasJavaAnggi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Siswa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(length = 20,nullable = false)
    private String nis;

    @Column(length = 100,nullable = false)
    private String nama;

    @Column(length = 1,nullable = false)
    private Character jenisKelamin;

    @Column(length = 100,nullable = false)
    private String alamat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_kelas", referencedColumnName = "id")
    private Kelas kelas;

    @ManyToMany(mappedBy = "siswaSet")
    private Set<Eskul> eskulSet = new HashSet<>();
}
