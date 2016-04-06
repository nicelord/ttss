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
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Checkbox;

/**
 *
 * @author user
 */
public class MenuSettingLainVM {


    String saldoAwal = Util.setting("saldo_awal_transfer");
    String saldoAwalDropping = Util.setting("saldo_awal_dropping");

    Date tglSaldoAwal = Util.toDate(Util.setting("tgl_saldo_awal"));
    String pdfPath = Util.setting("pdf_path");

    String smtpHost = Util.setting("smtp_host");
    String smtpPort = Util.setting("smtp_port");
    String smtpUsername = Util.setting("smtp_username");
    String smtpPassword = Util.setting("smtp_password");
    String emailFrom = Util.setting("email_from");
    String emailTo = Util.setting("email_to");
    String emailAktif = Util.setting("email_aktif");
    String cabang = Util.setting("nama_cabang");

    @AfterCompose
    public void initSetup(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
    }

    @Command
    @NotifyChange("setting")
    public void simpanSetting() {

        Setting set;
        set = Ebean.find(Setting.class).where().eq("namaSetting", "nama_cabang").findUnique();
        set.setNilaiSetting(cabang);
        Ebean.save(set);
        
        if (Ebean.find(Setting.class).where().eq("namaSetting", "saldo_awal_transfer").findUnique().getNilaiSetting().equals("0")) {
            set = Ebean.find(Setting.class).where().eq("namaSetting", "saldo_awal_transfer").findUnique();
            set.setNilaiSetting(saldoAwal);
            Ebean.save(set);

        }

        if (Ebean.find(Setting.class).where().eq("namaSetting", "saldo_awal_dropping").findUnique().getNilaiSetting().equals("0")) {
            set = Ebean.find(Setting.class).where().eq("namaSetting", "saldo_awal_dropping").findUnique();
            set.setNilaiSetting(saldoAwalDropping);
            Ebean.save(set);
        }

        set = Ebean.find(Setting.class).where().eq("namaSetting", "tgl_saldo_awal").findUnique();
        set.setNilaiSetting(Util.toString(tglSaldoAwal));
        Ebean.save(set);

        set = Ebean.find(Setting.class).where().eq("namaSetting", "pdf_path").findUnique();
        set.setNilaiSetting(pdfPath);
        Ebean.save(set);

        set = Ebean.find(Setting.class).where().eq("namaSetting", "smtp_host").findUnique();
        set.setNilaiSetting(smtpHost);
        Ebean.save(set);

        set = Ebean.find(Setting.class).where().eq("namaSetting", "smtp_port").findUnique();
        set.setNilaiSetting(smtpPort);
        Ebean.save(set);

        set = Ebean.find(Setting.class).where().eq("namaSetting", "smtp_username").findUnique();
        set.setNilaiSetting(smtpUsername);
        Ebean.save(set);

        set = Ebean.find(Setting.class).where().eq("namaSetting", "smtp_password").findUnique();
        set.setNilaiSetting(smtpPassword);
        Ebean.save(set);

        set = Ebean.find(Setting.class).where().eq("namaSetting", "email_from").findUnique();
        set.setNilaiSetting(emailFrom);
        Ebean.save(set);

        set = Ebean.find(Setting.class).where().eq("namaSetting", "email_to").findUnique();
        set.setNilaiSetting(emailTo);
        Ebean.save(set);
        
        set = Ebean.find(Setting.class).where().eq("namaSetting", "email_aktif").findUnique();
        set.setNilaiSetting(emailAktif);
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

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getSmtpUsername() {
        return smtpUsername;
    }

    public void setSmtpUsername(String smtpUsername) {
        this.smtpUsername = smtpUsername;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    public void setSmtpPassword(String smtpPassword) {
        this.smtpPassword = smtpPassword;
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getEmailAktif() {
        return emailAktif;
    }

    public void setEmailAktif(String emailAktif) {
        this.emailAktif = emailAktif;
    }

    public String getCabang() {
        return cabang;
    }

    public void setCabang(String cabang) {
        this.cabang = cabang;
    }

 


}
