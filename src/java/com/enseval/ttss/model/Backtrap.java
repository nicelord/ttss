package com.enseval.ttss.model;

import java.io.*;
import javax.persistence.*;
import java.util.*;
import java.text.*;
import com.avaje.ebean.*;
import java.sql.Timestamp;

@Entity
public class Backtrap implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    Long nilai;
    @ManyToOne
    UserBacktrap userBacktrap;
    @Lob
    String keterangan = "";
    String tag;
    @Temporal(TemporalType.TIMESTAMP)
    Timestamp createDate;
    TTSS ttss;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNilai() {
        return nilai;
    }

    public void setNilai(Long nilai) {
        this.nilai = nilai;
    }

    public UserBacktrap getUserBacktrap() {
        return userBacktrap;
    }

    public void setUserBacktrap(UserBacktrap userBacktrap) {
        this.userBacktrap = userBacktrap;
    }


    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public TTSS getTtss() {
        return ttss;
    }

    public void setTtss(TTSS ttss) {
        this.ttss = ttss;
    }

}
