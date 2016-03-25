package com.enseval.ttss.vm;

import com.enseval.ttss.model.*;
import java.util.*;
import org.zkoss.zk.ui.*;
import com.avaje.ebean.*;
import org.zkoss.zk.ui.select.*;
import javax.activation.*;
import org.zkoss.zul.*;
import java.io.*;
import org.zkoss.bind.annotation.*;

public class DaftarRevisiTTSSVM
{
    List<Cetak> cetaks;
    TTSS ttss;
    
    public DaftarRevisiTTSSVM() {
        this.cetaks = new ArrayList<Cetak>();
    }
    
    @AfterCompose
    public void initSetup(@ContextParam(ContextType.VIEW) final Component view, @ExecutionArgParam("ttssnya") final TTSS ttssnya) {
        this.ttss = ttssnya;
        this.cetaks = (List<Cetak>)Ebean.find((Class)Cetak.class).where("ttssnya.nomor = '" + this.ttss.getNomor() + "'").orderBy("cetakanKe DESC").findList();
        Selectors.wireComponents(view, (Object)this, false);
    }
    
    @Command
    @NotifyChange({ "ttss" })
    public void downloadPDF(@BindingParam("filePath") final String filePath) {
        try {
            final File dosfile = new File(filePath);
            if (dosfile.exists()) {
                final FileInputStream inputStream = new FileInputStream(dosfile);
                Filedownload.save((InputStream)inputStream, new MimetypesFileTypeMap().getContentType(dosfile), dosfile.getName());
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public List<Cetak> getCetaks() {
        return this.cetaks;
    }
    
    public void setCetaks(final List<Cetak> cetaks) {
        this.cetaks = cetaks;
    }
    
    public TTSS getTtss() {
        return this.ttss;
    }
    
    public void setTtss(final TTSS ttss) {
        this.ttss = ttss;
    }
}
