
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
        AgentLoader.loadAgentFromClasspath("avaje-ebeanorm-agent", "debug=1");
        KasKecil kk = new KasKecil();
        kk.setNomor(new Long(12312));
        kk.setTanggalBuat(new Date());
        kk.setDept("KND");
        kk.setNama("RIZA");
        kk.setNilai(new Long(20000));
        kk.setKeperluan("Mkan");
        kk.setCatatanBuat("hutang");
        Ebean.save(kk);

//        try {
//            AgentLoader.loadAgentFromClasspath("avaje-ebeanorm-agent", "debug=1");
//
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//            Date startDate = sdf.parse("2016-08-01 00:00:00");
//            Date endDate = sdf.parse("2016-09-22 23:59:59");
//
//            java.util.Calendar beginCalendar = java.util.Calendar.getInstance();
//            java.util.Calendar finishCalendar = java.util.Calendar.getInstance();
//
//            beginCalendar.setTime(startDate);
//            finishCalendar.setTime(endDate);
//            finishCalendar.set(Calendar.HOUR_OF_DAY, 23);
//            finishCalendar.set(Calendar.MINUTE, 59);
//            finishCalendar.set(Calendar.SECOND, 59);
//
//            List<BalanceDaily> listBd = new ArrayList<>();
//
//            String sql = "SELECT DATE(wkt_terima), SUM(nilai) FROM ttss"
//                    + " WHERE wkt_terima >= '" + sdf.format(startDate) + " 00:00:00'"
//                    + " AND wkt_terima <= '" + sdf.format(endDate) + " 23:59:59'"
//                    + " AND jenis_kas = 'KAS TRANSFER'"
//                    + " AND tipe = 'masuk'"
//                    + " AND tag = 'COLLECTOR'"
//                    + " GROUP BY DATE(wkt_terima)"
//                    + " ORDER BY DATE(wkt_terima) DESC";
//
//            SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
//            List<SqlRow> list = sqlQuery.findList();
//
//            while (beginCalendar.before(finishCalendar)) {
//                BalanceDaily bd = new BalanceDaily();
//                String current = sdf.format(beginCalendar.getTime());
//                bd.setTanggal(current);
//                for (SqlRow list1 : list) {
//                    if (list1.getString("DATE(wkt_terima)").equals(current)) {
//                        bd.setTotalCollector(list1.getLong("SUM(nilai)"));
//                    }
//                }
//                listBd.add(bd);
//                beginCalendar.add(java.util.Calendar.DATE, 1);
//            }
//
//            for (BalanceDaily listBd1 : listBd) {
//                System.out.println(listBd1.getTanggal() + " | " + listBd1.getTotalCollector());
//
//            }
//
//        } catch (ParseException ex) {
//            Logger.getLogger(TES.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

 


}
