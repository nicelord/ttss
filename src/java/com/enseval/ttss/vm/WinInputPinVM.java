/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enseval.ttss.vm;

import com.avaje.ebean.Ebean;
import com.enseval.ttss.model.Backtrap;
import com.enseval.ttss.model.Giro;
import com.enseval.ttss.model.GiroHistory;
import com.enseval.ttss.model.UserBacktrap;
import com.enseval.ttss.util.MailNotif;
import com.enseval.ttss.util.Rupiah;
import com.enseval.ttss.util.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * @author Reza Elborneo
 */
public class WinInputPinVM {

    @Wire("#win_konfirm_kliring")
    Window win;
    UserBacktrap userBacktrap = null;
    String pin = "";
    Backtrap backtrap;
    String terbilang = "";
    String formatRupiah = "";

    @AfterCompose
    public void initSetup(@ContextParam(ContextType.VIEW) Component view,
            @ExecutionArgParam("backtrapnya") Backtrap backtrap) {

        this.backtrap = backtrap;
        this.terbilang = Rupiah.convert(backtrap.getNilai()).toUpperCase().concat(" RUPIAH");
        this.formatRupiah = Rupiah.format(this.backtrap.getNilai());
        Selectors.wireComponents(view, this, false);
    }

    
    
    public boolean validasiPin(){
        userBacktrap = Ebean.find(UserBacktrap.class).where().eq("pin", pin).findUnique();
        return userBacktrap != null;
    }
    
    @Command
    public void saveBacktrap(){
        if(validasiPin()){
            backtrap.setUserBacktrap(userBacktrap);
            backtrap.setTag(userBacktrap.getDivisi());
            backtrap.setCreateDate(new Timestamp(new Date().getTime()));
            Ebean.save(backtrap);
            win.detach();
        }
    }
    

    public Window getWin() {
        return win;
    }

    public void setWin(Window win) {
        this.win = win;
    }

    public UserBacktrap getUserBacktrap() {
        return userBacktrap;
    }

    public void setUserBacktrap(UserBacktrap userBacktrap) {
        this.userBacktrap = userBacktrap;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Backtrap getBacktrap() {
        return backtrap;
    }

    public void setBacktrap(Backtrap backtrap) {
        this.backtrap = backtrap;
    }

    public String getTerbilang() {
        return terbilang;
    }

    public void setTerbilang(String terbilang) {
        this.terbilang = terbilang;
    }

    public String getFormatRupiah() {
        return formatRupiah;
    }

    public void setFormatRupiah(String formatRupiah) {
        this.formatRupiah = formatRupiah;
    }


}
