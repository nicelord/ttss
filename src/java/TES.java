
import org.avaje.agentloader.*;
import com.avaje.ebean.*;
import java.util.*;
import com.enseval.ttss.model.*;
import java.sql.Timestamp;
import java.text.*;

public class TES {

    public static void main(final String[] args) {
        AgentLoader.loadAgentFromClasspath("avaje-ebeanorm-agent", "debug=1");
        
        Setting set = new Setting();
        set.setId(new Long(1));
        set.setSaldoAwal(new Long(10000));
        set.setTanggalSaldoAwal(new Date());
        Ebean.save(set);

   
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
}
