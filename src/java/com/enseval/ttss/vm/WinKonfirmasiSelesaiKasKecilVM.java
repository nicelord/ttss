/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enseval.ttss.vm;

import com.avaje.ebean.Ebean;
import com.enseval.ttss.model.Giro;
import com.enseval.ttss.model.KasKecil;
import java.util.Date;
import java.util.List;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

/**
 *
 * @author user
 */
public class WinKonfirmasiSelesaiKasKecilVM {
    @Wire("#win_konfirm_selesai_kaskecil")
    Window win;
    KasKecil kasKecil;
    
    @AfterCompose
    public void initSetup(@ContextParam(ContextType.VIEW) Component view,
            @ExecutionArgParam("selected_kasKecil") KasKecil selectedKasKecil) {

        this.kasKecil = selectedKasKecil;
        Selectors.wireComponents(view, this, false);
    }
    
    
    @Command
    public void updateKasKecil(){
        this.kasKecil.setStatus("SELESAI");
        this.kasKecil.setTanggalSelesai(new Date());
        Ebean.update(this.kasKecil);
        BindUtils.postGlobalCommand(null, null, "saring", null);
        win.detach();
        
    }

    public Window getWin() {
        return win;
    }

    public void setWin(Window win) {
        this.win = win;
    }

    public KasKecil getKasKecil() {
        return kasKecil;
    }

    public void setKasKecil(KasKecil kasKecil) {
        this.kasKecil = kasKecil;
    }
    
    
}
