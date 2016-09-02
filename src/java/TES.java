
import org.avaje.agentloader.*;
import com.avaje.ebean.*;
import java.util.*;
import com.enseval.ttss.model.*;
import com.enseval.ttss.util.Util;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TES {

    public static void main(final String[] args) {

        try {
            AgentLoader.loadAgentFromClasspath("avaje-ebeanorm-agent", "debug=1");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date startDate = sdf.parse("2016-08-01 00:00:00");
            Date endDate = sdf.parse("2016-08-31 23:59:59");

//            List<TTSS> list = Ebean.find(TTSS.class).where()
//                    .ge("wktTerima", sdf.format(startDate))
//                    .le("wktTerima", sdf.format(endDate))
//                    .orderBy("wktTerima desc").findList();
            java.util.Calendar beginCalendar = java.util.Calendar.getInstance();
            java.util.Calendar finishCalendar = java.util.Calendar.getInstance();

            beginCalendar.setTime(startDate);
            finishCalendar.setTime(endDate);
            finishCalendar.set(Calendar.HOUR_OF_DAY, 23);
            finishCalendar.set(Calendar.MINUTE, 59);
            finishCalendar.set(Calendar.SECOND, 59);

            List<BalanceDaily> listBd = new ArrayList<>();

            while (beginCalendar.before(finishCalendar)) {
                String hariAkhir = sdf.format(beginCalendar.getTime()).replace("00:00:00", "23:59:59");
           
                List<TTSS> dailyTTSS = Ebean.find(TTSS.class).where()
                        .ge("wktTerima", sdf.parse(sdf.format(beginCalendar.getTime())))
                        .le("wktTerima", sdf.parse(hariAkhir))
                        .orderBy("wktTerima desc").findList();
                
                List<TTSS> dailyTtssCol = Ebean.filter(TTSS.class)
                        .sort("wktTerima desc")
                        .eq("tag", "EXP")
                        .filter(dailyTTSS);
                
                for (TTSS dailyTTSS1 : dailyTtssCol) {
                    System.out.println(sdf.format(dailyTTSS1.getWktTerima()) + " " + dailyTTSS1.getNilai());
                }
                
                
                beginCalendar.add(java.util.Calendar.DATE, 1);
            }

        } catch (ParseException ex) {
            Logger.getLogger(TES.class.getName()).log(Level.SEVERE, null, ex);
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
