/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enseval.ttss.vm;

import com.avaje.ebean.Ebean;
import com.enseval.ttss.model.Giro;
import com.enseval.ttss.model.GiroHistory;
import com.enseval.ttss.util.MailNotif;
import com.enseval.ttss.util.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
public class WinKonfirmasiTglKliringVM {

    @Wire("#win_konfirm_kliring")
    Window win;
    List<Giro> selectedGiro = new ArrayList<>();
    Date tglKliring = new Date();
    boolean doDownload = true;

    @AfterCompose
    public void initSetup(@ContextParam(ContextType.VIEW) Component view,
            @ExecutionArgParam("selected_giro") List<Giro> selectedGiro,
            @ExecutionArgParam("do_download") boolean doDownload) {

        this.selectedGiro = selectedGiro;
        this.doDownload = doDownload;
        Selectors.wireComponents(view, this, false);
    }

    @Command
    @NotifyChange({"selectedGiro"})
    public void downloadDanProsess() {

        if (doDownload) {
            File filenya = new File(Util.setting("pdf_path") + "giro-reports.xls");
            try {
                InputStream streamReport = JRLoader.getFileInputStream(Executions.getCurrent().getDesktop().getWebApp().getRealPath("/") + "/report/giro.jasper");
                JRDataSource datasource = new JRBeanCollectionDataSource(selectedGiro);
                JRDataSource beanColDataSource = new JRBeanCollectionDataSource(selectedGiro);
                Map map = new HashMap();
                map.put("REPORT_DATA_SOURCE", datasource);
                map.put("GIRO", beanColDataSource);
                final JasperPrint report = JasperFillManager.fillReport(streamReport, map);
                final OutputStream outputStream = new FileOutputStream(filenya);
                final JRXlsExporter exporterXLS = new JRXlsExporter();
                exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, (Object) report);
                exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, (Object) outputStream);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
                exporterXLS.setParameter((JRExporterParameter) JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, (Object) Boolean.FALSE);
                exporterXLS.setParameter((JRExporterParameter) JRXlsExporterParameter.IS_DETECT_CELL_TYPE, (Object) Boolean.TRUE);
                exporterXLS.setParameter((JRExporterParameter) JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, (Object) Boolean.FALSE);
                exporterXLS.setParameter((JRExporterParameter) JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, (Object) Boolean.TRUE);
                exporterXLS.setParameter((JRExporterParameter) JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, (Object) Boolean.TRUE);
                exporterXLS.exportReport();
                streamReport.close();
                outputStream.close();
            } catch (JRException | FileNotFoundException ex4) {
                Logger.getLogger(MenuSetoranKeluarVM.class.getName()).log(Level.SEVERE, null, ex4);
            } catch (IOException ex2) {
                Logger.getLogger(MenuSetoranKeluarVM.class.getName()).log(Level.SEVERE, null, ex2);
            }
            FileInputStream inputStream = null;
            try {
                if (filenya.exists()) {
                    inputStream = new FileInputStream(filenya);
                    Filedownload.save((InputStream) inputStream, new MimetypesFileTypeMap().getContentType(filenya), filenya.getName());
                }
            } catch (FileNotFoundException e) {
                Messagebox.show(e.getMessage(), "ERROR", Messagebox.OK, Messagebox.ERROR);
            }
            filenya.delete();

            for (Giro giroSelected : selectedGiro) {

                giroSelected.setProsesKliring("DONE");
                giroSelected.setTglKliring(tglKliring);

                Ebean.save(giroSelected);

            }
            BindUtils.postGlobalCommand(null, null, "refresh", null);
            BindUtils.postGlobalCommand(null, null, "UpdateGiro", null);
            win.detach();

        } else {
            for (Giro giroSelected : selectedGiro) {
                if (Util.setting("email_aktif").equals("YA")) {
                    Giro gh = Ebean.find(Giro.class).where().eq("nomor", giroSelected.getNomor()).findUnique();
                    //System.out.println(gh.getProsesKliring() + "//////////////////////////////////////////");
                    if (gh.getProsesKliring().equals("DITOLAK")) {
                        MailNotif mailNotif = new MailNotif();
                        mailNotif.emailTolakUpdate(giroSelected);

                    }
                }

                
                giroSelected.setProsesKliring("DONE");
                giroSelected.setTglKliring(tglKliring);
                Ebean.save(giroSelected);

            }
            BindUtils.postGlobalCommand(null, null, "UpdateGiro", null);
            win.detach();
        }

    }

    public Window getWin() {
        return win;
    }

    public void setWin(Window win) {
        this.win = win;
    }

    public List<Giro> getSelectedGiro() {
        return selectedGiro;
    }

    public void setSelectedGiro(List<Giro> selectedGiro) {
        this.selectedGiro = selectedGiro;
    }

    public Date getTglKliring() {
        return tglKliring;
    }

    public void setTglKliring(Date tglKliring) {
        this.tglKliring = tglKliring;
    }

}
