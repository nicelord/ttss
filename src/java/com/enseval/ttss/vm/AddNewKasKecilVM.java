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

public class AddNewKasKecilVM {

    @Wire("#addNewKasKecil")
    private Window win;
    @Wire("#penyetor")
    private Combobox cmb;

    User userLogin;
    KasKecil kasKecil;
    List<KasKecil> listNama;
    List<KasKecil> listDept;

    public AddNewKasKecilVM() {

    }

    @AfterCompose
    public void initSetup(@ContextParam(ContextType.VIEW) final Component view) {

        kasKecil = new KasKecil();
        
        this.userLogin = Ebean.find(User.class, new AuthenticationServiceImpl().getUserCredential().getUser().getId());
        this.listNama = Ebean.find(KasKecil.class).select("nama").setDistinct(true).findList();
        this.listDept = Ebean.find(KasKecil.class).select("dept").setDistinct(true).findList();
        
        

        Selectors.wireComponents(view, (Object) this, false);
        
     
    }

    @Command
    public void saveNewKasKecil() {
        this.kasKecil.setNomor(Long.parseLong(this.kasKecil.getLastNomor()) + 1L);
        this.kasKecil.setTanggalBuat(new Date());
        this.kasKecil.setStatus("PENDING");
        this.kasKecil.setNamaPetugas(userLogin.getNama());
        Ebean.save(this.kasKecil);
        win.detach();
        BindUtils.postGlobalCommand((String) null, (String) null, "saring", (Map) null);

    }

    public Window getWin() {
        return win;
    }

    public void setWin(Window win) {
        this.win = win;
    }

    public Combobox getCmb() {
        return cmb;
    }

    public void setCmb(Combobox cmb) {
        this.cmb = cmb;
    }

    public User getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(User userLogin) {
        this.userLogin = userLogin;
    }

    public KasKecil getKasKecil() {
        return kasKecil;
    }

    public void setKasKecil(KasKecil kasKecil) {
        this.kasKecil = kasKecil;
    }

    public List<KasKecil> getListNama() {
        return listNama;
    }

    public void setListNama(List<KasKecil> listNama) {
        this.listNama = listNama;
    }

    public List<KasKecil> getListDept() {
        return listDept;
    }

    public void setListDept(List<KasKecil> listDept) {
        this.listDept = listDept;
    }

}
