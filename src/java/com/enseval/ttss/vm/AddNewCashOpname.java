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
import org.zkoss.bind.annotation.*;

public class AddNewCashOpname {

    List<CashOpname> listOpname = new ArrayList<>();
    CashOpname opnameBaru = null;

    @AfterCompose
    public void initSetup(@ContextParam(ContextType.VIEW) Component view,
            @ExecutionArgParam("jenisKas") String jenisKas,
            @ExecutionArgParam("tglCutoff") Timestamp tglCutoff,
            @ExecutionArgParam("saldoSistem") Long saldoSistem) {

        listOpname = Ebean.find(CashOpname.class).orderBy("id desc").findList();
        opnameBaru = new CashOpname();
        opnameBaru.setTglOpname(new Timestamp(new Date().getTime()));
        opnameBaru.setPelaksana(Ebean.find(User.class, new AuthenticationServiceImpl().getUserCredential().getUser().getId()).getNama());
        opnameBaru.setJenisKas(jenisKas);
        opnameBaru.setTglCutoff(tglCutoff);
        opnameBaru.setSaldoSistem(saldoSistem);

        Selectors.wireComponents(view, this, false);
    }

    @Command
    @NotifyChange({"listOpname", "opnameBaru"})
    public void submitOpname() {
        try {
            opnameBaru.setSelisih(opnameBaru.getSaldoFisik() - opnameBaru.getSaldoSistem());
            Ebean.save(opnameBaru);
            opnameBaru = null;

            listOpname = Ebean.find(CashOpname.class).orderBy("id desc").findList();

        } catch (Exception e) {
            Messagebox.show("Parameter tidak lengkap", "Error", Messagebox.OK, Messagebox.ERROR);
            e.getMessage();

        }

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

    public List<CashOpname> getListOpname() {
        return listOpname;
    }

    public void setListOpname(List<CashOpname> listOpname) {
        this.listOpname = listOpname;
    }

    public CashOpname getOpnameBaru() {
        return opnameBaru;
    }

    public void setOpnameBaru(CashOpname opnameBaru) {
        this.opnameBaru = opnameBaru;
    }

}
