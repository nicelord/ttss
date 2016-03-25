package com.enseval.ttss.model;

import java.io.*;
import javax.persistence.*;
import java.util.*;

@Entity
public class User implements Serializable
{
    @Id
    @GeneratedValue
    private Long id;
    String nama;
    String username;
    String password;
    String akses;
    @OneToMany(mappedBy = "userLogin")
    List<TTSS> ttss;
    @OneToMany(mappedBy = "userLogin")
    List<Cetak> cetak;
    @ManyToOne
    Printer defPrinter;
    
    public User() {
        this.ttss = new ArrayList<>();
        this.cetak = new ArrayList<>();
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getAkses() {
        return this.akses;
    }
    
    public void setAkses(String akses) {
        this.akses = akses;
    }
    
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNama() {
        return this.nama;
    }
    
    public void setNama(String nama) {
        this.nama = nama;
    }
    
    public List<TTSS> getTtss() {
        return this.ttss;
    }
    
    public void setTtss(List<TTSS> ttss) {
        this.ttss = ttss;
    }
    
    public Printer getDefPrinter() {
        return this.defPrinter;
    }
    
    public void setDefPrinter(Printer defPrinter) {
        this.defPrinter = defPrinter;
    }
}
