package com.enseval.ttss.util;

import java.util.logging.*;
import java.text.*;
import java.io.*;

public class Rupiah
{
    int number;
    boolean includeCurrency;
    
    public Rupiah(int numberr, boolean includeCurrencyy) {
        this.number = 0;
        this.includeCurrency = false;
        this.number = numberr;
        this.includeCurrency = includeCurrencyy;
    }
    
    public String get() {
        String nilai = "";
        if (this.includeCurrency) {
            nilai = convert(this.number) + " Rupiah";
        }
        else {
            nilai = convert(this.number);
        }
        return nilai;
    }
    
    public static String convert(int numberr) {
        Label_0038: {
            if (numberr >= 0) {
                if (numberr <= 999999999) {
                    break Label_0038;
                }
            }
            try {
                throw new Exception("Number is out of range");
            }
            catch (Exception ex) {
                Logger.getLogger(Rupiah.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        final int Gn = (int)Math.floor(numberr / 1000000);
        numberr -= Gn * 1000000;
        final int Kn = (int)Math.floor(numberr / 1000);
        numberr -= Kn * 1000;
        final Integer Hn = (int)Math.floor(numberr / 100);
        numberr -= Hn * 100;
        final int Dn = (int)Math.floor(numberr / 10);
        final int n = numberr % 10;
        final StringBuilder res = new StringBuilder();
        if (Gn != 0) {
            res.append(convert(Gn)).append(" Juta ");
        }
        if (Kn != 0) {
            res.append(res.toString().isEmpty() ? "" : "").append(convert(Kn)).append(" Ribu ");
        }
        if (Hn != 0) {
            res.append(res.toString().isEmpty() ? "" : "").append(convert(Hn)).append(" Ratus");
        }
        final String[] ones = { "", "Satu", "Dua", "Tiga", "Empat", "Lima", "Enam", "Tujuh", "Delapan", "Sembilan", "Sepuluh", "Sebelas", "Dua Belas", "Tiga Belas", "Empat Belas", "Lima Belas", "Enam Belas", "Tujuh Belas", "Delapan Belas", "Sembilan Belas" };
        final String[] tens = { "", "Sepuluh", "Dua Puluh", "Tiga Puluh", "Empat Puluh", "Lima Puluh", "Enam Puluh", "Tujuh Puluh", "Delapan Puluh", "Sembilan Puluh" };
        final String[] thousands = { "", "Seribu", "Dua Ribu", "Tiga Ribu", "Empat Ribu", "Lima Ribu", "Enam Ribu", "Tujuh Ribu", "Delapan Ribu", "Sembilan Ribu" };
        if (Dn != 0 || n != 0) {
            if (!res.toString().isEmpty()) {
                res.append(" ");
            }
            if (Dn < 2) {
                res.append(ones[Dn * 10 + n]);
            }
            else {
                res.append(tens[Dn]);
                if (n != 0) {
                    res.append(" ").append(ones[n]);
                }
            }
        }
        if (res.toString().isEmpty()) {
            res.append("nol");
        }
        replace("Satu Ratus", "Seratus", res);
        replace("Satu Ribu", "Seribu", res);
        try {
            if (res.substring(res.indexOf("Seribu") - 6).startsWith("Puluh")) {
                replace("Seribu", "Satu Ribu", res);
            }
        }
        catch (Exception ex2) {}
        return res.toString();
    }
    
    public static void replace(final String target, final String replacement, final StringBuilder builder) {
        int indexOfTarget = -1;
        while ((indexOfTarget = builder.indexOf(target)) >= 0) {
            builder.replace(indexOfTarget, indexOfTarget + target.length(), replacement);
        }
    }
    
    public static String format(Long l) {
        final DecimalFormat kursIndonesia = (DecimalFormat)NumberFormat.getCurrencyInstance();
        final DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia.format(l);
    }
}
