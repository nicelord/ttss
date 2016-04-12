
import org.avaje.agentloader.*;
import com.avaje.ebean.*;
import java.util.*;
import com.enseval.ttss.model.*;
import com.enseval.ttss.util.Rupiah;
import com.enseval.ttss.util.Util;
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
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
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
        List<String> badGuys = Arrays.asList("Inky", "Blinky", "Pinky", "Pinky", "Clyde");
Group group = group(badGuys, by(on(String.class).length)));
System.out.println(group.keySet());

        AgentLoader.loadAgentFromClasspath("avaje-ebeanorm-agent", "debug=1");

        Calendar cal = Calendar.getInstance();
        List<TTSS> list = Ebean.find(TTSS.class).where().ge("wktTerima", Util.setting("tgl_saldo_awal")).orderBy("wktTerima desc").findList();

        list.stream().collect(collector)
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
