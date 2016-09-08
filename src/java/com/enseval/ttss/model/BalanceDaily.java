/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enseval.ttss.model;

import com.avaje.ebean.annotation.Sql;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author Reza Elborneo
 */
public class BalanceDaily {

    String tanggal;

    long totalCollector = 0L;
    long totalSalesman = 0L;
    long totalExpedisi = 0L;
    long totalLainnya = 0L;
    long totalHarianIn = 0L;

    long totalBank = 0L;
    long totalCNOutlet = 0L;
    long totalLainnyaOut = 0L;
    long totalHarianOut = 0L;

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
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

    public long getTotalBank() {
        return totalBank;
    }

    public void setTotalBank(long totalBank) {
        this.totalBank = totalBank;
    }

    public long getTotalCNOutlet() {
        return totalCNOutlet;
    }

    public void setTotalCNOutlet(long totalCNOutlet) {
        this.totalCNOutlet = totalCNOutlet;
    }

    public long getTotalLainnyaOut() {
        return totalLainnyaOut;
    }

    public void setTotalLainnyaOut(long totalLainnyaOut) {
        this.totalLainnyaOut = totalLainnyaOut;
    }

    public long getTotalHarianIn() {
        totalHarianIn = totalCollector + totalExpedisi + totalSalesman + totalLainnya;
        return totalHarianIn;
    }

    public void setTotalHarianIn(long totalHarianIn) {
        this.totalHarianIn = totalHarianIn;
    }

    public long getTotalHarianOut() {
        totalHarianOut = totalBank+totalCNOutlet+totalLainnyaOut;
        return totalHarianOut;
    }

    public void setTotalHarianOut(long totalHarianOut) {
        this.totalHarianOut = totalHarianOut;
    }

}
