package com.enseval.ttss.vm;

import com.enseval.ttss.model.*;
import java.util.*;
import com.enseval.ttss.util.*;
import com.avaje.ebean.*;
import org.zkoss.zk.ui.util.*;
import org.zkoss.zk.ui.*;
import org.zkoss.bind.annotation.*;

public class MenuUserSettingVM
{
    User userLogin;
    List<Printer> printers;
    Printer printer;
    
    public MenuUserSettingVM() {
        this.printers = new ArrayList<Printer>();
    }
    
    @AfterCompose
    public void initSetup() {
        this.userLogin = (User)Ebean.find((Class)User.class, (Object)new AuthenticationServiceImpl().getUserCredential().getUser().getId());
        this.printers = (List<Printer>)Ebean.find((Class)Printer.class).findList();
    }
    
    @Command
    @NotifyChange({ "userLogin" })
    public void simpan() {
        Ebean.save((Object)this.userLogin);
        Clients.showNotification("Perubahan disimpan", "info", (Component)null, (String)null, 3000, true);
    }
    
    public User getUserLogin() {
        return this.userLogin;
    }
    
    public void setUserLogin(final User userLogin) {
        this.userLogin = userLogin;
    }
    
    public Printer getPrinter() {
        return this.printer;
    }
    
    public void setPrinter(final Printer printer) {
        this.printer = printer;
    }
    
    public List<Printer> getPrinters() {
        return this.printers;
    }
    
    public void setPrinters(final List<Printer> printers) {
        this.printers = printers;
    }
}
