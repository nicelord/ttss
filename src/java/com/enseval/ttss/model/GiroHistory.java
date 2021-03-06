package com.enseval.ttss.model;

import java.io.*;
import javax.persistence.*;
import java.util.*;
import java.text.*;
import com.avaje.ebean.*;
import java.sql.Timestamp;

@Entity
public class GiroHistory implements Serializable {

    @Id @GeneratedValue Long id;
    Long nomor;
    Long nilai;
    String nomorGiro;
    String bank;
    @ManyToOne
    User userLogin;
    String namaPenyetor;
    @Temporal(TemporalType.TIMESTAMP)
    Timestamp wktTerima;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date tglKliring;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date tglJtTempo = new Date();
    @Lob
    String keterangan;
    String tag;
    String DKLK = "DK";
    String status = "OK";
    Long custID;
    String prosesKliring = "DONE";
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Timestamp lastUpdate;

    

    public Long getNomor() {
        return this.nomor;
    }

    public void setNomor(Long nomor) {
        this.nomor = nomor;
    }

    public Long getNilai() {
        return this.nilai;
    }

    public void setNilai(Long nilai) {
        this.nilai = nilai;
    }

    public User getUserLogin() {
        return this.userLogin;
    }

    public void setUserLogin(User userLogin) {
        this.userLogin = userLogin;
    }

    public Timestamp getWktTerima() {
        return this.wktTerima;
    }

    public void setWktTerima(Timestamp wktTerima) {
        this.wktTerima = wktTerima;
    }

    public String getNamaPenyetor() {
        return this.namaPenyetor;
    }

    public void setNamaPenyetor(String namaPenyetor) {
        this.namaPenyetor = namaPenyetor;
    }

    public String getKeterangan() {
        return this.keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getTglKliring() {
        return tglKliring;
    }

    public void setTglKliring(Date tglKliring) {
        this.tglKliring = tglKliring;
    }

    public String getDKLK() {
        return DKLK;
    }

    public void setDKLK(String DKLK) {
        this.DKLK = DKLK;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTglJtTempo() {
        return tglJtTempo;
    }

    public void setTglJtTempo(Date tglJtTempo) {
        this.tglJtTempo = tglJtTempo;
    }

    public String getNomorGiro() {
        return nomorGiro;
    }

    public void setNomorGiro(String nomorGiro) {
        this.nomorGiro = nomorGiro;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Long getCustID() {
        return custID;
    }

    public void setCustID(Long custID) {
        this.custID = custID;
    }

    public String getProsesKliring() {
        return prosesKliring;
    }

    public void setProsesKliring(String prosesKliring) {
        this.prosesKliring = prosesKliring;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }


}
