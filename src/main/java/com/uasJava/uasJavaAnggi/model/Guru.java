package com.uasJava.uasJavaAnggi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Guru {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(length = 100,nullable = false)
    private String nama;

    @Column(length = 200,nullable = false)
    private String alamat;

    @Column(length = 15,nullable = false)
    private String noHp;
}
