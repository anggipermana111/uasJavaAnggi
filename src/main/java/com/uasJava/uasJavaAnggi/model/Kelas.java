package com.uasJava.uasJavaAnggi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Kelas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(length = 100, nullable = false)
    private String nama;

    @OneToOne
    @JoinColumn(name = "id_wali")
    private Guru wali;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "kelas_mata_pelajaran",
            joinColumns = {@JoinColumn(name = "id_kelas")},
            inverseJoinColumns = {@JoinColumn(name = "id_pelajaran")}
    )
    private Set<MataPelajaran> mataPelajaran = new HashSet<>();
}
