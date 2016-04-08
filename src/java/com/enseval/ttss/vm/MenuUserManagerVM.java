package com.enseval.ttss.vm;

import com.avaje.ebean.*;
import org.zkoss.zk.ui.util.*;
import org.zkoss.zk.ui.*;
import org.zkoss.zul.*;
import com.enseval.ttss.model.*;
import java.util.*;
import org.zkoss.bind.annotation.*;

public class MenuUserManagerVM
{
    List<User> users;
    List<User> selectedUsers;
    List<Printer> printers;
    User user;
    String filterID;
    String filterUsername;
    String filterAkses;
    String printernya;
    String akses;
    boolean newUser;
    
    public MenuUserManagerVM() {
        this.users = new ArrayList<User>();
        this.selectedUsers = new ArrayList<User>();
        this.printers = new ArrayList<Printer>();
        this.filterID = "";
        this.filterUsername = "";
        this.filterAkses = "";
        this.newUser = false;
    }
    
    @AfterCompose
    public void initSetup() {
        this.users = (List<User>)Ebean.find((Class)User.class).findList();
        this.printers = (List<Printer>)Ebean.find((Class)Printer.class).findList();
    }
    
    @Command
    @NotifyChange({ "users" })
    public void saring() {
        this.users = (List<User>)Ebean.find((Class)User.class).where("id like '%" + this.filterID + "%' and username like '%" + this.filterUsername + "%' and akses like '%" + this.filterAkses + "%'").findList();
    }
    
    @Command
    @NotifyChange({ "user", "newUser", "users", "printernya" })
    public void simpan() {
        if (this.newUser) {
            if(user.getNama().isEmpty()|user.getNama()==null | 
                    user.getUsername().isEmpty()|user.getUsername()==null | 
                    user.getPassword().isEmpty()|user.getPassword()==null | 
                    user.getAkses().isEmpty()|user.getAkses()==null | 
                    user.getDefPrinter().getNamaPrinter().isEmpty()|user.getDefPrinter().getNamaPrinter()==null){
                Messagebox.show("Data tidak lengkap", "ERROR", Messagebox.OK, Messagebox.ERROR);
                return;
            }
            this.user.setNama(this.user.getNama());
            this.user.setUsername(this.user.getUsername());
            this.user.setPassword(this.user.getPassword());
            this.user.setDefPrinter(this.user.getDefPrinter());
            Ebean.save((Object)this.user);
            Clients.showNotification("User baru ditambahkan", "info", (Component)null, (String)null, 3000, true);
        }
        else {
            if (this.user == null) {
                Messagebox.show("User belum dipilih");
                return;
            }
            Ebean.save((Object)this.user);
            Clients.showNotification("Perubahan disimpan", "info", (Component)null, (String)null, 3000, true);
        }
        this.newUser = false;
        this.user = null;
        this.refresh();
    }
    
    @Command
    @NotifyChange({ "user", "newUser", "printernya" })
    public void showDetail(@BindingParam("user") final User u) {
        this.newUser = false;
        this.user = u;
    }
    
    @Command
    @NotifyChange({ "user", "newUser", "printernya" })
    public void tambahUser() {
        this.newUser = true;
        this.user = new User();
    }
    
    @Command
    @NotifyChange({ "users", "selectedUsers" })
    public void hapusSelectedUsers() {
        for (final User user1 : this.selectedUsers) {
            Ebean.delete((Class)Cetak.class, (Collection)Ebean.find((Class)Cetak.class).where("userLogin.id = '" + user1.getId() + "'").findIds());
            Ebean.delete((Class)TTSS.class, (Collection)Ebean.find((Class)TTSS.class).where("userLogin.id = '" + user1.getId() + "'").findIds());
        }
        Ebean.delete((Collection)this.selectedUsers);
        this.refresh();
    }
    
    @GlobalCommand
    @NotifyChange({ "users", "selectedUsers", "printernya", "newUser" })
    public void refresh() {
        this.users = (List<User>)Ebean.find((Class)User.class).where("id like '%" + this.filterID + "%' and username like '%" + this.filterUsername + "%' and akses like '%" + this.filterAkses + "%'").findList();
        this.selectedUsers = new ArrayList<User>();
        this.newUser = false;
        this.user = null;
    }
    
    public List<User> getUsers() {
        return this.users;
    }
    
    public void setUsers(final List<User> users) {
        this.users = users;
    }
    
    public User getUser() {
        return this.user;
    }
    
    public void setUser(final User user) {
        this.user = user;
    }
    
    public String getFilterID() {
        return this.filterID;
    }
    
    public void setFilterID(final String filterID) {
        this.filterID = filterID;
    }
    
    public String getFilterUsername() {
        return this.filterUsername;
    }
    
    public void setFilterUsername(final String filterUsername) {
        this.filterUsername = filterUsername;
    }
    
    public List<User> getSelectedUsers() {
        return this.selectedUsers;
    }
    
    public void setSelectedUsers(final List<User> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }
    
    public List<Printer> getPrinters() {
        return this.printers;
    }
    
    public void setPrinters(final List<Printer> printers) {
        this.printers = printers;
    }
    
    public String getPrinternya() {
        return this.printernya;
    }
    
    public void setPrinternya(final String printernya) {
        this.printernya = printernya;
    }
    
    public String getAkses() {
        return this.akses;
    }
    
    public void setAkses(final String akses) {
        this.akses = akses;
    }
    
    public boolean isNewUser() {
        return this.newUser;
    }
    
    public void setNewUser(final boolean newUser) {
        this.newUser = newUser;
    }
    
    public String getFilterAkses() {
        return this.filterAkses;
    }
    
    public void setFilterAkses(final String filterAkses) {
        this.filterAkses = filterAkses;
    }
}
