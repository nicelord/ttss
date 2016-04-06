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

public class MenuBalanceVM {

    List<TTSS> listTTSS;
    TTSS ttss;
    Long totalNilai;
    String filterNomor;
    String filterNilai;
    String filterPenyetor;
    String filterJenisKas = "SEMUA";
    String filterTag;
    Timestamp cutOffDate;
    List<Cetak> cetaks;
    User userLogin;
    List<TTSS> selectedTTSS;
    Long saldo;
    Timestamp filterCutoff = new Timestamp(new Date().getTime());

    String saldoAwal = Util.setting("saldo_awal_transfer");
    String saldoAwalDropping = Util.setting("saldo_awal_dropping");
    Date tglSaldoAwal = Util.toDate(Util.setting("tgl_saldo_awal"));

    public MenuBalanceVM() {
        this.listTTSS = new ArrayList<>();
        this.totalNilai = 0L;
        this.filterNomor = "";
        this.filterNilai = "";
        this.filterPenyetor = "";
        this.filterJenisKas = "SEMUA";
        this.filterTag = "";
        this.cetaks = new ArrayList<>();
        this.selectedTTSS = new ArrayList<>();
        this.saldo = 0L;

    }

    @AfterCompose
    public void initSetup() throws ParseException {

        this.userLogin = Ebean.find(User.class, new AuthenticationServiceImpl().getUserCredential().getUser().getId());
        this.listTTSS = Ebean.find(TTSS.class).where().ge("wktTerima", Util.setting("tgl_saldo_awal")).orderBy("wktTerima desc").findList();

        Long nilai = 0L;
        for (final TTSS ttss1 : this.listTTSS) {
            nilai += ttss1.getNilai();
        }
        this.totalNilai = nilai;

        Long nilaiSaldo = Long.valueOf(Util.setting("saldo_awal_transfer")) + Long.valueOf(Util.setting("saldo_awal_dropping"));
        if (filterJenisKas.equals("KAS TRANSFER")) {
            nilaiSaldo = Long.valueOf(Util.setting("saldo_awal_transfer"));
        }

        if (filterJenisKas.equals("KAS DROPPING")) {
            nilaiSaldo = Long.valueOf(Util.setting("saldo_awal_dropping"));

        }

        for (int i = this.listTTSS.size() - 1; i >= 0; i--) {
            if (this.listTTSS.get(i).getWktTerima().after(Util.toDate(Util.setting("tgl_saldo_awal")))) {
                if (this.listTTSS.get(i).getTipe().equals("masuk")) {
                    this.listTTSS.get(i).setSaldo(nilaiSaldo += this.listTTSS.get(i).getNilai());
                } else {
                    this.listTTSS.get(i).setSaldo(nilaiSaldo -= this.listTTSS.get(i).getNilai());
                }
            }

        }
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
        Executions.createComponents("AddNewTTSS.zul", (Component) null, (Map) null);
    }

    @Command
    @NotifyChange({"ttss"})
    public void cetakUlang() {
        if (this.ttss == null) {
            Messagebox.show("Setoran belum dipilih!", "ERROR", 1, "z-messagebox-icon z-messagebox-error");
        } else {
            final Map args = new HashMap();
            args.put("ttssnya", this.ttss);
            Executions.createComponents("CetakUlangTTSS.zul", (Component) null, args);
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

    @Command
    @NotifyChange("*")
    public void refresh() {
        this.listTTSS = Ebean.find(TTSS.class).where().ge("wktTerima", Util.setting("tgl_saldo_awal")).orderBy("wktTerima desc").findList();
        
        //stupid code goes here
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
           boolean isTheDayBefore = dateFormat.parse(Util.toString(filterCutoff)).before(dateFormat.parse(Util.toString(new Date())));
           if(isTheDayBefore){
               SimpleDateFormat changeCutOff = new SimpleDateFormat("yyyy-MM-dd 23:59:00");
               System.out.println(changeCutOff.format(filterCutoff));
               filterCutoff = Timestamp.valueOf(changeCutOff.format(filterCutoff));
               System.out.println(filterCutoff);
               
           }
        
        } catch (ParseException ex) {
            Messagebox.show(ex.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
            return;
        }
        //End stupid 
        
        if (!this.filterJenisKas.equals("SEMUA")) {
            this.listTTSS = Ebean.find(TTSS.class)
                    .where().eq("jenisKas", filterJenisKas)
                    .where().ge("wktTerima", Util.toDate(Util.setting("tgl_saldo_awal")))
                    .orderBy("wktTerima desc").findList();
        }
        if (this.filterCutoff != null) {
            this.listTTSS = Ebean.find(TTSS.class)
                    .where().le("wktTerima", filterCutoff)
                    .where().ge("wktTerima", Util.toDate(Util.setting("tgl_saldo_awal")))
                    .orderBy("wktTerima desc").findList();
        }

        if (!this.filterJenisKas.equals("SEMUA") && this.filterCutoff != null) {
            this.listTTSS = Ebean.find(TTSS.class)
                    .where().eq("jenisKas", filterJenisKas)
                    .where().le("wktTerima", filterCutoff)
                    .where().ge("wktTerima", Util.toDate(Util.setting("tgl_saldo_awal")))
                    .orderBy("wktTerima desc").findList();
        }

        Long nilaiSaldo = Long.valueOf(Util.setting("saldo_awal_transfer")) + Long.valueOf(Util.setting("saldo_awal_dropping"));
        if (filterJenisKas.equals("KAS TRANSFER")) {
            nilaiSaldo = Long.valueOf(Util.setting("saldo_awal_transfer"));
        }

        if (filterJenisKas.equals("KAS DROPPING")) {
            nilaiSaldo = Long.valueOf(Util.setting("saldo_awal_dropping"));

        }

        for (int i = this.listTTSS.size() - 1; i >= 0; i--) {
            if (this.listTTSS.get(i).getWktTerima().after(Util.toDate(Util.setting("tgl_saldo_awal")))) {
                if (this.listTTSS.get(i).getTipe().equals("masuk")) {
                    this.listTTSS.get(i).setSaldo(nilaiSaldo += this.listTTSS.get(i).getNilai());
                } else {
                    this.listTTSS.get(i).setSaldo(nilaiSaldo -= this.listTTSS.get(i).getNilai());
                }
            }

        }

    }

    @Command
    @NotifyChange({"listTTSS", "totalNilai"})
    public void saring() {

    }

    @Command
    @NotifyChange({"listTTSS", "totalNilai"})
    public void saringTgl() {

    }

    @Command
    @NotifyChange({"listTTSS", "totalNilai"})
    public void resetSaringWkt() {

    }

    @Command
    @NotifyChange({"listGiro"})
    public void downloadXLS() {

        File filenya = new File(Util.setting("pdf_path") + "balance-reports.xls");
        try {
            InputStream streamReport = JRLoader.getFileInputStream(Executions.getCurrent().getDesktop().getWebApp().getRealPath("/") + "/report/balance.jasper");
            JRDataSource datasource = new JRBeanCollectionDataSource(listTTSS);
            JRDataSource beanColDataSource = new JRBeanCollectionDataSource(listTTSS);
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
                Ebean.delete((Class) TTSS.class, ttss1.getNomor());
            } catch (Exception e) {
                Logger.getLogger(MenuBalanceVM.class.getName()).log(Level.SEVERE, null, e);
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

    @Command
    public void showCashOpname() {
        refresh();
        
        Map m = new HashMap();
        m.put("jenisKas", filterJenisKas);
        m.put("tglCutoff", filterCutoff);
        m.put("saldoSistem", this.listTTSS.get(0).getSaldo());
        Executions.createComponents("AddNewCashOpname.zul", null, m);
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

    public Timestamp getCutOffDate() {
        return cutOffDate;
    }

    public void setCutOffDate(Timestamp cutOffDate) {
        this.cutOffDate = cutOffDate;
    }

    public Long getSaldo() {
        return saldo;
    }

    public void setSaldo(Long saldo) {
        this.saldo = saldo;
    }

    public Timestamp getFilterCutoff() {
        return filterCutoff;
    }

    public void setFilterCutoff(Timestamp filterCutoff) {
        this.filterCutoff = filterCutoff;
    }

    public String getSaldoAwal() {
        return saldoAwal;
    }

    public void setSaldoAwal(String saldoAwal) {
        this.saldoAwal = saldoAwal;
    }

    public Date getTglSaldoAwal() {
        return tglSaldoAwal;
    }

    public void setTglSaldoAwal(Date tglSaldoAwal) {
        this.tglSaldoAwal = tglSaldoAwal;
    }

    public String getSaldoAwalDropping() {
        return saldoAwalDropping;
    }

    public void setSaldoAwalDropping(String saldoAwalDropping) {
        this.saldoAwalDropping = saldoAwalDropping;
    }

}
