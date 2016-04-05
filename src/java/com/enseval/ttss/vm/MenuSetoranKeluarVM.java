package com.enseval.ttss.vm;

import com.enseval.ttss.model.*;
import com.avaje.ebean.*;
import java.text.*;
import com.enseval.ttss.util.*;
import org.zkoss.zk.ui.*;
import org.zkoss.bind.annotation.*;
import net.sf.jasperreports.engine.util.*;
import net.sf.jasperreports.engine.data.*;
import java.util.*;
import net.sf.jasperreports.engine.export.*;
import java.util.logging.*;
import javax.activation.*;
import org.zkoss.zul.*;
import net.sf.jasperreports.engine.*;
import java.io.*;
import java.sql.Timestamp;

public class MenuSetoranKeluarVM {

    List<TTSS> listTTSS;
    TTSS ttss;
    Long totalNilai;
    String filterNomor;
    String filterNilai;
    String filterPenyetor;
    String filterJenisKas;
    String filterTag;
    Timestamp tsAwal;
    Timestamp tsAkhir;
    List<Cetak> cetaks;
    User userLogin;
    List<TTSS> selectedTTSS;

    public MenuSetoranKeluarVM() {
        this.listTTSS = new ArrayList<TTSS>();
        this.totalNilai = 0L;
        this.filterNomor = "";
        this.filterNilai = "";
        this.filterPenyetor = "";
        this.filterJenisKas = "";
        this.filterTag = "";
        this.cetaks = new ArrayList<Cetak>();
        this.selectedTTSS = new ArrayList<TTSS>();
    }

    @AfterCompose
    public void initSetup() {
        this.userLogin = (User) Ebean.find((Class) User.class, (Object) new AuthenticationServiceImpl().getUserCredential().getUser().getId());
        this.listTTSS = (List<TTSS>) Ebean.find((Class) TTSS.class).where().eq("tipe", (Object) "keluar").setMaxRows(50).orderBy("wktTerima desc").findList();
        Long nilai = 0L;
        for (final TTSS ttss1 : this.listTTSS) {
            nilai += ttss1.getNilai();
        }
        this.totalNilai = nilai;
    }

    @Command
    public void tes(@BindingParam("s") final String s) {
        Messagebox.show(s);
    }

    @Command
    @NotifyChange({"ttss", "totalNilai"})
    public void showDetail(@BindingParam("TTSS") final TTSS ttss) {
        this.ttss = ttss;
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
    public String terbilang(final long nilai) {
        return Rupiah.convert((int) nilai).toUpperCase() + " RUPIAH";
    }

    @Command
    public void buatSetoranBaru() {
        Executions.createComponents("AddNewTTSSKeluar.zul", (Component) null, (Map) null);
    }

    @Command
    @NotifyChange({"ttss"})
    public void cetakUlang() {
        if (this.ttss == null) {
            Messagebox.show("Setoran belum dipilih!", "ERROR", 1, "z-messagebox-icon z-messagebox-error");
        } else {
            final Map args = new HashMap();
            args.put("ttssnya", this.ttss);
            Executions.createComponents("CetakUlangTTSSKeluar.zul", (Component) null, args);
        }
    }

    @Command
    @NotifyChange({"ttss"})
    public void liatRevisi() {
        if (this.ttss == null) {
            Messagebox.show("Setoran belum dipilih!", "ERROR", 1, "z-messagebox-icon z-messagebox-error");
        } else {
            final Map args = new HashMap();
            args.put("ttssnya", this.ttss);
            Executions.createComponents("DaftarRevisiTTSS.zul", (Component) null, args);
        }
    }

    @GlobalCommand
    @NotifyChange({"listTTSS", "totalNilai"})
    public void refresh() {
        if (this.tsAwal != null | this.tsAkhir != null) {
            this.listTTSS = (List<TTSS>) Ebean.find((Class) TTSS.class).where("tipe = 'keluar' and nomor like '%" + this.filterNomor + "%' and nilai like '%" + this.filterNilai + "%' and namaPenyetor like '%" + this.filterPenyetor + "%' and tag like '%" + this.filterTag + "%' and jenisKas like '%" + this.filterJenisKas + "%' and wktTerima >= '" + this.tsAwal + "' and wktTerima <= '" + this.tsAkhir + "'").orderBy("wktTerima desc").findList();
        } else {
            this.listTTSS = (List<TTSS>) Ebean.find((Class) TTSS.class).where("tipe = 'keluar' and nomor like '%" + this.filterNomor + "%' and nilai like '%" + this.filterNilai + "%' and namaPenyetor like '%" + this.filterPenyetor + "%' and tag like '%" + this.filterTag + "%' and jenisKas like '%" + this.filterJenisKas + "%'").orderBy("wktTerima desc").findList();
        }
        Long nilai = 0L;
        for (final TTSS ttss1 : this.listTTSS) {
            nilai += ttss1.getNilai();
        }
        this.totalNilai = nilai;
    }

    @Command
    @NotifyChange({"listTTSS", "totalNilai"})
    public void saring() {
        if (this.tsAwal != null | this.tsAkhir != null) {
            this.listTTSS = (List<TTSS>) Ebean.find((Class) TTSS.class).where("tipe = 'keluar' and nomor like '%" + this.filterNomor + "%' and nilai like '%" + this.filterNilai + "%' and namaPenyetor like '%" + this.filterPenyetor + "%' and tag like '%" + this.filterTag + "%' and jenisKas like '%" + this.filterJenisKas + "%' and wktTerima >= '" + this.tsAwal + "' and wktTerima <= '" + this.tsAkhir + "'").orderBy("wktTerima desc").findList();
        } else {
            this.listTTSS = (List<TTSS>) Ebean.find((Class) TTSS.class).where("tipe = 'keluar' and nomor like '%" + this.filterNomor + "%' and nilai like '%" + this.filterNilai + "%' and namaPenyetor like '%" + this.filterPenyetor + "%' and tag like '%" + this.filterTag + "%' and jenisKas like '%" + this.filterJenisKas + "%'").orderBy("wktTerima desc").findList();
        }
        Long nilai = 0L;
        for (final TTSS ttss1 : this.listTTSS) {
            nilai += ttss1.getNilai();
        }
        this.totalNilai = nilai;
    }

    @Command
    @NotifyChange({"listTTSS", "totalNilai"})
    public void saringTgl() {
        if (this.tsAwal != null | this.tsAkhir != null) {
            this.listTTSS = (List<TTSS>) Ebean.find((Class) TTSS.class).where("tipe = 'keluar' and nomor like '%" + this.filterNomor + "%' and nilai like '%" + this.filterNilai + "%' and namaPenyetor like '%" + this.filterPenyetor + "%' and tag like '%" + this.filterTag + "%' and jenisKas like '%" + this.filterJenisKas + "%' and wktTerima >= '" + this.tsAwal + "' and wktTerima <= '" + this.tsAkhir + "'").orderBy("wktTerima desc").findList();
        } else {
            this.listTTSS = (List<TTSS>) Ebean.find((Class) TTSS.class).where("tipe = 'keluar' and nomor like '%" + this.filterNomor + "%' and nilai like '%" + this.filterNilai + "%' and namaPenyetor like '%" + this.filterPenyetor + "%' and tag like '%" + this.filterTag + "%'  and jenisKas like '%" + this.filterJenisKas + "%'").orderBy("wktTerima desc").findList();
        }
        Long nilai = 0L;
        for (final TTSS ttss1 : this.listTTSS) {
            nilai += ttss1.getNilai();
        }
        this.totalNilai = nilai;
    }

    @Command
    @NotifyChange({"listTTSS", "totalNilai"})
    public void resetSaringWkt() {
        this.tsAwal = null;
        this.tsAkhir = null;
        this.listTTSS = (List<TTSS>) Ebean.find((Class) TTSS.class).where("tipe = 'keluar' and nomor like '%" + this.filterNomor + "%' and nilai like '%" + this.filterNilai + "%' and namaPenyetor like '%" + this.filterPenyetor + "%' and tag like '%" + this.filterTag + "%' and jenisKas like '%" + this.filterJenisKas + "%'").orderBy("wktTerima desc").findList();
        Long nilai = 0L;
        for (final TTSS ttss1 : this.listTTSS) {
            nilai += ttss1.getNilai();
        }
        this.totalNilai = nilai;
    }

    @Command
    @NotifyChange({"listTTSS"})
    public void downloadXLS() {
        File filenya = new File(Util.setting("pdf_path") + "ttss-reports.xls");
        try {
            InputStream streamReport = JRLoader.getFileInputStream(Executions.getCurrent().getDesktop().getWebApp().getRealPath("/") + "/report/setoran.keluar.jasper");
            JRDataSource datasource = new JRBeanCollectionDataSource(this.listTTSS);
            JRDataSource beanColDataSource = new JRBeanCollectionDataSource(this.listTTSS);
            Map map = new HashMap();
            map.put("REPORT_DATA_SOURCE", datasource);
            map.put("TTSS", beanColDataSource);
            map.put("TOTAL", this.totalNilai);
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
    @NotifyChange({"listTTSS", "totalNilai"})
    public void hapusSelectedTTSS() {
        for (final TTSS ttss1 : this.selectedTTSS) {
            try {
                Ebean.delete((Class) Cetak.class, (Collection) Ebean.find((Class) Cetak.class).where("ttssnya.nomor = '" + ttss1.getNomor() + "'").findIds());
                Ebean.delete((Class) TTSS.class, (Object) ttss1.getNomor());
            } catch (Exception e) {
                Logger.getLogger(MenuSetoranKeluarVM.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        this.refresh();
    }

    @Command
    @NotifyChange({"listTTSS", "totalNilai"})
    public void filterKasTransfer() {
        this.filterJenisKas = "KAS TRANSFER";
        this.refresh();
    }

    @Command
    @NotifyChange({"listTTSS", "totalNilai"})
    public void filterKasDropping() {
        this.filterJenisKas = "KAS DROPPING";
        this.refresh();
    }

    @Command
    @NotifyChange({"listTTSS", "totalNilai"})
    public void resetFilterKas() {
        this.filterJenisKas = "";
        this.refresh();
    }

    public List<TTSS> getListTTSS() {
        return this.listTTSS;
    }

    public void setListTTSS(final List<TTSS> listTTSS) {
        this.listTTSS = listTTSS;
    }

    public TTSS getTtss() {
        return this.ttss;
    }

    public void setTtss(final TTSS ttss) {
        this.ttss = ttss;
    }

    public Long getTotalNilai() {
        return this.totalNilai;
    }

    public void setTotalNilai(final Long totalNilai) {
        this.totalNilai = totalNilai;
    }

    public String getFilterNomor() {
        return this.filterNomor;
    }

    public void setFilterNomor(final String filterNomor) {
        this.filterNomor = filterNomor;
    }

    public String getFilterNilai() {
        return this.filterNilai;
    }

    public void setFilterNilai(final String filterNilai) {
        this.filterNilai = filterNilai;
    }

    public String getFilterPenyetor() {
        return this.filterPenyetor;
    }

    public void setFilterPenyetor(final String filterPenyetor) {
        this.filterPenyetor = filterPenyetor;
    }

    public Timestamp getTsAwal() {
        return this.tsAwal;
    }

    public void setTsAwal(final Timestamp tsAwal) {
        this.tsAwal = tsAwal;
    }

    public Timestamp getTsAkhir() {
        return this.tsAkhir;
    }

    public void setTsAkhir(final Timestamp tsAkhir) {
        this.tsAkhir = tsAkhir;
    }

    public List<Cetak> getCetaks() {
        return this.cetaks;
    }

    public void setCetaks(final List<Cetak> cetaks) {
        this.cetaks = cetaks;
    }

    public User getUserLogin() {
        return this.userLogin;
    }

    public void setUserLogin(final User userLogin) {
        this.userLogin = userLogin;
    }

    public List<TTSS> getSelectedTTSS() {
        return this.selectedTTSS;
    }

    public void setSelectedTTSS(final List<TTSS> selectedTTSS) {
        this.selectedTTSS = selectedTTSS;
    }

    public String getFilterJenisKas() {
        return this.filterJenisKas;
    }

    public void setFilterJenisKas(final String filterJenisKas) {
        this.filterJenisKas = filterJenisKas;
    }

    public String getFilterTag() {
        return this.filterTag;
    }

    public void setFilterTag(final String filterTag) {
        this.filterTag = filterTag;
    }
}
