package com.enseval.ttss.model;

import java.io.*;
import java.sql.Timestamp;
import javax.persistence.*;
import java.util.*;

@Entity
public class UserBacktrap implements Serializable
{
    @Id
    @GeneratedValue
    private Long id;
    String nama;
    String divisi;
    String pin;
    String defaultPin;
    @Temporal(TemporalType.DATE)
    Date pinExpired;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDivisi() {
        return divisi;
    }

    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getDefaultPin() {
        return defaultPin;
    }

    public void setDefaultPin(String defaultPin) {
        this.defaultPin = defaultPin;
    }

    public Date getPinExpired() {
        return pinExpired;
    }

    public void setPinExpired(Date pinExpired) {
        this.pinExpired = pinExpired;
    }
    
    
}
