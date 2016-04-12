package com.enseval.ttss.vm;

import com.enseval.ttss.model.*;
import com.avaje.ebean.*;
import java.text.*;
import org.zkoss.zk.ui.*;
import org.zkoss.bind.annotation.*;
import java.util.*;

public class HistoryGiroVM {

    List<GiroHistory> listGiroHistory = new ArrayList<>();

    public HistoryGiroVM() {
        this.listGiroHistory = new ArrayList<>();

    }

    @AfterCompose
    public void initSetup(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("nomor") Long nomor) {
        listGiroHistory = Ebean.find(GiroHistory.class).where().eq("nomor", nomor).order("lastUpdate desc").findList();
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

    public List<GiroHistory> getListGiroHistory() {
        return listGiroHistory;
    }

    public void setListGiroHistory(List<GiroHistory> listGiroHistory) {
        this.listGiroHistory = listGiroHistory;
    }

    
   

}
