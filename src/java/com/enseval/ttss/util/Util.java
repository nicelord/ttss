/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enseval.ttss.util;

import com.avaje.ebean.Ebean;
import com.enseval.ttss.model.Setting;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class Util {

    public static Date toDate(String tgl) {
        Date ret = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            ret = formatter.parse(tgl);
        } catch (ParseException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ret;
    }

    public static String toString(Date tgl) {
        String ret = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ret = formatter.format(tgl);

        return ret;
    }

    public static String setting(String namaSetting) {
        return Ebean.find(Setting.class).where().eq("namaSetting", namaSetting).findUnique().getNilaiSetting();
    }

}
