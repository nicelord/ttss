package com.enseval.ttss.vm;

import org.zkoss.zk.ui.select.annotation.*;
import org.zkoss.zk.ui.*;
import com.avaje.ebean.*;
import org.zkoss.zk.ui.select.*;
import java.util.*;
import com.enseval.ttss.model.*;
import javax.persistence.OptimisticLockException;
import org.zkoss.zul.*;
import org.zkoss.bind.annotation.*;

public class MenuMasterCustomerVM {

    List<Customer> listCustomer = new ArrayList<>();
    @Wire("#lboxId")
    Longbox lboxId;
    @Wire("#txtNama")
    Textbox txtNama;
    @Wire("#txtShipto")
    Textbox txtShipto;
    Customer customer = new Customer();

    @AfterCompose
    public void initSetup(@ContextParam(ContextType.VIEW) Component view) {

        listCustomer = Ebean.find(Customer.class).orderBy("id desc").findList();
        Selectors.wireComponents(view, this, false);
    }

    @Command
    @NotifyChange("listCustomer")
    public void doFilter() {
//        this.listCustomer = Ebean.find(Customer.class).where().or(
//                Expr.like("id", "%" + txtCari.getValue() + "%"),
//                Expr.like("nama", "%" + txtCari.getValue() + "%"))
//                .findList();

    }

    @Command
    @NotifyChange("listCustomer")
    public void saveCustomer() {
        Customer c = null;

        try {
            c = Ebean.find(Customer.class, lboxId.getValue());
            c.setNama(txtNama.getValue());
            c.setShipto(txtShipto.getValue());
            Ebean.update(c);
            Messagebox.show("Customer diupdate!", "INFO", Messagebox.OK, Messagebox.INFORMATION);
            listCustomer = Ebean.find(Customer.class).orderBy("id desc").findList();
            customer = null;

        } catch (NullPointerException ex) {

            c = new Customer();
            c.setId(lboxId.getValue());
            c.setNama(txtNama.getValue());
            c.setShipto(txtShipto.getValue());
            Ebean.save(c);

            Messagebox.show("Customer ditambahkan", "Info", Messagebox.OK, Messagebox.INFORMATION);
            listCustomer = Ebean.find(Customer.class).orderBy("id desc").findList();
            
            customer = null;

        }

    }

    @Command
    @NotifyChange("listCustomer")
    public void hapusCustomer(@BindingParam("customernya") Customer c) {

        if (Ebean.find(Giro.class).where().eq("custID", c.getId()).findList().size() > 0) {
            Messagebox.show("Salah satu giro atau lebih menggunakan customer ini", "ERROR", Messagebox.OK, Messagebox.ERROR);
            return;
        }

        Ebean.delete(Customer.class, c.getId());
        listCustomer = Ebean.find(Customer.class).orderBy("id desc").findList();
    }

    public List<Customer> getListCustomer() {
        return listCustomer;
    }

    public void setListCustomer(List<Customer> listCustomer) {
        this.listCustomer = listCustomer;
    }

    public Longbox getLboxId() {
        return lboxId;
    }

    public void setLboxId(Longbox lboxId) {
        this.lboxId = lboxId;
    }

    public Textbox getTxtNama() {
        return txtNama;
    }

    public void setTxtNama(Textbox txtNama) {
        this.txtNama = txtNama;
    }

    public Textbox getTxtShipto() {
        return txtShipto;
    }

    public void setTxtShipto(Textbox txtShipto) {
        this.txtShipto = txtShipto;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
