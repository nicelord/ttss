/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enseval.ttss.model;

/**
 *
 * @author Reza Elborneo
 */
public class Balance {
    
    String periode;
    long saldoAwal = 0L;
    long totalKeluar = 0L;
    long totalMasuk = 0L;
    long saldoAkhir = 0L;
    
    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public long getTotalKeluar() {
        return totalKeluar;
    }

    public void setTotalKeluar(long totalKeluar) {
        this.totalKeluar = totalKeluar;
    }

    public long getTotalMasuk() {
        return totalMasuk;
    }

    public void setTotalMasuk(long totalMasuk) {
        this.totalMasuk = totalMasuk;
    }

    public long getSaldoAwal() {
        return saldoAwal;
    }

    public void setSaldoAwal(long saldoAwal) {
        this.saldoAwal = saldoAwal;
    }

    public long getSaldoAkhir() {
        return saldoAkhir;
    }

    public void setSaldoAkhir(long saldoAkhir) {
        this.saldoAkhir = saldoAkhir;
    }

  
    
    
    
}
