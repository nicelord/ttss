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
    private Combobox cmbStatus;
    @Wire("#txtCustID")
    private Textbox txtCUstID;
    @Wire("#txtCustName")
    private Textbox txtCustName;

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
        this.userLogin = (User) Ebean.find((Class) User.class, (Object) new AuthenticationServiceImpl().getUserCredential().getUser().getId());
        this.giro = gironya;
        this.listPenyetor = (List<DsPenyetor>) Ebean.find((Class) DsPenyetor.class).findList();
        this.listTag = Ebean.find((Class) Giro.class).select("tag").setDistinct(true).findList();
        this.listBank = Ebean.find((Class) Giro.class).select("bank").setDistinct(true).findList();
        this.listStatus = Ebean.find((Class) Giro.class).select("status").setDistinct(true).findList();
        
        Selectors.wireComponents(view, this, false);
        if (this.giro.getCustID() != null) {
            txtCUstID.setValue(this.giro.getCustID().toString());
            txtCustName.setValue(Ebean.find(Customer.class, this.giro.getCustID()).getNama());
        }
    }

    @Command
    public void UpdateGiro() {

        if (chkKliring.isChecked()) {

            if (this.giro.getTglKliring() == null) {
                this.giro.setTglKliring(new Date());
            }
        } else {
            this.giro.setTglKliring(null);
        }

        if (cmbStatus.getValue().equals("DITOLAK BANK")) {
            if (txtCUstID.getValue().isEmpty()) {
                Messagebox.show("Customer belum dipilih", "Error", Messagebox.OK, Messagebox.ERROR);
                return;
            }
            this.giro.setTglKliring(null);
        }

        if(!txtCUstID.getValue().isEmpty()){
            this.giro.setCustID(Long.valueOf(txtCUstID.getValue()));
        }
        this.giro.setUserLogin(this.userLogin);
        //this.ttss.setWktTerima(new Timestamp(new Date().getTime()));
        Ebean.save(this.giro);
        BindUtils.postGlobalCommand((String) null, (String) null, "refresh", (Map) null);
        this.win.detach();
    }

    @Command
    public void showCustomer() {
        Executions.createComponents("customer.zul", null, null);
    }

    @GlobalCommand
    public void setCustomer(@BindingParam("customer") Customer customer) {
        txtCUstID.setValue(customer.getId().toString());
        txtCustName.setValue(customer.getNama());

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

    public Textbox getTxtCUstID() {
        return txtCUstID;
    }

    public void setTxtCUstID(Textbox txtCUstID) {
        this.txtCUstID = txtCUstID;
    }

    public Textbox getTxtCustName() {
        return txtCustName;
    }

    public void setTxtCustName(Textbox txtCustName) {
        this.txtCustName = txtCustName;
    }

}
