/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enseval.ttss.model;

import java.io.Serializable;
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
    @Temporal(javax.persistence.TemporalType.DATE)
    Date tglOpname;
    @ManyToOne
    String pelaksana;
    String jenisKas;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date tglCutoff;
    Long saldoSistem;
    Long saldoFisik;
    Long selisih;

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public Date getTglOpname() {
        return tglOpname;
    }

    public void setTglOpname(Date tglOpname) {
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

    public Date getTglCutoff() {
        return tglCutoff;
    }

    public void setTglCutoff(Date tglCutoff) {
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
