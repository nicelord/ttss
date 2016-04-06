/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enseval.ttss.util;

import com.avaje.ebean.Ebean;
import com.enseval.ttss.model.Customer;
import com.enseval.ttss.model.Giro;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.zkoss.zk.ui.Executions;

/**
 *
 * @author user
 */
public class MailNotif {

    public void emailGiroTolak(Giro gironya, String namaCustomer, String customerID) {

        String port = (Executions.getCurrent().getServerPort() == 80) ? "" : (":" + Executions.getCurrent().getServerPort());
        String url = Executions.getCurrent().getScheme() + "://" + Executions.getCurrent().getServerName() + port + Executions.getCurrent().getContextPath() + "/giro.zul";

        String msg = "<html>"
                + "<head>"
                + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
                + "<title>Untitled Document</title>"
                + "<style type=\"text/css\">"
                + "p {"
                + "font-family: \"Courier New\", Courier, monospace;"
                + "font-size: 12px;"
                + "}"
                + "</style>"
                + "</head>"
                + "<p>Yth, </p>"
                + "<p>Berikut kami informasikan customer/outlet baru ditambahkan dalam daftar giro tolak;</p> "
                + "<br/>"
                + "<p><pre>"
                + "Customer ID      : " + customerID + "<br/>"
                + "Nama Customer    : " + namaCustomer + "<br/>"
                + "Nomor Giro       : " + gironya.getNomorGiro() + "<br/>"
                + "Nilai            : " + Rupiah.format(gironya.getNilai()) + "<br/>"
                + "Bank             : " + gironya.getBank() + "<br/>"
                + "Keterangan       : " + gironya.getKeterangan() + ""
                + "<pre>"
                + "</p>"
                + "<br/>"
                + "<br/>"
                + "<br/>"
                + "<p>Outlet akan di hold sementara oleh bagian Data Proses, selama di hold outlet tidak bisa melakukan order</p>"
                + "<br/>"
                + "<br/>"
                + "<p><i>Note : "
                + "<br>"
                + "Info giro " + url + "<br/>"
                + "Ini adalah email otomatis, mohon tidak membalas email ini !</i></p>"
                + "</html>";

        try {
            HtmlEmail mail = new HtmlEmail();
            mail.setHostName(Util.setting("smtp_host"));
            mail.setSmtpPort(Integer.parseInt(Util.setting("smtp_port")));
            mail.setAuthenticator((Authenticator) new DefaultAuthenticator(Util.setting("smtp_username"), Util.setting("smtp_password")));
            mail.setFrom(Util.setting("email_from"));
            for (String s : Util.setting("email_to").split(",")) {
                mail.addTo(s.trim());
            }
            
            mail.setSubject("[INFO GIRO TOLAK] - Nomor Giro : " + gironya.getNomorGiro() + " , Customer : " + namaCustomer + " (" + customerID + ")");
            mail.setHtmlMsg(msg);
            mail.send();
        } catch (EmailException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
