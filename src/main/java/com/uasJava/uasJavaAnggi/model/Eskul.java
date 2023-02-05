package com.uasJava.uasJavaAnggi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Eskul {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(length = 100, nullable = false)
    private String nama;

    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "eskul_siswa",
            joinColumns = { @JoinColumn(name = "id_eskul") },
            inverseJoinColumns = { @JoinColumn(name = "id_siswa") }
    )
    private Set<Siswa> siswaSet = new HashSet<>();
}
