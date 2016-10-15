/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enseval.ttss.model;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author user
 */

@Entity
public class KasKecil {
    
    @Id
    @GeneratedValue
    Long nomor ;
    @Temporal(TemporalType.DATE)
    Date tanggalBuat;
    @Temporal(TemporalType.DATE)
    Date tanggalSelesai;
    String nama;
    String namaPetugas;
    String dept;
    Long nilai;
    String keperluan;
    String catatanBuat;
    String catatanSelesai;
    String status;

    public Long getNomor() {
        return nomor;
    }

    public void setNomor(Long nomor) {
        this.nomor = nomor;
    }

    public Date getTanggalBuat() {
        return tanggalBuat;
    }

    public void setTanggalBuat(Date tanggalBuat) {
        this.tanggalBuat = tanggalBuat;
    }

    public Date getTanggalSelesai() {
        return tanggalSelesai;
    }

    public void setTanggalSelesai(Date tanggalSelesai) {
        this.tanggalSelesai = tanggalSelesai;
    }



    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Long getNilai() {
        return nilai;
    }

    public void setNilai(Long nilai) {
        this.nilai = nilai;
    }

    public String getKeperluan() {
        return keperluan;
    }

    public void setKeperluan(String keperluan) {
        this.keperluan = keperluan;
    }

    public String getCatatanBuat() {
        return catatanBuat;
    }

    public void setCatatanBuat(String catatanBuat) {
        this.catatanBuat = catatanBuat;
    }

    public String getCatatanSelesai() {
        return catatanSelesai;
    }

    public void setCatatanSelesai(String catatanSelesai) {
        this.catatanSelesai = catatanSelesai;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNamaPetugas() {
        return namaPetugas;
    }

    public void setNamaPetugas(String namaPetugas) {
        this.namaPetugas = namaPetugas;
    }

 
    
    
}
