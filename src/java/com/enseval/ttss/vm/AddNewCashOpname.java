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

public class AddNewCashOpname {

    List<CashOpname> listOpname = new ArrayList<>();
    CashOpname opnameBaru = new CashOpname();

    @AfterCompose
    public void initSetup(@ContextParam(ContextType.VIEW) Component view,
            @ExecutionArgParam("jenisKas") String jenisKas,
            @ExecutionArgParam("tglCutoff") Date tglCutoff,
            @ExecutionArgParam("saldoSistem") Long saldoSistem) {

        this.listOpname = Ebean.find(CashOpname.class).findList();
        opnameBaru.setTglOpname(new Date());
        opnameBaru.setJenisKas(jenisKas);
        opnameBaru.setTglCutoff(tglCutoff);
        opnameBaru.setSaldoSistem(saldoSistem);

        Selectors.wireComponents(view, this, false);
    }

    public List<CashOpname> getListOpname() {
        return listOpname;
    }

    public void setListOpname(List<CashOpname> listOpname) {
        this.listOpname = listOpname;
    }

    public CashOpname getOpnameBaru() {
        return opnameBaru;
    }

    public void setOpnameBaru(CashOpname opnameBaru) {
        this.opnameBaru = opnameBaru;
    }

}
