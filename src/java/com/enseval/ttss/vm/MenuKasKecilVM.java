/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enseval.ttss.vm;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.enseval.ttss.model.Giro;
import com.enseval.ttss.model.KasKecil;
import com.enseval.ttss.model.TTSS;
import com.enseval.ttss.model.User;
import com.enseval.ttss.util.Rupiah;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;

/**
 *
 * @author user
 */
public class MenuKasKecilVM {

    List<KasKecil> listKasKecil;
    List<KasKecil> selectedKasKecil;
    User userLogin;
    Long saldo;
    KasKecil kasKecil;

    String filterNomor = "", filterNilai = "", filterNama = "", filterDept = "", filterStatus = "";
    Date tglBuatDari, tglBuatSampai, tglSelesaiDari, tglSelesaiSampai;

    @AfterCompose
    public void initSetup(@ContextParam(ContextType.VIEW) Component view) {

        this.listKasKecil = Ebean.find(KasKecil.class).orderBy("tanggalBuat desc").findList();

        Long nilai = 0L;
        for (KasKecil kk : this.listKasKecil) {
            nilai += kk.getNilai();
        }
        this.saldo = nilai;

    }

    @Command
    public String format(final long nilai) {
        final DecimalFormat kursIndonesia = (DecimalFormat) NumberFormat.getCurrencyInstance();
        final DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia.format(nilai);
    }

    @Command
    public String terbilang(long nilai) {
        return Rupiah.convert(nilai).toUpperCase() + " RUPIAH";
    }

    @Command
    @NotifyChange({"kasKecil"})
    public void showDetail(@BindingParam("kasKecil") final KasKecil kasKecil) {
        this.kasKecil = kasKecil;
    }

    @Command
    @NotifyChange({"listKasKecil", "saldo"})
    public void saring() {

        this.listKasKecil = Ebean.find(KasKecil.class)
                .where().like("nomor", "%" + this.filterNomor + "%")
                .where().like("nilai", "%" + this.filterNilai + "%")
                .where().like("nama", "%" + this.filterNama + "%")
                .or(Expr.like("dept", "%" + this.filterDept + "%"), Expr.isNull("dept"))
                .where().like("status", "%" + this.filterStatus + "%")
                .orderBy("nomor DESC")
                .findList();

        Long nilai = 0L;
        for (KasKecil kk : this.listKasKecil) {
            nilai += kk.getNilai();
        }
        this.saldo = nilai;
    }

    public List<KasKecil> getListKasKecil() {
        return listKasKecil;
    }

    public void setListKasKecil(List<KasKecil> listKasKecil) {
        this.listKasKecil = listKasKecil;
    }

    public Long getSaldo() {
        return saldo;
    }

    public void setSaldo(Long saldo) {
        this.saldo = saldo;
    }

    @Command
    public void buatTransaksi() {
        Executions.createComponents("AddNewkasKecil.zul", (Component) null, (Map) null);
    }

    public User getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(User userLogin) {
        this.userLogin = userLogin;
    }

    public KasKecil getKasKecil() {
        return kasKecil;
    }

    public void setKasKecil(KasKecil kasKecil) {
        this.kasKecil = kasKecil;
    }

    public List<KasKecil> getSelectedKasKecil() {
        return selectedKasKecil;
    }

    public void setSelectedKasKecil(List<KasKecil> selectedKasKecil) {
        this.selectedKasKecil = selectedKasKecil;
    }

    public String getFilterNomor() {
        return filterNomor;
    }

    public void setFilterNomor(String filterNomor) {
        this.filterNomor = filterNomor;
    }

    public String getFilterNilai() {
        return filterNilai;
    }

    public void setFilterNilai(String filterNilai) {
        this.filterNilai = filterNilai;
    }

    public String getFilterNama() {
        return filterNama;
    }

    public void setFilterNama(String filterNama) {
        this.filterNama = filterNama;
    }

    public String getFilterDept() {
        return filterDept;
    }

    public void setFilterDept(String filterDept) {
        this.filterDept = filterDept;
    }

    public Date getTglBuatDari() {
        return tglBuatDari;
    }

    public void setTglBuatDari(Date tglBuatDari) {
        this.tglBuatDari = tglBuatDari;
    }

    public Date getTglBuatSampai() {
        return tglBuatSampai;
    }

    public void setTglBuatSampai(Date tglBuatSampai) {
        this.tglBuatSampai = tglBuatSampai;
    }

    public Date getTglSelesaiDari() {
        return tglSelesaiDari;
    }

    public void setTglSelesaiDari(Date tglSelesaiDari) {
        this.tglSelesaiDari = tglSelesaiDari;
    }

    public Date getTglSelesaiSampai() {
        return tglSelesaiSampai;
    }

    public void setTglSelesaiSampai(Date tglSelesaiSampai) {
        this.tglSelesaiSampai = tglSelesaiSampai;
    }

    public String getFilterStatus() {
        return filterStatus;
    }

    public void setFilterStatus(String filterStatus) {
        this.filterStatus = filterStatus;
    }

}
