package com.enseval.ttss.vm;

import com.enseval.ttss.model.*;
import java.util.*;
import com.enseval.ttss.util.*;
import com.avaje.ebean.*;
import org.zkoss.zk.ui.util.*;
import org.zkoss.zk.ui.*;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Messagebox;

public class MenuPrinterSettingVM {

    List<Printer> printers = new ArrayList<>();
    Printer printer;
    String namaPrinter, keterangan;

    public MenuPrinterSettingVM() {
        this.printers = Ebean.find(Printer.class).order("id desc").findList();
    }

    @Command
    @NotifyChange({"printers","namaPrinter","keterangan"})
    public void tambahPrinter() {
        if(this.namaPrinter.isEmpty() || this.keterangan.isEmpty()){
            return;
        }
        Printer p = new Printer();
        p.setNamaPrinter(namaPrinter);
        p.setKeterangan(keterangan);
        Ebean.save(p);
        refresh();
        this.namaPrinter = "";
        this.keterangan = "";
    }

    @Command
    @NotifyChange("printers")
    public void hapusPrinter(@BindingParam("printernya") Printer printernya) {
        List<User> u = Ebean.find(User.class).where().eq("defPrinter", printernya).findList();
        for (User u1 : u) {
            u1.setDefPrinter(null);
            Ebean.save(u1);
        }
        

        Ebean.delete(printernya);
        refresh();

    }

    @GlobalCommand
    @NotifyChange("printers")
    private void refresh() {

        this.printers = Ebean.find(Printer.class).order("id desc").findList();

    }

    @AfterCompose
    public void initSetup() {
        this.printers = (List<Printer>) Ebean.find((Class) Printer.class).findList();
    }

    public Printer getPrinter() {
        return this.printer;
    }

    public void setPrinter(final Printer printer) {
        this.printer = printer;
    }

    public List<Printer> getPrinters() {
        return this.printers;
    }

    public void setPrinters(final List<Printer> printers) {
        this.printers = printers;
    }

    public String getNamaPrinter() {
        return namaPrinter;
    }

    public void setNamaPrinter(String namaPrinter) {
        this.namaPrinter = namaPrinter;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

}
