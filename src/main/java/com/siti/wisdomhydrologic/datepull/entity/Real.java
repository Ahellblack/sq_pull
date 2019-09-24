package com.siti.wisdomhydrologic.datepull.entity;

import com.siti.wisdomhydrologic.util.DateOrTimeTrans;

import java.util.Date;

/**
 * Created by dell on 2019/7/11.
 */
public class Real {

    private Integer senId;
    private Date Time;
    private Double FACTV;
    private Double IFCH;
    private Double CYCLE;
    private Double STATE;
    private Double TS;

    public Integer getSenId() {
        return senId;
    }

    public void setSenId(Integer senId) {
        this.senId = senId;
    }

    public Date getTime() {
        return Time;
    }

    public void setTime(Date time) {
        Time = time;
    }

    public Double getIFCH() {
        return IFCH;
    }

    public void setIFCH(Double IFCH) {
        this.IFCH = IFCH;
    }

    public Double getCYCLE() {
        return CYCLE;
    }

    public void setCYCLE(Double CYCLE) {
        this.CYCLE = CYCLE;
    }

    public Double getSTATE() {
        return STATE;
    }

    public void setSTATE(Double STATE) {
        this.STATE = STATE;
    }

    public Double getTS() {
        return TS;
    }

    public void setTS(Double TS) {
        this.TS = TS;
    }

    public Double getFACTV() {
        return FACTV;
    }

    public void setFACTV(Double FACTV) {
        this.FACTV = FACTV;
    }

    @Override
    public String toString() {
        return "Real{" + "senId=" + senId + ", Time=" + DateOrTimeTrans.Date2TimeString2(Time) + ", FACTV=" + FACTV + ", IFCH=" + IFCH + ", CYCLE=" + CYCLE + ", STATE=" + STATE + ", TS=" + TS + '}';
    }
}
