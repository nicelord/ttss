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

    
    String namaSetting;
    String nilaiSetting;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaSetting() {
        return namaSetting;
    }

    public void setNamaSetting(String namaSetting) {
        this.namaSetting = namaSetting;
    }

    public String getNilaiSetting() {
        return nilaiSetting;
    }

    public void setNilaiSetting(String nilaiSetting) {
        this.nilaiSetting = nilaiSetting;
    }

    
    
    
    
}
