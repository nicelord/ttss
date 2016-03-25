package com.enseval.ttss.model;

import javax.persistence.*;

@Entity
public class DsPenyetor
{
    @Id
    @GeneratedValue
    private Long id;
    String nama;
    
    public String getNama() {
        return this.nama.toUpperCase();
    }
    
    public void setNama(String nama) {
        this.nama = nama.toUpperCase();
    }
    
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
}
