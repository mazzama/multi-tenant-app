package com.mazzama.research.multisourcedb.master.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "master_tenant")
@Data
public class MasterTenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 30)
    @Column(name = "tenant_id")
    private String tenantId;

    @Size(max = 255)
    @Column(name = "url")
    private String url;

    @Size(max = 30)
    @Column(name = "username")
    private String username;

    @Size(max = 30)
    @Column(name = "password")
    private String password;

    // Optimistic Locking
    @Version
    private int version = 0;
}
