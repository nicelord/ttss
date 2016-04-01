package com.enseval.ttss.model;

import com.avaje.ebean.Ebean;
import javax.persistence.*;
import javax.print.attribute.standard.*;
import java.awt.print.*;
import com.enseval.ttss.util.*;
import org.zkoss.zk.ui.*;
import net.sf.jasperreports.engine.type.*;
import net.sf.jasperreports.engine.export.*;
import java.util.logging.*;
import javax.print.attribute.*;
import javax.print.*;
import java.text.*;
import java.util.*;
import java.io.*;
import java.sql.Timestamp;
import net.sf.jasperreports.engine.*;

@Entity
public class Cetak {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    TTSS ttssnya;
    @ManyToOne
    User userLogin;
    @Temporal(TemporalType.TIMESTAMP)
    Timestamp wktCetak;
    String filePath;
    int cetakanKe;
    
    @Transient
    Setting setting = Ebean.find(Setting.class, 1);

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TTSS getTtssnya() {
        return this.ttssnya;
    }

    public void setTtssnya(TTSS ttssnya) {
        this.ttssnya = ttssnya;
    }

    public User getUserLogin() {
        return this.userLogin;
    }

    public void setUserLogin(User userLogin) {
        this.userLogin = userLogin;
    }

    public Timestamp getWktCetak() {
        return this.wktCetak;
    }

    public void setWktCetak(Timestamp wktCetak) {
        this.wktCetak = wktCetak;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getCetakanKe() {
        return this.cetakanKe;
    }

    public void setCetakanKe(int cetakanKe) {
        this.cetakanKe = cetakanKe;
    }

    public void doCetak(String printernya) throws JRException, PrinterException, ArrayIndexOutOfBoundsException {
        AttributeSet aset = new HashAttributeSet();
        aset.add(new PrinterName(printernya, null));
        PrintService[] printService = PrintServiceLookup.lookupPrintServices(null, aset);
        if (printService == null) {
            System.err.println("NULL PRINTER");
            throw new PrinterException("No Printer Attached / Shared to the server");
        }
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Date date = new Date();
        String tgl = dateFormat.format(date);
        Map map = new HashMap();
        map.put("TERBILANG", Rupiah.convert(this.ttssnya.getNilai().intValue()) + " RUPIAH");
        map.put("JUMLAH_UANG", Rupiah.format(this.ttssnya.getNilai()));
        map.put("KETERANGAN", this.ttssnya.getKeterangan());
        map.put("PENYETOR", this.ttssnya.getNamaPenyetor().toUpperCase());
        map.put("NOMOR", this.ttssnya.getNomor().toString());
        map.put("PENERIMA", this.userLogin.getNama().toUpperCase());
        map.put("TANGGAL", tgl.toUpperCase());
        map.put("JENIS_KAS", this.ttssnya.getJenisKas().toUpperCase());
        map.put("CETAK_ULANG", Integer.toString(this.ttssnya.itungCetakan() + 1));
        JasperPrint jasperPrint = JasperFillManager.fillReport(Executions.getCurrent().getDesktop().getWebApp().getRealPath("/") + "/report/tes.jasper", map, (JRDataSource) new JREmptyDataSource());
        jasperPrint.setOrientation(OrientationEnum.PORTRAIT);
        JRExporter exporter = (JRExporter) new JRPrintServiceExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, (Object) jasperPrint);
        System.out.println(printService[0].getAttributes());
        exporter.setParameter((JRExporterParameter) JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, (Object) printService[0].getAttributes());
        exporter.setParameter((JRExporterParameter) JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, (Object) Boolean.FALSE);
        exporter.setParameter((JRExporterParameter) JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, (Object) Boolean.FALSE);
        exporter.exportReport();
        OutputStream output = null;
        InputStream input = null;
        try {
           
            String pdfPath = setting.getFolderPDF();
            this.cetakanKe = this.ttssnya.itungCetakan() + 1;
            File dir = new File(pdfPath + this.ttssnya.getNomor() + "/");
            dir.mkdirs();
            File f = new File(dir, this.ttssnya.getNomor() + "_" + this.cetakanKe + ".pdf");
            output = new FileOutputStream(f);
            JasperExportManager.exportReportToPdfStream(jasperPrint, output);
            output.close();
            input.close();
            this.filePath = f.getAbsolutePath();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Cetak.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex2) {
            Logger.getLogger(Cetak.class.getName()).log(Level.SEVERE, null, ex2);
        }
    }

    public void doCetakKeluar(String printernya) throws JRException, PrinterException, ArrayIndexOutOfBoundsException {
        AttributeSet aset = new HashAttributeSet();
        aset.add(new PrinterName(printernya, null));
        PrintService[] printService = PrintServiceLookup.lookupPrintServices(null, aset);
        if (printService == null) {
            System.err.println("NULL PRINTER");
            throw new PrinterException("No Printer Attached / Shared to the server");
        }
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Date date = new Date();
        String tgl = dateFormat.format(date);
        Map map = new HashMap();
        map.put("TERBILANG", Rupiah.convert(this.ttssnya.getNilai().intValue()) + " RUPIAH");
        map.put("JUMLAH_UANG", Rupiah.format(this.ttssnya.getNilai()));
        map.put("KETERANGAN", this.ttssnya.getKeterangan());
        map.put("PENYETOR", this.ttssnya.getNamaPenyetor().toUpperCase());
        map.put("NOMOR", this.ttssnya.getNomor().toString());
        map.put("PENERIMA", this.userLogin.getNama().toUpperCase());
        map.put("TANGGAL", tgl.toUpperCase());
        map.put("JENIS_KAS", this.ttssnya.getJenisKas().toUpperCase());
        map.put("CETAK_ULANG", Integer.toString(this.ttssnya.itungCetakan() + 1));
        JasperPrint jasperPrint = JasperFillManager.fillReport(Executions.getCurrent().getDesktop().getWebApp().getRealPath("/") + "/report/tesKeluar.jasper", map, (JRDataSource) new JREmptyDataSource());
        jasperPrint.setOrientation(OrientationEnum.PORTRAIT);
        JRExporter exporter = (JRExporter) new JRPrintServiceExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, (Object) jasperPrint);
        System.out.println(printService[0].getAttributes());
        exporter.setParameter((JRExporterParameter) JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, (Object) printService[0].getAttributes());
        exporter.setParameter((JRExporterParameter) JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, (Object) Boolean.FALSE);
        exporter.setParameter((JRExporterParameter) JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, (Object) Boolean.FALSE);
        exporter.exportReport();
        OutputStream output = null;
        InputStream input = null;
        try {
           
            String pdfPath = setting.getFolderPDF();
            this.cetakanKe = this.ttssnya.itungCetakan() + 1;
            File dir = new File(pdfPath + this.ttssnya.getNomor() + "/");
            dir.mkdirs();
            File f = new File(dir, this.ttssnya.getNomor() + "_" + this.cetakanKe + ".pdf");
            output = new FileOutputStream(f);
            JasperExportManager.exportReportToPdfStream(jasperPrint, output);
            output.close();
            input.close();
            this.filePath = f.getAbsolutePath();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Cetak.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex2) {
            Logger.getLogger(Cetak.class.getName()).log(Level.SEVERE, null, ex2);
        }
    }
}
