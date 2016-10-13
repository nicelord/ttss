
import com.avaje.ebean.Ebean;
import com.enseval.ttss.model.Customer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.OptimisticLockException;
import org.apache.commons.codec.Charsets;
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
public class TestReadCustData {

    public static void main(String[] args) {
        AgentLoader.loadAgentFromClasspath("avaje-ebeanorm-agent", "debug=1");
        List<Customer> lCust = new ArrayList<>();
        try {
            List<String> s = Files.readAllLines(Paths.get("customer.txt"), Charsets.ISO_8859_1);

            for (String s1 : s) {
                if (!s1.startsWith("GRUP")) {
                    Customer c = new Customer();
                    try{
                        c.setId(Long.parseLong(s1.split("~")[3]));
                    }catch(ArrayIndexOutOfBoundsException arrex ){
                        c.setId(Long.parseLong(s1.split("~")[3]));
                    }
                    try{
                        c.setNama(s1.split("~")[4]);
                    }catch(ArrayIndexOutOfBoundsException arrex ){
                        c.setNama("");
                    }
                    try{
                        c.setShipto(s1.split("~")[9]);
                    }catch(ArrayIndexOutOfBoundsException arrex ){
                        c.setShipto("");
                    }
                    try{
                        c.setKota(s1.split("~")[12]);
                    }catch(ArrayIndexOutOfBoundsException arrex ){
                        c.setKota("");
                    }
                    try{
                        c.setProvinsi(s1.split("~")[13]);
                    }catch(ArrayIndexOutOfBoundsException arrex ){
                        c.setProvinsi("");
                    }
                    try{
                        c.setKodePos(s1.split("~")[14]);
                    }catch(ArrayIndexOutOfBoundsException arrex ){
                        c.setKodePos("");
                    }
                    try{
                        c.setNamaArea(s1.split("~")[2]);
                    }catch(ArrayIndexOutOfBoundsException arrex ){
                        c.setNamaArea("");
                    }
                    try{
                        c.setDKLK(s1.split("~")[15]);
                    }catch(ArrayIndexOutOfBoundsException arrex ){
                        c.setDKLK("");
                    }
                    try{
                        c.setCreditLimit(Long.parseLong(s1.split("~")[16]));
                    }catch(ArrayIndexOutOfBoundsException arrex ){
                        c.setCreditLimit(new Long(0));
                    }
                    try{
                        c.setNpwp(s1.split("~")[11]);
                    }catch(ArrayIndexOutOfBoundsException arrex ){
                        c.setNpwp("");
                    }
                    try{
                        c.setNamaWajibPajak(s1.split("~")[10]);
                    }catch(ArrayIndexOutOfBoundsException arrex ){
                        c.setNamaWajibPajak("");
                    }
                    try{
                        c.setCreationDate(s1.split("~")[6]);
                    }catch(ArrayIndexOutOfBoundsException arrex ){
                        c.setCreationDate("");
                    }
                    try{
                        c.setLastUpdateBy(s1.split("~")[17]);
                    }catch(ArrayIndexOutOfBoundsException arrex ){
                        c.setLastUpdateBy("");
                    }
                    try{
                        c.setLastUpdateDate(s1.split("~")[18]);
                    }catch(ArrayIndexOutOfBoundsException arrex ){
                        c.setLastUpdateDate("");
                    }
                    
                    
                    lCust.add(c);
                }

            }
            
            for (Customer c : lCust) {
                
               
                    Customer cc = Ebean.find(Customer.class, c.getId());
                    if (cc != null) {
                        cc = c;
                        Ebean.update(cc);
                    }
             
                
                System.out.print(c.getId());
                System.out.print(" | ");
                System.out.print(c.getNama());
                System.out.print(" | ");
                System.out.print(c.getShipto());
                System.out.print(" | ");
                System.out.print(c.getNpwp());
                System.out.print(" | ");
                System.out.print(c.getNamaWajibPajak());
                System.out.println();
            }

        } catch (IOException ex) {
            Logger.getLogger(TestReadCustData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
