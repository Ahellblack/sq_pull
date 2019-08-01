package com.siti.wisdomhydrologic.datepull.entity;

/**
 * Created by dell on 2019/7/17.
 */
public class Crunode {

    private String CSTCD;
    private String cnodename;
    private double NTYPE;
    private double NCSID;
    private char CESLO;
    private char CNTLA;
    private double FSTADHI;
    private double NTELEX;
    private String CSHOW_T;
    private double NREFTYPE;

    public String getCSTCD() {
        return CSTCD;
    }

    public void setCSTCD(String CSTCD) {
        this.CSTCD = CSTCD;
    }

    public String getCnodename() {
        return cnodename;
    }

    public void setCnodename(String cnodename) {
        this.cnodename = cnodename;
    }

    public double getNTYPE() {
        return NTYPE;
    }

    public void setNTYPE(double NTYPE) {
        this.NTYPE = NTYPE;
    }

    public double getNCSID() {
        return NCSID;
    }

    public void setNCSID(double NCSID) {
        this.NCSID = NCSID;
    }

    public char getCESLO() {
        return CESLO;
    }

    public void setCESLO(char CESLO) {
        this.CESLO = CESLO;
    }

    public char getCNTLA() {
        return CNTLA;
    }

    public void setCNTLA(char CNTLA) {
        this.CNTLA = CNTLA;
    }

    public double getFSTADHI() {
        return FSTADHI;
    }

    public void setFSTADHI(double FSTADHI) {
        this.FSTADHI = FSTADHI;
    }

    public double getNTELEX() {
        return NTELEX;
    }

    public void setNTELEX(double NTELEX) {
        this.NTELEX = NTELEX;
    }

    public String getCSHOW_T() {
        return CSHOW_T;
    }

    public void setCSHOW_T(String CSHOW_T) {
        this.CSHOW_T = CSHOW_T;
    }

    public double getNREFTYPE() {
        return NREFTYPE;
    }

    public void setNREFTYPE(double NREFTYPE) {
        this.NREFTYPE = NREFTYPE;
    }
}
