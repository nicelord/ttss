package com.enseval.ttss.vm;

import org.zkoss.zk.ui.select.annotation.*;
import org.zkoss.zk.ui.*;
import com.enseval.ttss.util.*;
import com.avaje.ebean.*;
import org.zkoss.zk.ui.select.*;
import java.util.*;
import com.enseval.ttss.model.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.zkoss.zul.*;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.zkoss.bind.annotation.*;

public class MenuCashOpname {

    List<CashOpname> listOpname = new ArrayList<>();
    CashOpname opnameBaru = null;
    String filterJenis = "SEMUA";

    @AfterCompose
    public void initSetup(@ContextParam(ContextType.VIEW) Component view) {

        listOpname = Ebean.find(CashOpname.class).orderBy("id desc").findList();
        opnameBaru = new CashOpname();
        opnameBaru.setTglOpname(new Timestamp(new Date().getTime()));
        opnameBaru.setPelaksana(Ebean.find(User.class, new AuthenticationServiceImpl().getUserCredential().getUser().getId()).getNama());

        Selectors.wireComponents(view, this, false);
    }

    @Command
    @NotifyChange("listOpname")
    public void doFilter() {
        if (filterJenis.equals("-")) {
            listOpname = Ebean.find(CashOpname.class).orderBy("id desc").findList();
        } else {
            listOpname = Ebean.find(CashOpname.class).where().eq("jenisKas", filterJenis).orderBy("id desc").findList();
        }

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

    @Command
    @NotifyChange({"listOpname"})
    public void downloadXLS() {

        File filenya = new File(Util.setting("pdf_path") + "opname-reports.xls");
        try {
            InputStream streamReport = JRLoader.getFileInputStream(Executions.getCurrent().getDesktop().getWebApp().getRealPath("/") + "/report/opname.jasper");
            JRDataSource datasource = new JRBeanCollectionDataSource(listOpname);
            JRDataSource beanColDataSource = new JRBeanCollectionDataSource(listOpname);
            Map map = new HashMap();
            map.put("REPORT_DATA_SOURCE", datasource);
            map.put("OPNAME", beanColDataSource);
            map.put("JENIS", filterJenis);
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
            e.printStackTrace();
        }
        filenya.delete();
    }

    @Command
    @NotifyChange({"listOpname"})
    public void downloadPDF() {

        File filenya = new File(Util.setting("pdf_path") + "opname-reports.pdf");
        try {
            InputStream streamReport = JRLoader.getFileInputStream(Executions.getCurrent().getDesktop().getWebApp().getRealPath("/") + "/report/opname.pdf.jasper");
            JRDataSource datasource = new JRBeanCollectionDataSource(listOpname);
            JRDataSource beanColDataSource = new JRBeanCollectionDataSource(listOpname);
            Map map = new HashMap();
            map.put("REPORT_DATA_SOURCE", datasource);
            map.put("OPNAME", beanColDataSource);
            map.put("JENIS", filterJenis);
            final JasperPrint report = JasperFillManager.fillReport(streamReport, map);
            final OutputStream outputStream = new FileOutputStream(filenya);
    //        final JRXlsExporter exporterXLS = new JRXlsExporter();
     //       JRPdfExporter exporterPDF = new JRPdfExporter();
    //        exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, report);
    //        exporterPDF.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
//            exporterPDF.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
//            exporterPDF.setParameter((JRExporterParameter) JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
//            exporterPDF.setParameter((JRExporterParameter) JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
//            exporterPDF.setParameter((JRExporterParameter) JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
//            exporterPDF.setParameter((JRExporterParameter) JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
//            exporterPDF.setParameter((JRExporterParameter) JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);

            JRExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(report));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            configuration.setMetadataAuthor("Reza Elborneo");  //why not set some config as we like
            exporter.setConfiguration(configuration);
            exporter.exportReport();

      //      exporterPDF.exportReport();
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
            e.printStackTrace();
        }
        filenya.delete();
    }

    public String getFilterJenis() {
        return filterJenis;
    }

    public void setFilterJenis(String filterJenis) {
        this.filterJenis = filterJenis;
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
