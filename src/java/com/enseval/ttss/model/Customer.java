package com.enseval.ttss.model;

import java.io.*;
import javax.persistence.*;
import java.util.*;
import java.text.*;
import com.avaje.ebean.*;
import java.sql.Timestamp;

@Entity
public class Customer implements Serializable
{
    @Id
    private Long id;
    String nama;
    @Lob
    String shipto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getShipto() {
        return shipto;
    }

    public void setShipto(String shipto) {
        this.shipto = shipto;
    }
    
    

}
