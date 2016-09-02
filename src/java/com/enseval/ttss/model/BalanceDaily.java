/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enseval.ttss.model;

import java.util.Date;

/**
 *
 * @author Reza Elborneo
 */
public class BalanceDaily {
    
    Date tanggal;
    long saldoAwal = 0L;
    long totalCollector = 0L;
    long totalSalesman = 0L;
    long totalExpedisi = 0L;
    long totalLainnya = 0L;
    long saldoAkhir = 0L;

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public long getSaldoAwal() {
        return saldoAwal;
    }

    public void setSaldoAwal(long saldoAwal) {
        this.saldoAwal = saldoAwal;
    }

    public long getTotalCollector() {
        return totalCollector;
    }

    public void setTotalCollector(long totalCollector) {
        this.totalCollector = totalCollector;
    }

    public long getTotalSalesman() {
        return totalSalesman;
    }

    public void setTotalSalesman(long totalSalesman) {
        this.totalSalesman = totalSalesman;
    }

    public long getTotalExpedisi() {
        return totalExpedisi;
    }

    public void setTotalExpedisi(long totalExpedisi) {
        this.totalExpedisi = totalExpedisi;
    }

    public long getTotalLainnya() {
        return totalLainnya;
    }

    public void setTotalLainnya(long totalLainnya) {
        this.totalLainnya = totalLainnya;
    }

    public long getSaldoAkhir() {
        return saldoAkhir;
    }

    public void setSaldoAkhir(long saldoAkhir) {
        this.saldoAkhir = saldoAkhir;
    }
    
    
    
}
