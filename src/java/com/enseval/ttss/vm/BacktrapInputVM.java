/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enseval.ttss.vm;

import com.enseval.ttss.model.Backtrap;
import java.util.HashMap;
import java.util.Map;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;

/**
 *
 * @author user
 */
public class BacktrapInputVM {

    String msg;
    Backtrap backtrap = new Backtrap();

    @Command
    public void inputPin() {
        Map args = new HashMap();
        args.put("backtrapnya", this.backtrap);
        Executions.createComponents("WinInputPin.zul", (Component) null, args);
    }
    
    @Command
    @NotifyChange({"backtrap"})
    public void reset() {
       backtrap = new Backtrap();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Backtrap getBacktrap() {
        return backtrap;
    }

    public void setBacktrap(Backtrap backtrap) {
        this.backtrap = backtrap;
    }

}
