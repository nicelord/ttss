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

public class MenuBalanceDailyVM {

    Date startDate, endDate;

    List<BalanceDaily> listBalanceDaily = new ArrayList<>();
    long totalListCollector = 0L;
    long totalListSalesman = 0L;
    long totalListExpedisi = 0L;
    long totalListLainnyaIn = 0L;
    long totalListAllIn = 0L;

    long totalListBank = 0L;
    long totalListCn = 0L;
    long totalListLainnyaOut = 0L;
    long totalListAllOut = 0L;

    @AfterCompose
    @NotifyChange({"listBalanceDaily"})
    public void initSetup() {

        refreshDailyReport();

    }

    private List<SqlRow> getDailyBalanceBy(String tipe, String tag, Date startDate, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "SELECT DATE(wkt_terima), SUM(nilai) FROM ttss"
                + " WHERE wkt_terima >= '" + sdf.format(startDate) + " 00:00:00'"
                + " AND wkt_terima <= '" + sdf.format(endDate) + " 23:59:59'"
                + " AND jenis_kas = 'KAS TRANSFER'"
                + " AND tipe = '" + tipe + "'"
                + " AND tag = '" + tag + "'"
                + " GROUP BY DATE(wkt_terima)"
                + " ORDER BY DATE(wkt_terima) DESC";
        SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
        List<SqlRow> list = sqlQuery.findList();
        return list;

    }

    @Command
    public String format(final long nilai) {
        final DecimalFormat kursIndonesia = (DecimalFormat) NumberFormat.getCurrencyInstance();
        final DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setMinimumFractionDigits(0);
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia.format(nilai);
    }

    @Command
    @NotifyChange({"listBalance"})
    public void downloadXLS() {

        File filenya = new File(Util.setting("pdf_path") + "balance-reports.xls");
        try {
            InputStream streamReport = JRLoader.getFileInputStream(Executions.getCurrent().getDesktop().getWebApp().getRealPath("/") + "/report/balance.jasper");
            JRDataSource datasource = new JRBeanCollectionDataSource(listBalanceDaily);
            JRDataSource beanColDataSource = new JRBeanCollectionDataSource(listBalanceDaily);
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
        }
        filenya.delete();

    }

    @Command
    @NotifyChange("*")
    public void refreshDailyReport() {

        try {
            listBalanceDaily = new ArrayList<>();
            totalListCollector = 0L;
            totalListSalesman = 0L;
            totalListExpedisi = 0L;
            totalListLainnyaIn = 0L;
            totalListAllIn = 0L;

            totalListBank = 0L;
            totalListCn = 0L;
            totalListLainnyaOut = 0L;
            totalListAllOut = 0L;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            if (startDate == null && endDate == null) {
                java.util.Calendar firstOfMonth = java.util.Calendar.getInstance();
                firstOfMonth.set(java.util.Calendar.DAY_OF_MONTH, 1);

                startDate = sdf.parse(sdf.format(firstOfMonth.getTime()) + " 00:00:00");
                endDate = sdf.parse(sdf.format(new Date()) + " 23:59:59");
            }

            java.util.Calendar beginCalendar = java.util.Calendar.getInstance();
            java.util.Calendar finishCalendar = java.util.Calendar.getInstance();
            beginCalendar.setTime(startDate);
            finishCalendar.setTime(endDate);

            finishCalendar.set(java.util.Calendar.HOUR_OF_DAY, 23);
            finishCalendar.set(java.util.Calendar.MINUTE, 59);
            finishCalendar.set(java.util.Calendar.SECOND, 59);

            while (beginCalendar.before(finishCalendar)) {
                BalanceDaily bd = new BalanceDaily();
                String current = sdf.format(beginCalendar.getTime());
                bd.setTanggal(current);

                for (SqlRow list1 : getDailyBalanceBy("masuk", "COLLECTOR", startDate, endDate)) {
                    if (list1.getString("DATE(wkt_terima)").equals(current)) {
                        bd.setTotalCollector(list1.getLong("SUM(nilai)"));
                    }
                }

                for (SqlRow list1 : getDailyBalanceBy("masuk", "SALESMAN", startDate, endDate)) {
                    if (list1.getString("DATE(wkt_terima)").equals(current)) {
                        bd.setTotalSalesman(list1.getLong("SUM(nilai)"));
                    }
                }

                for (SqlRow list1 : getDailyBalanceBy("masuk", "EXPEDISI", startDate, endDate)) {
                    if (list1.getString("DATE(wkt_terima)").equals(current)) {
                        bd.setTotalExpedisi(list1.getLong("SUM(nilai)"));
                    }
                }

                for (SqlRow list1 : getDailyBalanceBy("masuk", "LAIN-LAIN", startDate, endDate)) {
                    if (list1.getString("DATE(wkt_terima)").equals(current)) {
                        bd.setTotalLainnya(list1.getLong("SUM(nilai)"));
                    }
                }

                for (SqlRow list1 : getDailyBalanceBy("keluar", "BANK/PICKUP", startDate, endDate)) {
                    if (list1.getString("DATE(wkt_terima)").equals(current)) {
                        bd.setTotalBank(list1.getLong("SUM(nilai)"));
                    }
                }

                for (SqlRow list1 : getDailyBalanceBy("keluar", "CN OUTLET", startDate, endDate)) {
                    if (list1.getString("DATE(wkt_terima)").equals(current)) {
                        bd.setTotalCNOutlet(list1.getLong("SUM(nilai)"));
                    }
                }

                for (SqlRow list1 : getDailyBalanceBy("keluar", "LAIN-LAIN", startDate, endDate)) {
                    if (list1.getString("DATE(wkt_terima)").equals(current)) {
                        bd.setTotalLainnyaOut(list1.getLong("SUM(nilai)"));
                    }
                }

                listBalanceDaily.add(bd);
                beginCalendar.add(java.util.Calendar.DATE, 1);
            }

        } catch (ParseException ex) {
            Logger.getLogger(MenuBalanceDailyVM.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (BalanceDaily listBalanceDaily1 : listBalanceDaily) {
            totalListCollector += listBalanceDaily1.getTotalCollector();
            totalListSalesman += listBalanceDaily1.getTotalSalesman();
            totalListExpedisi += listBalanceDaily1.getTotalExpedisi();
            totalListLainnyaIn += listBalanceDaily1.getTotalLainnya();
            totalListAllIn += listBalanceDaily1.getTotalHarianIn();

            totalListBank += listBalanceDaily1.getTotalBank();
            totalListCn += listBalanceDaily1.getTotalCNOutlet();
            totalListLainnyaOut += listBalanceDaily1.getTotalLainnyaOut();
            totalListAllOut += listBalanceDaily1.getTotalHarianOut();
        }

    }
    
    @Command
    @NotifyChange("*")
    public void resetDailyReport(){
        startDate = null;
        endDate = null;
        refreshDailyReport();
    }

    public List<BalanceDaily> getListBalanceDaily() {
        return listBalanceDaily;
    }

    public void setListBalanceDaily(List<BalanceDaily> listBalanceDaily) {
        this.listBalanceDaily = listBalanceDaily;
    }

    public long getTotalListCollector() {
        return totalListCollector;
    }

    public void setTotalListCollector(long totalListCollector) {
        this.totalListCollector = totalListCollector;
    }

    public long getTotalListSalesman() {
        return totalListSalesman;
    }

    public void setTotalListSalesman(long totalListSalesman) {
        this.totalListSalesman = totalListSalesman;
    }

    public long getTotalListExpedisi() {
        return totalListExpedisi;
    }

    public void setTotalListExpedisi(long totalListExpedisi) {
        this.totalListExpedisi = totalListExpedisi;
    }

    public long getTotalListLainnyaIn() {
        return totalListLainnyaIn;
    }

    public void setTotalListLainnyaIn(long totalListLainnyaIn) {
        this.totalListLainnyaIn = totalListLainnyaIn;
    }

    public long getTotalListAllIn() {
        return totalListAllIn;
    }

    public void setTotalListAllIn(long totalListAllIn) {
        this.totalListAllIn = totalListAllIn;
    }

    public long getTotalListBank() {
        return totalListBank;
    }

    public void setTotalListBank(long totalListBank) {
        this.totalListBank = totalListBank;
    }

    public long getTotalListCn() {
        return totalListCn;
    }

    public void setTotalListCn(long totalListCn) {
        this.totalListCn = totalListCn;
    }

    public long getTotalListLainnyaOut() {
        return totalListLainnyaOut;
    }

    public void setTotalListLainnyaOut(long totalListLainnyaOut) {
        this.totalListLainnyaOut = totalListLainnyaOut;
    }

    public long getTotalListAllOut() {
        return totalListAllOut;
    }

    public void setTotalListAllOut(long totalListAllOut) {
        this.totalListAllOut = totalListAllOut;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
