package com.enseval.ttss.vm;

import org.zkoss.zk.ui.select.annotation.*;
import org.zkoss.zk.ui.*;
import com.enseval.ttss.util.*;
import com.avaje.ebean.*;
import org.zkoss.zk.ui.select.*;
import java.util.*;
import com.enseval.ttss.model.*;
import org.zkoss.zul.*;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.bind.annotation.*;

public class MenuInputCashOpname {

    CashOpname opnameBaru = null;

    @AfterCompose
    public void initSetup() {
        opnameBaru = new CashOpname();
        opnameBaru.setPelaksana(Ebean.find(User.class, new AuthenticationServiceImpl().getUserCredential().getUser().getId()).getNama());
        opnameBaru.setTglCutoff(new Timestamp(new Date().getTime()));
        opnameBaru.setJenisKas("KAS TRANSFER");
    }

    @Command
    @NotifyChange("opnameBaru")
    public void submitOpname() {
        try {
            opnameBaru.setSaldoSistem(hitungSaldo());
            opnameBaru.setSelisih(opnameBaru.getSaldoFisik() - opnameBaru.getSaldoSistem());
            opnameBaru.setTglOpname(new Timestamp(new Date().getTime()));
            Ebean.save(opnameBaru);
            initSetup();

        } catch (Exception e) {
            Messagebox.show("Kesalahan input \n"+e.toString(), "ERROR", Messagebox.OK, Messagebox.ERROR);
            Logger.getLogger(MenuInputCashOpname.class.getName()).log(Level.SEVERE, null, e);

        }
        
        Messagebox.show("Opname insert success", "SUCCESS", Messagebox.OK, Messagebox.INFORMATION);

    }

    public long hitungSaldo() {
        List<TTSS> listTTSS = Ebean.find(TTSS.class).where().ge("wktTerima", Util.setting("tgl_saldo_awal")).orderBy("wktTerima desc").findList();

        if (!opnameBaru.getJenisKas().equals("SEMUA")) {
            listTTSS = Ebean.find(TTSS.class)
                    .where().eq("jenisKas", opnameBaru.getJenisKas())
                    .where().ge("wktTerima", Util.toDate(Util.setting("tgl_saldo_awal")))
                    .orderBy("wktTerima desc").findList();
        }

        Long nilaiSaldo = Long.valueOf(Util.setting("saldo_awal_transfer")) + Long.valueOf(Util.setting("saldo_awal_dropping"));
        if (opnameBaru.getJenisKas().equals("KAS TRANSFER")) {
            nilaiSaldo = Long.valueOf(Util.setting("saldo_awal_transfer"));
        }

        if (opnameBaru.getJenisKas().equals("KAS DROPPING")) {
            nilaiSaldo = Long.valueOf(Util.setting("saldo_awal_dropping"));

        }

        for (int i = listTTSS.size() - 1; i >= 0; i--) {
            if (listTTSS.get(i).getWktTerima().after(Util.toDate(Util.setting("tgl_saldo_awal")))) {
                if (listTTSS.get(i).getTipe().equals("masuk")) {
                    listTTSS.get(i).setSaldo(nilaiSaldo += listTTSS.get(i).getNilai());
                } else {
                    listTTSS.get(i).setSaldo(nilaiSaldo -= listTTSS.get(i).getNilai());
                }
            }

        }

        return nilaiSaldo;

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

    public CashOpname getOpnameBaru() {
        return opnameBaru;
    }

    public void setOpnameBaru(CashOpname opnameBaru) {
        this.opnameBaru = opnameBaru;
    }

}
