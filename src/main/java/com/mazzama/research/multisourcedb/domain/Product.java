package com.mazzama.research.multisourcedb.domain;

import lombok.Data;

import javax.persistence.*;

@Table(name = "product")
@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}
