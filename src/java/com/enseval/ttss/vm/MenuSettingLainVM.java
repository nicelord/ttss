/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enseval.ttss.vm;

import com.avaje.ebean.Ebean;
import com.enseval.ttss.model.Setting;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.util.Clients;

/**
 *
 * @author user
 */
public class MenuSettingLainVM {
    Setting setting = Ebean.find(Setting.class, 1);
    
    
    
    @Command
    @NotifyChange("setting")
    public void simpanSetting(){
        Ebean.save(this.setting);
        Clients.showNotification("Setting disimpan!");
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }
    
    
}
