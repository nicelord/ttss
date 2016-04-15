
import org.avaje.agentloader.*;
import com.avaje.ebean.*;
import java.util.*;
import com.enseval.ttss.model.*;
import com.enseval.ttss.util.Util;
import com.enseval.ttss.vm.MenuSetoranVM;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class TES {

    public static void main(final String[] args) {

        AgentLoader.loadAgentFromClasspath("avaje-ebeanorm-agent", "debug=1");

        List<TTSS> lst = Ebean.find(TTSS.class).where().ge("wktTerima", Util.setting("tgl_saldo_awal")).orderBy("wktTerima desc").findList();

        Map<Timestamp, TTSS> byMonth = new HashMap<>();
        for (TTSS lst1 : lst) {
            byMonth.put(lst1.getWktTerima(), lst1);
        }

        Map<Integer, Long> result = new HashMap<>();

        for (Entry<Timestamp, TTSS> entrySet : byMonth.entrySet()) {
            int key = entrySet.getKey().getMonth() + 1;
            long value = entrySet.getValue().getNilai();
            if(entrySet.getValue().getTipe().equals("keluar")){
                value = ~entrySet.getValue().getNilai()+1;
            }
            long oldValue = result.get(key) != null ? result.get(key) : 0;
            result.put(key, oldValue + value);

        }

        for (Entry<Integer, Long> entrySet : result.entrySet()) {
            System.out.println("Month " + entrySet.getKey() + "- Value = " + entrySet.getValue());

        }
    }

}
