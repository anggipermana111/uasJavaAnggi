package com.uasJava.uasJavaAnggi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class MataPelajaran {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(length = 100, nullable = false)
    private String nama;

    @OneToOne()
    @JoinColumn(table = "mata_pelajaran", name = "id_pengajar")
    private Guru pengajar;

    @JsonIgnore
    @ManyToMany(mappedBy = "mataPelajaran", cascade = {CascadeType.ALL})
    private Set<Kelas> kelas = new HashSet<>();
}
