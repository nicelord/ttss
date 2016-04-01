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
import javax.persistence.Temporal;

/**
 *
 * @author Reza Elborneo
 */

@Entity
public class Setting implements Serializable {
    @Id
    @GeneratedValue
    Long id;
    Long saldoAwal = 0L;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date tanggalSaldoAwal;
    
    String folderPDF = "D";

    public Long getSaldoAwal() {
        return saldoAwal;
    }

    public void setSaldoAwal(Long saldoAwal) {
        this.saldoAwal = saldoAwal;
    }

    public Date getTanggalSaldoAwal() {
        return tanggalSaldoAwal;
    }

    public void setTanggalSaldoAwal(Date tanggalSaldoAwal) {
        this.tanggalSaldoAwal = tanggalSaldoAwal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFolderPDF() {
        return folderPDF;
    }

    public void setFolderPDF(String folderPDF) {
        this.folderPDF = folderPDF;
    }
    
    
    
    
}
