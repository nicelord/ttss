package com.enseval.ttss.model;

import java.io.*;
import javax.persistence.*;

@Entity
public class Printer implements Serializable
{
    @Id
    private String namaPrinter;
    String keterangan;
    
    public Printer(String namaPrinter) {
        this.namaPrinter = namaPrinter;
    }
    
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
}
