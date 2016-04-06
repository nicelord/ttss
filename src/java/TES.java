
import org.avaje.agentloader.*;
import com.avaje.ebean.*;
import java.util.*;
import com.enseval.ttss.model.*;
import com.enseval.ttss.util.Rupiah;
import com.enseval.ttss.vm.MenuSetoranVM;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.*;
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
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;

public class TES {

    public static void main(final String[] args) {
        AgentLoader.loadAgentFromClasspath("avaje-ebeanorm-agent", "debug=1");
        System.out.println(Rupiah.convert(2147483647));
  //      new TES().downloadXLS();

//        Setting set = new Setting();
//        set.setId(new Long(1));
//        set.setSaldoAwal(new Long(10000));
//        set.setTanggalSaldoAwal(new Date());
//        Ebean.save(set);
//
//        User usr = Ebean.find(User.class, 1);
//
//        Giro gr1 = new Giro();
//        gr1.setNomor(Long.parseLong(gr1.getLastNomor()) + 1L);
//        gr1.setUserLogin(usr);
//        gr1.setDKLK("LK");
//        gr1.setStatus("OK");
//        gr1.setNamaPenyetor("ADADEH");
//        gr1.setKeterangan("KETERANGAN");
//        gr1.setNilai(new Long(12345));
//        gr1.setTag("BNI");
//        gr1.setWktTerima(new Timestamp(new Date().getTime()));
//        gr1.setTglKliring(new Date());
//        gr1.setTglJtTempo(new Date());
//        Ebean.save(gr1);
    }

    public void downloadXLS() {

        List<TTSS> listTTSS = Ebean.find(TTSS.class).setMaxRows(5).order("nomor desc").findList();
        final File filenya = new File("ttss-reports.xls");
        try {
            InputStream streamReport = JRLoader.getFileInputStream("web/report/report1.jasper");
            JRDataSource datasource = new JRBeanCollectionDataSource(listTTSS);
            Map map = new HashMap();
            map.put("REPORT_DATA_SOURCE", datasource);
            map.put("TOTAL", new Long(12312));
            JasperPrint report = JasperFillManager.fillReport(streamReport, map);
            OutputStream outputStream = new FileOutputStream(filenya);
            JRXlsExporter exporterXLS = new JRXlsExporter();
            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, (Object) report);
            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, (Object) outputStream);
            exporterXLS.setParameter((JRExporterParameter) JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, (Object) Boolean.FALSE);
            exporterXLS.setParameter((JRExporterParameter) JRXlsExporterParameter.IS_DETECT_CELL_TYPE, (Object) Boolean.TRUE);
            exporterXLS.setParameter((JRExporterParameter) JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, (Object) Boolean.FALSE);
            exporterXLS.setParameter((JRExporterParameter) JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, (Object) Boolean.TRUE);
            exporterXLS.setParameter((JRExporterParameter) JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, (Object) Boolean.TRUE);
            exporterXLS.exportReport();
            streamReport.close();
            outputStream.close();
        } catch (JRException | FileNotFoundException ex4) {
            Logger.getLogger(MenuSetoranVM.class.getName()).log(Level.SEVERE, null, ex4);
        } catch (IOException ex2) {
            Logger.getLogger(MenuSetoranVM.class.getName()).log(Level.SEVERE, null, ex2);
        }
        
    }

}
