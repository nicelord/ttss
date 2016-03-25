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

public class AddNewGiro {

    @Wire("#addGiro")
    private Window win;
    @Wire("#penyetor")
    private Combobox cmb;
    @Wire("#tag")
    private Combobox cmbTag;
    Giro giro;
    List<DsPenyetor> listPenyetor;
    List<Giro> listTag;
    User userLogin;
    String DKLK;

    public AddNewGiro() {
        this.listPenyetor = new ArrayList<>();
        this.listTag = new ArrayList<>();
    }

    @AfterCompose
    public void initSetup(@ContextParam(ContextType.VIEW) final Component view) {
        this.userLogin = (User) Ebean.find((Class) User.class, (Object) new AuthenticationServiceImpl().getUserCredential().getUser().getId());
        this.giro = new Giro();
        this.giro.setNomor(Long.parseLong(this.giro.getLastNomor()) + 1L);
        this.listPenyetor = (List<DsPenyetor>) Ebean.find((Class) DsPenyetor.class).findList();
        this.listTag = (List<Giro>) Ebean.find((Class) Giro.class).select("tag").setDistinct(true).findList();
        Selectors.wireComponents(view, this, false);
    }

    @Command
    public void saveNewGiro() {
        this.giro.setUserLogin(this.userLogin);
        this.giro.setWktTerima(new Timestamp(new Date().getTime()));
        Ebean.save(this.giro);
        DsPenyetor dsp = (DsPenyetor) Ebean.find((Class) DsPenyetor.class).where("nama = '" + this.giro.getNamaPenyetor() + "'").findUnique();
        if (dsp == null) {
            dsp = new DsPenyetor();
            dsp.setNama(this.giro.getNamaPenyetor().toUpperCase());
            Ebean.save((Object) dsp);
        }
        BindUtils.postGlobalCommand((String) null, (String) null, "refresh", (Map) null);
        this.win.detach();
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

    public Combobox getCmb() {
        return this.cmb;
    }

    public void setCmb(final Combobox cmb) {
        this.cmb = cmb;
    }

 


    public Combobox getCmbTag() {
        return this.cmbTag;
    }

    public void setCmbTag(final Combobox cmbTag) {
        this.cmbTag = cmbTag;
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

    public List<Giro> getListTag() {
        return listTag;
    }

    public void setListTag(List<Giro> listTag) {
        this.listTag = listTag;
    }

    public String getDKLK() {
        return DKLK;
    }

    public void setDKLK(String DKLK) {
        this.DKLK = DKLK;
    }


}
