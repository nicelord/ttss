package com.enseval.ttss.vm;

import java.sql.*;
import com.enseval.ttss.model.*;
import com.avaje.ebean.*;
import java.text.*;
import com.enseval.ttss.util.*;
import org.zkoss.zk.ui.*;
import org.zkoss.bind.annotation.*;
import java.util.*;

public class MenuBacktrapVM {

    List<Backtrap> listBacktrap = new ArrayList<>();
    List<Backtrap> listSelectedBacktrap = new ArrayList<>();
    Backtrap selectedBacktrap;

    Long totalNilai = 0L;
    String filterNomor = "";
    String filterNilai = "";
    String filterPenyetor = "";
    String filterTag = "";
    Timestamp tsAwal;
    Timestamp tsAkhir;
    User userLogin;

    @AfterCompose
    public void initSetup() {
        this.userLogin = (User) Ebean.find((Class) User.class, (Object) new AuthenticationServiceImpl().getUserCredential().getUser().getId());
        this.listBacktrap = Ebean.find(Backtrap.class).where().isNull("ttss").orderBy("createDate DESC").findList();

        Long nilai = 0L;
        for (Backtrap b : this.listBacktrap) {
            nilai += b.getNilai();
        }
        this.totalNilai = nilai;
    }

    @Command
    @NotifyChange({"selectedBacktrap", "totalNilai"})
    public void showDetail(@BindingParam("BT") Backtrap backtrap) {
        this.selectedBacktrap = backtrap;
    }
    
    @Command
    public void cetak() {
        Executions.createComponents("AddNewTTSS.zul", (Component) null, (Map) null);
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
    public void buatSetoranBaru() {
        Executions.createComponents("AddNewTTSS.zul", (Component) null, (Map) null);
    }

    @GlobalCommand
    @NotifyChange({"listBacktrap", "totalNilai"})
    public void refresh() {
        if (this.tsAwal != null | this.tsAkhir != null) {
            this.listBacktrap = Ebean.find(Backtrap.class)
                    .where().isNull("ttss")
                    .where().like("id", "%" + this.filterNomor + "%")
                    .where().like("nilai", "%" + this.filterNilai + "%")
                    .where().like("userBacktrap.nama", "%" + this.filterPenyetor + "%")
                    .or(Expr.like("tag", "%" + this.filterTag + "%"), Expr.isNull("tag"))
                    .where().ge("createDate", this.tsAwal).where().le("createDate", this.tsAkhir)
                    .orderBy("createDate DESC")
                    .findList();
        } else {
            this.listBacktrap = Ebean.find(Backtrap.class)
                    .where().isNull("ttss")
                    .where().like("id", "%" + this.filterNomor + "%")
                    .where().like("nilai", "%" + this.filterNilai + "%")
                    .where().like("userBacktrap.nama", "%" + this.filterPenyetor + "%")
                    .or(Expr.like("tag", "%" + this.filterTag + "%"), Expr.isNull("tag"))
                    .orderBy("createDate DESC")
                    .findList();
        }
        Long nilai = 0L;
        for (Backtrap b : this.listBacktrap) {
            nilai += b.getNilai();
        }
        this.totalNilai = nilai;
    }

    @Command
    @NotifyChange({"listBacktrap", "totalNilai"})
    public void saring() {
        if (this.tsAwal != null | this.tsAkhir != null) {
            this.listBacktrap = Ebean.find(Backtrap.class)
                    .where().isNull("ttss")
                    .where().like("id", "%" + this.filterNomor + "%")
                    .where().like("nilai", "%" + this.filterNilai + "%")
                    .where().like("userBacktrap.nama", "%" + this.filterPenyetor + "%")
                    .or(Expr.like("tag", "%" + this.filterTag + "%"), Expr.isNull("tag"))
                    .where().ge("createDate", this.tsAwal).where().le("createDate", this.tsAkhir)
                    .orderBy("createDate DESC")
                    .findList();

        } else {
            this.listBacktrap = Ebean.find(Backtrap.class)
                    .where().isNull("ttss")
                    .where().like("id", "%" + this.filterNomor + "%")
                    .where().like("nilai", "%" + this.filterNilai + "%")
                    .where().like("userBacktrap.nama", "%" + this.filterPenyetor + "%")
                    .or(Expr.like("tag", "%" + this.filterTag + "%"), Expr.isNull("tag"))
                    .orderBy("createDate DESC")
                    .findList();

        }
        Long nilai = 0L;
        for (Backtrap b : this.listBacktrap) {
            nilai += b.getNilai();
        }
        this.totalNilai = nilai;
    }

    @Command
    @NotifyChange({"listBacktrap", "totalNilai"})
    public void saringTgl() {
        if (this.tsAwal != null | this.tsAkhir != null) {
            this.listBacktrap = Ebean.find(Backtrap.class)
                    .where().isNull("ttss")
                    .where().like("id", "%" + this.filterNomor + "%")
                    .where().like("nilai", "%" + this.filterNilai + "%")
                    .where().like("userBacktrap.nama", "%" + this.filterPenyetor + "%")
                    .or(Expr.like("tag", "%" + this.filterTag + "%"), Expr.isNull("tag"))
                    .where().ge("createDate", this.tsAwal).where().le("createDate", this.tsAkhir)
                    .orderBy("createDate DESC")
                    .findList();

        } else {
            this.listBacktrap = Ebean.find(Backtrap.class)
                    .where().isNull("ttss")
                    .where().like("id", "%" + this.filterNomor + "%")
                    .where().like("nilai", "%" + this.filterNilai + "%")
                    .where().like("userBacktrap.nama", "%" + this.filterPenyetor + "%")
                    .or(Expr.like("tag", "%" + this.filterTag + "%"), Expr.isNull("tag"))
                    .orderBy("createDate DESC")
                    .findList();
        }
        Long nilai = 0L;
        for (Backtrap b : this.listBacktrap) {
            nilai += b.getNilai();
        }
        this.totalNilai = nilai;
    }

    @Command
    @NotifyChange({"listBacktrap", "totalNilai"})
    public void resetSaringWkt() {
        this.tsAwal = null;
        this.tsAkhir = null;
        this.listBacktrap = Ebean.find(Backtrap.class)
                .where().isNull("ttss")
                .where().like("id", "%" + this.filterNomor + "%")
                .where().like("nilai", "%" + this.filterNilai + "%")
                .where().like("userBacktrap.nama", "%" + this.filterPenyetor + "%")
                .or(Expr.like("tag", "%" + this.filterTag + "%"), Expr.isNull("tag"))
                .orderBy("createDate DESC")
                .findList();
        Long nilai = 0L;
        for (Backtrap b : this.listBacktrap) {
            nilai += b.getNilai();
        }
        this.totalNilai = nilai;
    }

    @Command
    @NotifyChange({"listBacktrap", "totalNilai"})
    public void hapusSelectedBacktrap() {
        for (Backtrap b : this.listSelectedBacktrap) {
            Ebean.delete(b);
        }
        this.refresh();
    }

    public List<Backtrap> getListBacktrap() {
        return listBacktrap;
    }

    public void setListBacktrap(List<Backtrap> listBacktrap) {
        this.listBacktrap = listBacktrap;
    }

    public List<Backtrap> getListSelectedBacktrap() {
        return listSelectedBacktrap;
    }

    public void setListSelectedBacktrap(List<Backtrap> listSelectedBacktrap) {
        this.listSelectedBacktrap = listSelectedBacktrap;
    }

    public Backtrap getSelectedBacktrap() {
        return selectedBacktrap;
    }

    public void setSelectedBacktrap(Backtrap selectedBacktrap) {
        this.selectedBacktrap = selectedBacktrap;
    }

    public Long getTotalNilai() {
        return this.totalNilai;
    }

    public void setTotalNilai(final Long totalNilai) {
        this.totalNilai = totalNilai;
    }

    public String getFilterNomor() {
        return this.filterNomor;
    }

    public void setFilterNomor(final String filterNomor) {
        this.filterNomor = filterNomor;
    }

    public String getFilterNilai() {
        return this.filterNilai;
    }

    public void setFilterNilai(final String filterNilai) {
        this.filterNilai = filterNilai;
    }

    public String getFilterPenyetor() {
        return this.filterPenyetor;
    }

    public void setFilterPenyetor(final String filterPenyetor) {
        this.filterPenyetor = filterPenyetor;
    }

    public Timestamp getTsAwal() {
        return this.tsAwal;
    }

    public void setTsAwal(final Timestamp tsAwal) {
        this.tsAwal = tsAwal;
    }

    public Timestamp getTsAkhir() {
        return this.tsAkhir;
    }

    public void setTsAkhir(final Timestamp tsAkhir) {
        this.tsAkhir = tsAkhir;
    }

    public User getUserLogin() {
        return this.userLogin;
    }

    public void setUserLogin(final User userLogin) {
        this.userLogin = userLogin;
    }

    public String getFilterTag() {
        return this.filterTag;
    }

    public void setFilterTag(final String filterTag) {
        this.filterTag = filterTag;
    }
}
