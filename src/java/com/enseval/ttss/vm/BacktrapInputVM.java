/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enseval.ttss.vm;

import com.enseval.ttss.model.Backtrap;

/**
 *
 * @author user
 */
public class BacktrapInputVM {
    
    String msg;
    Backtrap backtrap = new Backtrap();

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
