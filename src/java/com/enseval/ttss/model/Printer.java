package com.enseval.ttss.model;

import java.io.*;
import javax.persistence.*;

@Entity
public class Printer implements Serializable
{
    @Id @GeneratedValue long id;
    
    private String namaPrinter;
    String keterangan;
    
    public String getNamaPrinter() {
        return this.namaPrinter;
    }
    
    public void setNamaPrinter(String namaPrinter) {
        this.namaPrinter = namaPrinter;
    }
    
    public String getKeterangan() {
        return this.keterangan;
    }
    
    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    
}
