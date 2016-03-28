package com.enseval.ttss.vm;

import com.enseval.ttss.model.*;
import com.avaje.ebean.*;
import java.text.*;
import com.enseval.ttss.util.*;
import org.zkoss.zk.ui.*;
import org.zkoss.bind.annotation.*;
import java.util.*;
import java.util.logging.*;
import org.zkoss.zul.*;
import java.sql.Timestamp;

public class MenuGiroPendingVM {

    List<Giro> listGiro;
    Giro giro;
    Long totalNilai;
    String filterNomor;
    String filterNomorGiro;
    String filterBank;
    String filterNilai;
    String filterPenyetor;
    String filterTag;
    String filterDKLK;
    String filterStatus;
    Timestamp tsAwal;
    Timestamp tsAkhir;
    User userLogin;
    Date tglJtTempoAwal;
    Date tglJtTempoAkhir;
    List<Giro> selectedGiro;

    public MenuGiroPendingVM() {
        this.listGiro = new ArrayList<>();
        this.totalNilai = 0L;
        this.filterNomor = "";
        this.filterNomorGiro = "";
        this.filterBank = "";
        this.filterNilai = "";
        this.filterPenyetor = "";
        this.filterTag = "";
        this.filterDKLK = "";
        this.filterStatus = "";
        this.selectedGiro = new ArrayList<>();

    }

    @AfterCompose
    public void initSetup(@ContextParam(ContextType.VIEW) Component view) {

        this.userLogin = Ebean.find(User.class, new AuthenticationServiceImpl().getUserCredential().getUser().getId());
        this.listGiro = Ebean.find(Giro.class).where().eq("tglKliring", null).setMaxRows(50).orderBy("nomor desc").findList();

        Long nilai = 0L;
        for (Giro giro1 : this.listGiro) {
            nilai += giro1.getNilai();
        }
        this.totalNilai = nilai;

    }

    @Command
    @NotifyChange({"giro", "totalNilai"})
    public void showDetail(@BindingParam("giro") Giro giro) {
        this.giro = giro;
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
    public void buatGiroBaru() {
        Executions.createComponents("AddNewGiro.zul", (Component) null, (Map) null);
    }

    @Command
    @NotifyChange({"giro"})
    public void liatRevisi() {
        if (this.giro == null) {
            Messagebox.show("Giro belum dipilih!", "ERROR", 1, "z-messagebox-icon z-messagebox-error");
        } else {
            final Map args = new HashMap();
            args.put("giro", this.giro);
            Executions.createComponents("DaftarRevisiGiro.zul", (Component) null, args);
        }
    }

    @GlobalCommand
    @NotifyChange({"listGiro", "totalNilai"})
    public void refresh() {
        if (this.tsAwal != null | this.tsAkhir != null) {
            
            this.listGiro = Ebean.find(Giro.class)
                    .where().eq("tglKliring", null)
                    .where().like("nomor", "%" + this.filterNomor + "%")
                    .where().like("nomorGiro", "%" + this.filterNomorGiro + "%")
                    .where().like("bank", "%" + this.filterBank + "%")
                    .where().like("namaPenyetor", "%" + this.filterPenyetor + "%")
                    .where().like("tag", "%" + this.filterTag + "%")
                    .where().like("DKLK", "%" + this.filterDKLK + "%")
                    .where().like("status", "%" + this.filterStatus + "%")
                    .where().ge("wktTerima", this.tsAwal).where().le("wktTerima", this.tsAkhir)
                    .orderBy("nomor DESC")
                    .findList();
        } else {

            this.listGiro = Ebean.find(Giro.class)
                    .where().eq("tglKliring", null)
                    .where().like("nomor", "%" + this.filterNomor + "%")
                    .where().like("nomorGiro", "%" + this.filterNomorGiro + "%")
                    .where().like("bank", "%" + this.filterBank + "%")
                    .where().like("namaPenyetor", "%" + this.filterPenyetor + "%")
                    .where().like("tag", "%" + this.filterTag + "%")
                    .where().like("DKLK", "%" + this.filterDKLK + "%")
                    .where().like("status", "%" + this.filterStatus + "%")
                    .orderBy("nomor DESC")
                    .findList();

        }

        Long nilai = 0L;
        for (final Giro giro1 : this.listGiro) {
            nilai += giro1.getNilai();
        }
        this.totalNilai = nilai;
    }

    @Command
    @NotifyChange({"listGiro", "totalNilai"})
    public void saring() {
        refresh();
    }

    @Command
    @NotifyChange({"listGiro", "totalNilai"})
    public void saringTgl() {
        refresh();
    }

    @Command
    @NotifyChange({"listGiro", "totalNilai"})
    public void resetSaringWkt() {
        this.tsAwal = null;
        this.tsAkhir = null;
        refresh();
    }

    @Command
    @NotifyChange({"listGiro"})
    public void downloadXLS() {
//        final File filenya = new File("D://giro-reports.xls");
//        try {
//            final InputStream streamReport = JRLoader.getFileInputStream(Executions.getCurrent().getDesktop().getWebApp().getRealPath("/") + "/report/giro.jasper");
//            final JRDataSource datasource = (JRDataSource) new JRBeanCollectionDataSource((Collection) this.listGiro);
//            final Map map = new HashMap();
//            map.put("REPORT_DATA_SOURCE", datasource);
//            map.put("TOTAL", this.totalNilai);
//            final JasperPrint report = JasperFillManager.fillReport(streamReport, map);
//            final OutputStream outputStream = new FileOutputStream(filenya);
//            final JRXlsExporter exporterXLS = new JRXlsExporter();
//            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, (Object) report);
//            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, (Object) outputStream);
//            exporterXLS.setParameter((JRExporterParameter) JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, (Object) Boolean.FALSE);
//            exporterXLS.setParameter((JRExporterParameter) JRXlsExporterParameter.IS_DETECT_CELL_TYPE, (Object) Boolean.TRUE);
//            exporterXLS.setParameter((JRExporterParameter) JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, (Object) Boolean.FALSE);
//            exporterXLS.setParameter((JRExporterParameter) JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, (Object) Boolean.TRUE);
//            exporterXLS.setParameter((JRExporterParameter) JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, (Object) Boolean.TRUE);
//            exporterXLS.exportReport();
//            streamReport.close();
//            outputStream.close();
//        } catch (JRException | FileNotFoundException ex4) {
//            Logger.getLogger(MenuSetoranBGVM.class.getName()).log(Level.SEVERE, null, ex4);
//        } catch (IOException ex2) {
//            Logger.getLogger(MenuSetoranBGVM.class.getName()).log(Level.SEVERE, null, ex2);
//        }
//        FileInputStream inputStream = null;
//        try {
//            if (filenya.exists()) {
//                inputStream = new FileInputStream(filenya);
//                Filedownload.save((InputStream) inputStream, new MimetypesFileTypeMap().getContentType(filenya), filenya.getName());
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        filenya.delete();

        for (Giro giroSelected : selectedGiro) {

            giroSelected.setTglKliring(new Date());
            Ebean.save(giroSelected);
            refresh();

        }

    }

    @Command
    @NotifyChange({"listGiro", "totalNilai"})
    public void hapusSelectedGiro() {
        for (final Giro giro1 : this.selectedGiro) {
            try {
                //Ebean.delete((Class) Cetak.class, (Collection) Ebean.find((Class) Cetak.class).where("ttssnya.nomor = '" + ttss1.getNomor() + "'").findIds());
                Ebean.delete(Giro.class, giro.getNomor());
            } catch (Exception e) {
                Logger.getLogger(MenuGiroPendingVM.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        this.refresh();
    }

    @Command
    @NotifyChange({"listGiro", "totalNilai"})
    public void filterDK() {
        this.filterDKLK = "DK";
        this.refresh();
    }

    @Command
    @NotifyChange({"listGiro", "totalNilai"})
    public void filterLK() {
        this.filterDKLK = "LK";
        this.refresh();
    }

    @Command
    @NotifyChange({"listGiro", "totalNilai"})
    public void resetFilterDKLK() {
        this.filterDKLK = "";
        this.refresh();
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

    public User getUserLogin() {
        return this.userLogin;
    }

    public void setUserLogin(final User userLogin) {
        this.userLogin = userLogin;
    }

    public String getFilterTag() {
        return this.filterTag;
    }

    public void setFilterTag(final String filterTag) {
        this.filterTag = filterTag;
    }

    public List<Giro> getListGiro() {
        return listGiro;
    }

    public void setListGiro(List<Giro> listGiro) {
        this.listGiro = listGiro;
    }

    public Giro getGiro() {
        return giro;
    }

    public void setGiro(Giro giro) {
        this.giro = giro;
    }

    public String getFilterDKLK() {
        return filterDKLK;
    }

    public void setFilterDKLK(String filterDKLK) {
        this.filterDKLK = filterDKLK;
    }

    public String getFilterStatus() {
        return filterStatus;
    }

    public void setFilterStatus(String filterStatus) {
        this.filterStatus = filterStatus;
    }

    public List<Giro> getSelectedGiro() {
        return selectedGiro;
    }

    public void setSelectedGiro(List<Giro> selectedGiro) {
        this.selectedGiro = selectedGiro;
    }

    public Date getTglJtTempoAwal() {
        return tglJtTempoAwal;
    }

    public void setTglJtTempoAwal(Date tglJtTempoAwal) {
        this.tglJtTempoAwal = tglJtTempoAwal;
    }

    public Date getTglJtTempoAkhir() {
        return tglJtTempoAkhir;
    }

    public void setTglJtTempoAkhir(Date tglJtTempoAkhir) {
        this.tglJtTempoAkhir = tglJtTempoAkhir;
    }

    public String getFilterNomorGiro() {
        return filterNomorGiro;
    }

    public void setFilterNomorGiro(String filterNomorGiro) {
        this.filterNomorGiro = filterNomorGiro;
    }

    public String getFilterBank() {
        return filterBank;
    }

    public void setFilterBank(String filterBank) {
        this.filterBank = filterBank;
    }

}
