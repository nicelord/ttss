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

public class AddNewTTSSKeluar {

    @Wire("#addTTSS")
    private Window win;
    @Wire("#penyetor")
    private Combobox cmb;
    @Wire("#tag")
    private Combobox cmbTag;
    TTSS ttss;
    List<DsPenyetor> listPenyetor;
    List<Printer> printers;
    List<TTSS> listTag;
    User userLogin;
    String printernya;

    public AddNewTTSSKeluar() {
        this.listPenyetor = new ArrayList<DsPenyetor>();
        this.printers = new ArrayList<Printer>();
        this.listTag = new ArrayList<TTSS>();
    }

    @AfterCompose
    public void initSetup(@ContextParam(ContextType.VIEW) final Component view) {
        this.userLogin = (User) Ebean.find((Class) User.class, (Object) new AuthenticationServiceImpl().getUserCredential().getUser().getId());
        this.listPenyetor = (List<DsPenyetor>) Ebean.find((Class) DsPenyetor.class).findList();
        this.printers = (List<Printer>) Ebean.find((Class) Printer.class).findList();
        this.printernya = this.userLogin.getDefPrinter().getNamaPrinter();
        this.listTag = (List<TTSS>) Ebean.find((Class) TTSS.class).select("tag").setDistinct(true).findList();
        this.ttss = new TTSS();
        this.ttss.setJenisKas("KAS TRANSFER");
        Selectors.wireComponents(view, (Object) this, false);
    }

    @Command
    public void saveNewTTSS() {
        
        this.ttss.setNomor(Long.parseLong(this.ttss.getLastNomor()) + 1L);
        this.ttss.setUserLogin(this.userLogin);
        this.ttss.setTipe("keluar");
        this.ttss.setWktTerima(new Timestamp(new Date().getTime()));
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
            this.saveNewTTSS();
            final Cetak c = new Cetak();
            c.setTtssnya(this.ttss);
            c.setUserLogin(this.userLogin);
            c.setWktCetak(new Timestamp(new Date().getTime()));
            c.doCetakKeluar(this.printernya, Util.setting("pdf_path"));
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

    public List<TTSS> getListTag() {
        return this.listTag;
    }

    public void setListTag(final List<TTSS> listTag) {
        this.listTag = listTag;
    }
}
