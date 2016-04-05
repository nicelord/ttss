/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enseval.ttss.vm;

import com.avaje.ebean.Ebean;
import com.enseval.ttss.model.Setting;
import com.enseval.ttss.util.Util;
import java.util.Date;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.util.Clients;

/**
 *
 * @author user
 */
public class MenuSettingLainVM {

    String saldoAwal = Util.setting("saldo_awal_transfer");
    String saldoAwalDropping = Util.setting("saldo_awal_dropping");

    Date tglSaldoAwal = Util.toDate(Util.setting("tgl_saldo_awal"));
    String pdfPath = Util.setting("pdf_path");

    @Command
    @NotifyChange("setting")
    public void simpanSetting() {

        Setting set = Ebean.find(Setting.class).where().eq("namaSetting", "saldo_awal_transfer").findUnique();
        set.setNilaiSetting(saldoAwal);
        Ebean.save(set);

        set = Ebean.find(Setting.class).where().eq("namaSetting", "saldo_awal_dropping").findUnique();
        set.setNilaiSetting(saldoAwalDropping);
        Ebean.save(set);

        set = Ebean.find(Setting.class).where().eq("namaSetting", "tgl_saldo_awal").findUnique();
        set.setNilaiSetting(Util.toString(tglSaldoAwal));
        Ebean.save(set);

        set = Ebean.find(Setting.class).where().eq("namaSetting", "pdf_path").findUnique();
        set.setNilaiSetting(pdfPath);
        Ebean.save(set);

        Clients.showNotification("Setting disimpan!");
    }

    public String getSaldoAwal() {
        return saldoAwal;
    }

    public void setSaldoAwal(String saldoAwal) {
        this.saldoAwal = saldoAwal;
    }

    public Date getTglSaldoAwal() {
        return tglSaldoAwal;
    }

    public void setTglSaldoAwal(Date tglSaldoAwal) {
        this.tglSaldoAwal = tglSaldoAwal;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public String getSaldoAwalDropping() {
        return saldoAwalDropping;
    }

    public void setSaldoAwalDropping(String saldoAwalDropping) {
        this.saldoAwalDropping = saldoAwalDropping;
    }

}
