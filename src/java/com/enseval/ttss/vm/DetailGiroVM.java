package com.enseval.ttss.vm;

import org.zkoss.zk.ui.select.annotation.*;
import org.zkoss.zk.ui.*;
import com.enseval.ttss.util.*;
import com.avaje.ebean.*;
import org.zkoss.zk.ui.select.*;
import java.text.*;
import org.zkoss.bind.*;
import java.util.*;
import com.enseval.ttss.model.*;
import org.zkoss.zul.*;
import net.sf.jasperreports.engine.*;
import java.awt.print.*;
import java.sql.Timestamp;
import org.zkoss.bind.annotation.*;

public class DetailGiroVM {

    @Wire("#addGiro")
    private Window win;
    @Wire("#chkKliring")
    private Checkbox chkKliring;

    @Wire("#status")
    Combobox cmbStatus;

    Giro giro;
    List<DsPenyetor> listPenyetor;
    List<String> listTag;
    List<String> listBank;
    List<String> listStatus;
    User userLogin;
    String DKLK;

    public DetailGiroVM() {
        this.listPenyetor = new ArrayList<>();
        this.listTag = new ArrayList<>();
        this.listBank = new ArrayList<>();
        this.listStatus = new ArrayList<>();
    }

    @AfterCompose
    public void initSetup(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("gironya") Giro gironya) {

        this.userLogin = new AuthenticationServiceImpl().getUserCredential().getUser();

        this.giro = gironya;
        this.listPenyetor = (List<DsPenyetor>) Ebean.find((Class) DsPenyetor.class).findList();
        this.listTag = Ebean.find((Class) Giro.class).select("tag").setDistinct(true).findList();
        this.listBank = Ebean.find((Class) Giro.class).select("bank").setDistinct(true).findList();
        this.listStatus = Ebean.find((Class) Giro.class).select("status").setDistinct(true).findList();
        Selectors.wireComponents(view, this, false);

    }

    @Command
    public void prosesKliring() {

        List<Giro> listGiro = new ArrayList<>();
        listGiro.add(giro);
        Map args = new HashMap();
        args.put("selected_giro", listGiro);
        args.put("do_download", false);
        Executions.createComponents("WinKonfirmasiTglKliring.zul", null, args);

    }

    @Command
    public void giroDitolak() {
        if (Util.setting("email_aktif").equals("YA")) {
            if (giro.getCustomer() == null) {
                Messagebox.show("Data customer wajib diisi untuk giro tolak", "Error", Messagebox.OK, Messagebox.ERROR);
                return;
            }

            MailNotif mailNotif = new MailNotif();
            mailNotif.emailGiroTolak(this.giro, giro.getCustomer().getNama(), giro.getCustomer().getId().toString());

        }

        this.giro.setProsesKliring("DITOLAK");
        UpdateGiro();

    }

    @Command
    public void giroBatal() {
        this.giro.setProsesKliring("BATAL");
        UpdateGiro();
    }

    @Command
    @GlobalCommand
    public void UpdateGiro() {
        
        this.giro.setLastUpdate(new Timestamp(new Date().getTime()));
        this.giro.setUserLogin(userLogin);
        Ebean.save(this.giro);

        BindUtils.postGlobalCommand((String) null, (String) null, "refresh", (Map) null);
        this.win.detach();
        saveHistory();

    }

    @GlobalCommand
    @NotifyChange({"giro"})
    public void setCustomer(@BindingParam("customer") Customer customer) {
        giro.setCustomer(customer);

    }

    public void saveHistory() {
        Giro g = Ebean.find(Giro.class, this.giro.getNomor());
        GiroHistory gh = new GiroHistory();
        gh.setLastUpdate(g.getLastUpdate());
        gh.setNomor(g.getNomor());
        gh.setNomorGiro(g.getNomorGiro());
        gh.setBank(g.getBank());
        gh.setUserLogin(g.getUserLogin());
        try {
            gh.setCustID(g.getCustomer().getId());
        } catch (NullPointerException e) {

        }
        gh.setNilai(g.getNilai());
        gh.setProsesKliring(g.getProsesKliring());
        gh.setNamaPenyetor(g.getNamaPenyetor());
        gh.setDKLK(g.getDKLK());
        gh.setStatus(g.getStatus());
        gh.setTag(g.getTag());
        gh.setTglJtTempo(g.getTglJtTempo());
        gh.setTglKliring(g.getTglKliring());
        gh.setWktTerima(g.getWktTerima());
        gh.setKeterangan(g.getKeterangan());

        Ebean.save(gh);
    }

    @Command
    public void showCustomer() {
        Executions.createComponents("customer.zul", null, null);
    }

    @Command
    public void showHistory(@BindingParam("nomor") Long nomor) {
        Map m = new HashMap();
        m.put("nomor", nomor);
        Executions.createComponents("HistoryGiro.zul", null, m);
    }

    public User getUserLogin() {
        return this.userLogin;
    }

    public void setUserLogin(final User userLogin) {
        this.userLogin = userLogin;
    }

    public List<DsPenyetor> getListPenyetor() {
        return this.listPenyetor;
    }

    public void setListPenyetor(final List<DsPenyetor> listPenyetor) {
        this.listPenyetor = listPenyetor;
    }

    public Window getWin() {
        return win;
    }

    public void setWin(Window win) {
        this.win = win;
    }

    public Giro getGiro() {
        return giro;
    }

    public void setGiro(Giro giro) {
        this.giro = giro;
    }

    public List<String> getListTag() {
        return listTag;
    }

    public void setListTag(List<String> listTag) {
        this.listTag = listTag;
    }

    public String getDKLK() {
        return DKLK;
    }

    public void setDKLK(String DKLK) {
        this.DKLK = DKLK;
    }

    public List<String> getListBank() {
        return listBank;
    }

    public void setListBank(List<String> listBank) {
        this.listBank = listBank;
    }

    public List<String> getListStatus() {
        return listStatus;
    }

    public void setListStatus(List<String> listStatus) {
        this.listStatus = listStatus;
    }

    public Checkbox getChkKliring() {
        return chkKliring;
    }

    public void setChkKliring(Checkbox chkKliring) {
        this.chkKliring = chkKliring;
    }

    public Combobox getCmbStatus() {
        return cmbStatus;
    }

    public void setCmbStatus(Combobox cmbStatus) {
        this.cmbStatus = cmbStatus;
    }

}
