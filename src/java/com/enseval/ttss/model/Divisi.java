package com.enseval.ttss.model;

import java.util.*;

public class Divisi
{
    private Long id;
    String nama;
    List<User> user;
    
    public Divisi() {
        this.user = new ArrayList<User>();
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
    
    public List<User> getUser() {
        return this.user;
    }
    
    public void setUser(List<User> user) {
        this.user = user;
    }
}
