
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAccessor;
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

        Long nilaiSaldo = Long.valueOf(Util.setting("saldo_awal_transfer")) + Long.valueOf(Util.setting("saldo_awal_dropping"));

        for (int i = lst.size() - 1; i >= 0; i--) {
            if (lst.get(i).getWktTerima().after(Util.toDate(Util.setting("tgl_saldo_awal")))) {
                if (lst.get(i).getTipe().equals("masuk")) {
                    lst.get(i).setSaldo(nilaiSaldo += lst.get(i).getNilai());
                } else {
                    lst.get(i).setSaldo(nilaiSaldo -= lst.get(i).getNilai());
                }
            }

        }

        List<Balance> bl = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");

        Calendar startCal = Calendar.getInstance();
        startCal.set(Calendar.DATE, Integer.parseInt(Util.setting("tgl_saldo_awal").split("-")[2]));
        startCal.set(Calendar.MONTH, Integer.parseInt(Util.setting("tgl_saldo_awal").split("-")[1]) - 1);
        startCal.set(Calendar.YEAR, Integer.parseInt(Util.setting("tgl_saldo_awal").split("-")[0]));
        Date startDate = startCal.getTime();

        Calendar endCal = Calendar.getInstance();
        Date endDate = endCal.getTime();

        Calendar beginCalendar = Calendar.getInstance();
        Calendar finishCalendar = Calendar.getInstance();

        beginCalendar.setTime(startDate);
        finishCalendar.setTime(lst.get(0).getWktTerima());


        while (beginCalendar.before(finishCalendar)) {
            // add one month to date per loop
            String date = sdf.format(beginCalendar.getTime()).toUpperCase();
            System.out.println(getFilterOutput(lst, date).size());
            Balance b = new Balance();
            b.setPeriode(date);
            for (TTSS t : getFilterOutput(lst, date)) {
                if (t.getTipe().equals("keluar")) {
                    b.setTotalKeluar(b.getTotalKeluar() + t.getNilai());
                } else {
                    b.setTotalMasuk(b.getTotalMasuk() + t.getNilai());
                }
            }
            bl.add(b);

            beginCalendar.add(Calendar.MONTH, 1);
        }

        for (Balance balance : bl) {
            System.out.println(balance.getPeriode());
            System.out.println(balance.getTotalKeluar());
            System.out.println(balance.getTotalMasuk());
        }

    }

    private static List<TTSS> addtobalance(List<TTSS> lines, String startDate) {
        List<TTSS> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
        for (TTSS line : lines) {
            if (sdf.format(line.getWktTerima()).equals(startDate)) {
                result.add(line);
            }
        }
        return result;
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

}
