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
    String filterId = "", filterNama = "", filterShipto = "";
    String searchText = "";

    @AfterCompose
    public void initSetup(@ContextParam(ContextType.VIEW) Component view) {

        listCustomer = Ebean.find(Customer.class).orderBy("id desc").findList();
        Selectors.wireComponents(view, this, false);
    }

    @Command
    @NotifyChange("listCustomer")
    public void doFilter() {

        listCustomer = Ebean.find(Customer.class)
                .where("id like '%" + this.searchText + "%' or "
                        + "nama like '%" + this.searchText + "%' or "
                        + "shipto like '%" + this.searchText + "%' or "
                        + "kota like '%" + this.searchText + "%' or "
                        + "kodePos like '%" + this.searchText + "%' or "
                        + "namaArea like '%" + this.searchText + "%' or "
                        + "DKLK like '%" + this.searchText + "%' or "
                        + "creditLimit like '%" + this.searchText + "%' or "
                        + "npwp like '%" + this.searchText + "%' or "
                        + "namaWajibPajak like '%" + this.searchText + "%' or "
                        + "provinsi like '%" + this.searchText + "%'")
                .findList();

    }

    @Command
    @NotifyChange("listCustomer")
    public void saveCustomer(@BindingParam("c") Customer c) {
        Customer cc = Ebean.find(Customer.class, c.getId());
        cc = c;
        Ebean.update(cc);
        

    }

    @Command
    @NotifyChange("listCustomer")
    public void hapusCustomer(@BindingParam("customernya") Customer c) {

        if (Ebean.find(Giro.class).where().eq("customer.id", c.getId()).findList().size() > 0) {
            Messagebox.show("Salah satu giro atau lebih menggunakan customer ini", "ERROR", Messagebox.OK, Messagebox.ERROR);
            return;
        }

        Ebean.delete(Customer.class, c.getId());
        Messagebox.show("Data customer berhasil dihapus", "INFO", Messagebox.OK, Messagebox.INFORMATION);
        listCustomer = Ebean.find(Customer.class)
                .where().like("id", "%" + filterId + "%")
                .where().like("nama", "%" + filterNama + "%")
                .where().like("shipto", "%" + filterShipto + "%")
                .orderBy("id desc").findList();
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

    public String getFilterId() {
        return filterId;
    }

    public void setFilterId(String filterId) {
        this.filterId = filterId;
    }

    public String getFilterNama() {
        return filterNama;
    }

    public void setFilterNama(String filterNama) {
        this.filterNama = filterNama;
    }

    public String getFilterShipto() {
        return filterShipto;
    }

    public void setFilterShipto(String filterShipto) {
        this.filterShipto = filterShipto;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

}
