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
    long totalKeluar = 0L;
    long totalMasuk = 0L;

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

  
    
    
    
}
