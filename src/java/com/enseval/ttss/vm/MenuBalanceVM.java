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

    List<TTSS> listTTSS = new ArrayList<>();
    TTSS ttss;
    Long totalNilai = 0L;
    String filterJenisKas = "SEMUA";
    Timestamp cutOffDate;
    List<Cetak> cetaks = new ArrayList<>();
    User userLogin;
    Long saldo = 0L;
    String filterBulan = "";

    String saldoAwal = Util.setting("saldo_awal_transfer");
    String saldoAwalDropping = Util.setting("saldo_awal_dropping");
    Date tglSaldoAwal = Util.toDate(Util.setting("tgl_saldo_awal"));

    List<Balance> listBalance = new ArrayList<>();

    List<String> listBulan = new ArrayList<>();

    @AfterCompose
    public void initSetup() {

        this.userLogin = Ebean.find(User.class, new AuthenticationServiceImpl().getUserCredential().getUser().getId());
        this.listTTSS = Ebean.find(TTSS.class).where().ge("wktTerima", Util.setting("tgl_saldo_awal")).orderBy("wktTerima desc").findList();
        showAllBalance();
    }

    @Command
    @NotifyChange({"listBalance"})
    public void showAllBalance() {
        listBalance = new ArrayList<>();
        if (listTTSS.isEmpty()) {
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
        Date startDate = Util.toDate(Util.setting("tgl_saldo_awal"));

        java.util.Calendar beginCalendar = java.util.Calendar.getInstance();
        java.util.Calendar finishCalendar = java.util.Calendar.getInstance();

        beginCalendar.setTime(startDate);
        finishCalendar.setTime(new Date());
        finishCalendar.add(java.util.Calendar.MONTH, 1);

        while (beginCalendar.before(finishCalendar)) {
            String date = sdf.format(beginCalendar.getTime()).toUpperCase();

            listBulan.add(date);

            //balance transfer
            this.filterJenisKas = "KAS TRANSFER";
            refresh();
            Balance b = new Balance();
            b.setPeriode(date);
            List<TTSS> monthlyTTSS = getFilterOutput(listTTSS, date);

            if (!monthlyTTSS.isEmpty()) {
                for (TTSS t : monthlyTTSS) {
                    if (t.getTipe().equals("keluar")) {
                        b.setTotalKeluar(b.getTotalKeluar() + t.getNilai());
                    } else {
                        b.setTotalMasuk(b.getTotalMasuk() + t.getNilai());
                    }
                }

                if (monthlyTTSS.get(monthlyTTSS.size() - 1).getTipe().equals("keluar")) {
                    b.setSaldoAwal(monthlyTTSS.get(monthlyTTSS.size() - 1).getSaldo() + monthlyTTSS.get(monthlyTTSS.size() - 1).getNilai());
                } else {
                    b.setSaldoAwal(monthlyTTSS.get(monthlyTTSS.size() - 1).getSaldo() - monthlyTTSS.get(monthlyTTSS.size() - 1).getNilai());
                }
                b.setSaldoAkhir(monthlyTTSS.get(0).getSaldo());
                b.setJenisKas("KAS TRANSFER");
                listBalance.add(b);
            }

            //balance dropping
            this.filterJenisKas = "KAS DROPPING";
            refresh();
            b = new Balance();
            b.setPeriode(date);
            monthlyTTSS = getFilterOutput(listTTSS, date);

            if (!monthlyTTSS.isEmpty()) {
                for (TTSS t : monthlyTTSS) {
                    if (t.getTipe().equals("keluar")) {
                        b.setTotalKeluar(b.getTotalKeluar() + t.getNilai());
                    } else {
                        b.setTotalMasuk(b.getTotalMasuk() + t.getNilai());
                    }
                }

                if (monthlyTTSS.get(monthlyTTSS.size() - 1).getTipe().equals("keluar")) {
                    b.setSaldoAwal(monthlyTTSS.get(monthlyTTSS.size() - 1).getSaldo() + monthlyTTSS.get(monthlyTTSS.size() - 1).getNilai());
                } else {
                    b.setSaldoAwal(monthlyTTSS.get(monthlyTTSS.size() - 1).getSaldo() - monthlyTTSS.get(monthlyTTSS.size() - 1).getNilai());
                }
                b.setSaldoAkhir(monthlyTTSS.get(0).getSaldo());
                b.setJenisKas("KAS DROPPING");
                listBalance.add(b);
            }
            beginCalendar.add(java.util.Calendar.MONTH, 1);
        }

        Collections.reverse(listBalance);        
        
        if(beginCalendar.get(java.util.Calendar.MONTH)>finishCalendar.get(java.util.Calendar.MONTH)){
            listBulan.remove(listBulan.get(listBulan.size()-1));
        }

    }

    @Command
    @NotifyChange({"listBalance"})
    public void filterDataBalance() {
        listBalance = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
        Date startDate = Util.toDate(Util.setting("tgl_saldo_awal"));

        java.util.Calendar beginCalendar = java.util.Calendar.getInstance();
        java.util.Calendar finishCalendar = java.util.Calendar.getInstance();

        beginCalendar.setTime(startDate);
        finishCalendar.add(java.util.Calendar.MONTH, 1);

        while (beginCalendar.before(finishCalendar)) {
            String date = sdf.format(beginCalendar.getTime()).toUpperCase();

            refresh();
            Balance b = new Balance();
            b.setPeriode(date);
            List<TTSS> monthlyTTSS = getFilterOutput(listTTSS, date);
            if (!monthlyTTSS.isEmpty()) {

                for (TTSS t : monthlyTTSS) {
                    if (t.getTipe().equals("keluar")) {
                        b.setTotalKeluar(b.getTotalKeluar() + t.getNilai());
                    } else {
                        b.setTotalMasuk(b.getTotalMasuk() + t.getNilai());
                    }
                }

                // b.setSaldoAwal(monthlyTTSS.get(monthlyTTSS.size() - 1).getSaldo() - monthlyTTSS.get(monthlyTTSS.size() - 1).getNilai());
                if (monthlyTTSS.get(monthlyTTSS.size() - 1).getTipe().equals("keluar")) {
                    b.setSaldoAwal(monthlyTTSS.get(monthlyTTSS.size() - 1).getSaldo() + monthlyTTSS.get(monthlyTTSS.size() - 1).getNilai());
                } else {
                    b.setSaldoAwal(monthlyTTSS.get(monthlyTTSS.size() - 1).getSaldo() - monthlyTTSS.get(monthlyTTSS.size() - 1).getNilai());
                }
                b.setSaldoAkhir(monthlyTTSS.get(0).getSaldo());
                b.setJenisKas(filterJenisKas);
                if (!filterBulan.isEmpty()) {
                    if (b.getPeriode().equals(filterBulan)) {
                        listBalance.add(b);
                    }
                } else {
                    listBalance.add(b);
                }

            }

            beginCalendar.add(java.util.Calendar.MONTH, 1);
        }

        Collections.reverse(listBalance);

    }

    private static List<TTSS> getFilterOutput(List<TTSS> lines, String month) {
        List<TTSS> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
        for (TTSS line : lines) {
            if (sdf.format(line.getWktTerima()).equals(month)) {
                result.add(line);
            }
        }
        return result;
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
    @NotifyChange("*")
    public void refresh() {
        this.listTTSS = Ebean.find(TTSS.class).where().ge("wktTerima", Util.setting("tgl_saldo_awal")).orderBy("wktTerima desc").findList();

        if (!this.filterJenisKas.equals("SEMUA")) {
            this.listTTSS = Ebean.find(TTSS.class)
                    .where().eq("jenisKas", filterJenisKas)
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
    @NotifyChange({"listBalance"})
    public void downloadXLS() {

        File filenya = new File(Util.setting("pdf_path") + "balance-reports.xls");
        try {
            InputStream streamReport = JRLoader.getFileInputStream(Executions.getCurrent().getDesktop().getWebApp().getRealPath("/") + "/report/balance.jasper");
            JRDataSource datasource = new JRBeanCollectionDataSource(listBalance);
            JRDataSource beanColDataSource = new JRBeanCollectionDataSource(listBalance);
            Map map = new HashMap();
            map.put("REPORT_DATA_SOURCE", datasource);
            map.put("BALANCE", beanColDataSource);
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

    public String getFilterJenisKas() {
        return this.filterJenisKas;
    }

    public void setFilterJenisKas(final String filterJenisKas) {
        this.filterJenisKas = filterJenisKas;
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

    public List<Balance> getListBalance() {
        return listBalance;
    }

    public void setListBalance(List<Balance> listBalance) {
        this.listBalance = listBalance;
    }

    public List<String> getListBulan() {
        return listBulan;
    }

    public void setListBulan(List<String> listBulan) {
        this.listBulan = listBulan;
    }

    public String getFilterBulan() {
        return filterBulan;
    }

    public void setFilterBulan(String filterBulan) {
        this.filterBulan = filterBulan;
    }

}
