package com.enseval.ttss.vm;

import org.zkoss.zk.ui.select.annotation.*;
import org.zkoss.zk.ui.*;
import com.enseval.ttss.util.*;
import com.avaje.ebean.*;
import org.zkoss.zk.ui.select.*;
import java.text.*;
import org.zkoss.bind.*;
import java.util.*;
import com.enseval.ttss.model.*;
import org.zkoss.zul.*;
import net.sf.jasperreports.engine.*;
import java.awt.print.*;
import java.sql.Timestamp;
import org.zkoss.bind.annotation.*;

public class AddNewTTSS {

    @Wire("#addTTSS")
    private Window win;
    @Wire("#penyetor")
    private Combobox cmb;
    @Wire("#tag")
    private Combobox cmbTag;
    TTSS ttss;
    List<DsPenyetor> listPenyetor;
    List<Printer> printers;
    User userLogin;
    String printernya = "";
    Backtrap backtrap;
    boolean isMultipleCetak = false;

    public AddNewTTSS() {
        this.listPenyetor = new ArrayList<>();
        this.printers = new ArrayList<>();
    }

    public AddNewTTSS(Backtrap backtrap, String printernya, User userlogin) {
        this.userLogin = userlogin;
        this.printernya = printernya;
        this.backtrap = backtrap;
        this.isMultipleCetak = true;

        this.ttss = new TTSS();
        this.ttss.setJenisKas("KAS TRANSFER");
        this.ttss.setNilai(this.backtrap.getNilai());
        this.ttss.setNamaPenyetor(this.backtrap.getUserBacktrap().getNama());
        this.ttss.setTag(this.backtrap.getTag());
        this.ttss.setKeterangan(this.backtrap.getKeterangan());

    }

    @AfterCompose
    public void initSetup(@ContextParam(ContextType.VIEW) final Component view,
            @ExecutionArgParam("backtrapnya") Backtrap backtrap) {

        this.userLogin = Ebean.find(User.class, new AuthenticationServiceImpl().getUserCredential().getUser().getId());
        this.backtrap = backtrap;

        this.printers = (List<Printer>) Ebean.find((Class) Printer.class).findList();
        if (this.userLogin.getDefPrinter() == null) {
            if (!Ebean.find(Printer.class).findList().isEmpty()) {
                this.userLogin.setDefPrinter(Ebean.find(Printer.class).findList().get(0));
            } else {
                Messagebox.show("Mohon tambahkan printer dulu!", "Printer error", 1, "z-messagebox-icon z-messagebox-error");
                view.detach();
                return;
            }
        }
        this.printernya = this.userLogin.getDefPrinter().getNamaPrinter();

        this.ttss = new TTSS();
        this.ttss.setJenisKas("KAS TRANSFER");
        this.ttss.setTag("COLLECTOR");

        if (backtrap != null) {
            this.ttss.setNilai(this.backtrap.getNilai());
            this.ttss.setNamaPenyetor(this.backtrap.getUserBacktrap().getNama());
            this.ttss.setTag(this.backtrap.getTag());
            this.ttss.setKeterangan(this.backtrap.getKeterangan());
        }

        this.listPenyetor = (List<DsPenyetor>) Ebean.find((Class) DsPenyetor.class).findList();

        Selectors.wireComponents(view, (Object) this, false);
    }

    @Command
    public void saveNewTTSS() {

        this.ttss.setNomor(Long.parseLong(this.ttss.getLastNomor()) + 1L);
        this.ttss.setUserLogin(this.userLogin);
        this.ttss.setTipe("masuk");
        this.ttss.setWktTerima(new Timestamp(new Date().getTime()));
        Ebean.save((Object) this.ttss);
        DsPenyetor dsp = (DsPenyetor) Ebean.find((Class) DsPenyetor.class).where("nama = '" + this.ttss.getNamaPenyetor() + "'").findUnique();
        if (dsp == null) {
            dsp = new DsPenyetor();
            dsp.setNama(this.ttss.getNamaPenyetor().toUpperCase());
            Ebean.save(dsp);
        }

        try {
            this.backtrap = Ebean.find(Backtrap.class, backtrap.getId());
            this.backtrap.setTtss(ttss);
            Ebean.save(this.backtrap);
        } catch (NullPointerException npe) {

        }

        BindUtils.postGlobalCommand((String) null, (String) null, "refresh", (Map) null);
        if(!isMultipleCetak){
            this.win.detach();
        }
        
    }

    @Command
    @NotifyChange({"printernya"})
    public void cetak() {
        this.saveNewTTSS();

        try {
            final Cetak c = new Cetak();
            c.setTtssnya(this.ttss);
            c.setUserLogin(this.userLogin);
            c.setWktCetak(new Timestamp(new Date().getTime()));
            c.doCetak(this.printernya, Util.setting("pdf_path"));
            Ebean.save(c);
        } catch (JRException jrex) {
            Messagebox.show(jrex.getMessage(), "Printer error", 1, "z-messagebox-icon z-messagebox-error");
        } catch (ArrayIndexOutOfBoundsException | PrinterException arrex) {
            Messagebox.show(arrex.getMessage(), "Printer error", 1, "z-messagebox-icon z-messagebox-error");
        }
    }

    public TTSS getTtss() {
        return this.ttss;
    }

    public void setTtss(final TTSS ttss) {
        this.ttss = ttss;
    }

    public User getUserLogin() {
        return this.userLogin;
    }

    public void setUserLogin(final User userLogin) {
        this.userLogin = userLogin;
    }

    public List<DsPenyetor> getListPenyetor() {
        return this.listPenyetor;
    }

    public void setListPenyetor(final List<DsPenyetor> listPenyetor) {
        this.listPenyetor = listPenyetor;
    }

    public Combobox getCmb() {
        return this.cmb;
    }

    public void setCmb(final Combobox cmb) {
        this.cmb = cmb;
    }

    public List<Printer> getPrinters() {
        return this.printers;
    }

    public void setPrinters(final List<Printer> printers) {
        this.printers = printers;
    }

    public String getPrinternya() {
        return this.printernya;
    }

    public void setPrinternya(final String printernya) {
        this.printernya = printernya;
    }

    public Combobox getCmbTag() {
        return this.cmbTag;
    }

    public void setCmbTag(final Combobox cmbTag) {
        this.cmbTag = cmbTag;
    }

    public Backtrap getBacktrap() {
        return backtrap;
    }

    public void setBacktrap(Backtrap backtrap) {
        this.backtrap = backtrap;
    }

    public boolean isIsMultipleCetak() {
        return isMultipleCetak;
    }

    public void setIsMultipleCetak(boolean isMultipleCetak) {
        this.isMultipleCetak = isMultipleCetak;
    }

}
