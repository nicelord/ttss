package com.enseval.ttss.vm;

import org.zkoss.zk.ui.select.annotation.*;
import org.zkoss.zk.ui.*;
import com.enseval.ttss.util.*;
import com.avaje.ebean.*;
import org.zkoss.zk.ui.select.*;
import org.zkoss.bind.*;
import java.util.*;
import com.enseval.ttss.model.*;
import java.util.logging.*;
import org.zkoss.zul.*;
import net.sf.jasperreports.engine.*;
import java.awt.print.*;
import java.sql.Timestamp;
import org.zkoss.bind.annotation.*;

public class CetakUlangTTSSVM {

    @Wire("#addTTSS")
    private Window win;
    @Wire("#penyetor")
    private Combobox cmb;
    TTSS ttss;
    List<DsPenyetor> listPenyetor;
    List<Printer> printers;
    User userLogin;
    String printernya;
    List<TTSS> listTag;

    public CetakUlangTTSSVM() {
        this.listPenyetor = new ArrayList<>();
        this.printers = new ArrayList<>();
    }

    @AfterCompose
    public void initSetup(@ContextParam(ContextType.VIEW) final Component view, @ExecutionArgParam("ttssnya") final TTSS ttssnya) {
        this.userLogin = Ebean.find(User.class, new AuthenticationServiceImpl().getUserCredential().getUser().getId());
        this.ttss = ttssnya;
        this.listPenyetor = Ebean.find(DsPenyetor.class).findList();
        this.printers = Ebean.find(Printer.class).findList();
        try {
            this.printernya = this.userLogin.getDefPrinter().getNamaPrinter();
        } catch (Exception e) {

            this.printernya = Ebean.find(Printer.class).findList().get(0).getNamaPrinter();

        }
        
        Selectors.wireComponents(view, this, false);
    }

    @Command
    public void UpdateTTSS() {
        
        //this.ttss.setWktTerima(new Timestamp(new Date().getTime()));
        Ebean.save((Object) this.ttss);
        DsPenyetor dsp = (DsPenyetor) Ebean.find((Class) DsPenyetor.class).where("nama = '" + this.ttss.getNamaPenyetor() + "'").findUnique();
        if (dsp == null) {
            dsp = new DsPenyetor();
            dsp.setNama(this.ttss.getNamaPenyetor().toUpperCase());
            Ebean.save((Object) dsp);
        }
        BindUtils.postGlobalCommand((String) null, (String) null, "refresh", (Map) null);
        this.win.detach();
    }

    @Command
    @NotifyChange({"printernya"})
    public void cetak() {
        try {
            Cetak c = new Cetak();
            c.setTtssnya(this.ttss);
            c.setUserLogin(this.userLogin);
            c.setWktCetak(new Timestamp(new Date().getTime()));
            c.doCetak(this.printernya, Util.setting("pdf_path"));
            this.UpdateTTSS();
            Ebean.save((Object) c);
        } catch (JRException | ArrayIndexOutOfBoundsException | PrinterException ex2) {
            Logger.getLogger(CetakUlangTTSSVM.class.getName()).log(Level.SEVERE, null, ex2);
            Messagebox.show(ex2.getMessage(), "Printer error", 1, "z-messagebox-icon z-messagebox-error");
        }
    }

    @Command
    @NotifyChange({"printernya"})
    public void cetakKeluar() {
        try {
            final Cetak c = new Cetak();
            c.setTtssnya(this.ttss);
            c.setUserLogin(this.userLogin);
            c.setWktCetak(new Timestamp(new Date().getTime()));
            c.doCetakKeluar(this.printernya, Util.setting("pdf_path"));
            this.UpdateTTSS();
            Ebean.save((Object) c);
        } catch (JRException | ArrayIndexOutOfBoundsException | PrinterException ex2) {

            Logger.getLogger(CetakUlangTTSSVM.class.getName()).log(Level.SEVERE, null, ex2);
            Messagebox.show(ex2.getMessage(), "Printer error", 1, "z-messagebox-icon z-messagebox-error");
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

    public List<TTSS> getListTag() {
        return listTag;
    }

    public void setListTag(List<TTSS> listTag) {
        this.listTag = listTag;
    }

}
