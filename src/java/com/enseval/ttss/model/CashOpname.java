/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enseval.ttss.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author user
 */

@Entity
public class CashOpname implements Serializable {
    
    @Id @GeneratedValue Long Id;
    Timestamp tglOpname;
    @ManyToOne
    String pelaksana;
    String jenisKas;
    Timestamp tglCutoff;
    Long saldoSistem;
    Long saldoFisik;
    Long selisih;

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public Timestamp getTglOpname() {
        return tglOpname;
    }

    public void setTglOpname(Timestamp tglOpname) {
        this.tglOpname = tglOpname;
    }

    public String getPelaksana() {
        return pelaksana;
    }

    public void setPelaksana(String pelaksana) {
        this.pelaksana = pelaksana;
    }

  

    public String getJenisKas() {
        return jenisKas;
    }

    public void setJenisKas(String jenisKas) {
        this.jenisKas = jenisKas;
    }

    public Timestamp getTglCutoff() {
        return tglCutoff;
    }

    public void setTglCutoff(Timestamp tglCutoff) {
        this.tglCutoff = tglCutoff;
    }

    public Long getSaldoSistem() {
        return saldoSistem;
    }

    public void setSaldoSistem(Long saldoSistem) {
        this.saldoSistem = saldoSistem;
    }

    public Long getSaldoFisik() {
        return saldoFisik;
    }

    public void setSaldoFisik(Long saldoFisik) {
        this.saldoFisik = saldoFisik;
    }

    public Long getSelisih() {
        return selisih;
    }

    public void setSelisih(Long selisih) {
        this.selisih = selisih;
    }
    
    
    
    
}
