package com.enseval.ttss.model;

import java.io.*;
import javax.persistence.*;
import java.util.*;
import java.text.*;
import com.avaje.ebean.*;
import java.sql.Timestamp;

@Entity
public class TTSS implements Serializable
{
    @Id
    private Long nomor;
    Long nilai;
    @ManyToOne
    User userLogin;
    String namaPenyetor;
    @Temporal(TemporalType.TIMESTAMP)
    Timestamp wktTerima;
    @Lob
    String keterangan;
    @OneToMany(mappedBy = "ttssnya")
    List<Cetak> cetak;
    String tag;
    String jenisKas;
    String tipe;
    @Transient
    Long saldo;
    
    public TTSS() {
        this.cetak = new ArrayList<>();
    }
    
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
    
    public Boolean isSdhDiterima() {
        return !this.wktTerima.toString().isEmpty();
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
    
    public List<Cetak> getCetak() {
        return this.cetak;
    }
    
    public void setCetak(List<Cetak> cetak) {
        this.cetak = cetak;
    }
    
    public String getLastNomor() {
        DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        Date date = new Date();
        String tgl = dateFormat.format(date);
        try {
            String sql = "select max(nomor) from ttss where nomor like '" + tgl + "%'";
            SqlRow sqlRows = Ebean.createSqlQuery(sql).findUnique();
            String lastNomor = sqlRows.getLong("max(nomor)").toString();
            return lastNomor;
        }
        catch (Exception e) {
            return tgl + "000";
        }
    }
    
    public int itungCetakan() {
        String sql = "SELECT COUNT(*) FROM cetak WHERE ttssnya_nomor = '" + this.getNomor() + "';";
        SqlRow sqlRows = Ebean.createSqlQuery(sql).findUnique();
        Long jml = sqlRows.getLong("COUNT(*)");
        return jml.intValue();
    }
    
    public String getJenisKas() {
        return this.jenisKas;
    }
    
    public void setJenisKas(String jenisKas) {
        this.jenisKas = jenisKas;
    }
    
    public String getTag() {
        return this.tag;
    }
    
    public void setTag(String tag) {
        this.tag = tag;
    }
    
    public String getTipe() {
        return this.tipe;
    }
    
    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public Long getSaldo() {
        return saldo;
    }

    public void setSaldo(Long saldo) {
        this.saldo = saldo;
    }
    
    
}
