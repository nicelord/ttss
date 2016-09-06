
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.enseval.ttss.model.BalanceDaily;
import com.enseval.ttss.model.TTSS;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.avaje.agentloader.AgentLoader;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author user
 */
public class Tes2 {

    BalanceDaily bd;

    public static void main(String[] args) {
        try {
            AgentLoader.loadAgentFromClasspath("avaje-ebeanorm-agent", "debug=1");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startDate = sdf.parse("2016-08-01 00:00:00");
            Date endDate = sdf.parse("2016-08-31 23:59:59");

            String sql = "SELECT DATE(wkt_terima) a, SUM(nilai) b FROM ttss"
                    + " WHERE wkt_terima >= '2016-08-01 00:00:00'"
                    + " AND wkt_terima <= '2016-08-31 23:59:59'"
                    + " AND jenis_kas = 'KAS TRANSFER'"
                    + " AND tipe = 'masuk'"
                    + " AND tag = 'COLLECTOR'"
                    + " GROUP BY DATE(wkt_terima)"
                    + " ORDER BY DATE(wkt_terima) ASC";

            SqlQuery sqlQuery = Ebean.createSqlQuery(sql);

            List<SqlRow> list = sqlQuery.findList();
            for (SqlRow list1 : list) {
                System.out.println(list1.getString("a") + " | " +list1.getLong("b"));
            }

        } catch (ParseException ex) {
            Logger.getLogger(Tes2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
