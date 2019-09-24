package com.siti.wisdomhydrologic.datepull.vo;

import com.siti.wisdomhydrologic.util.DateOrTimeTrans;

import java.util.Date;

/**
 * Created by dell on 2019/7/11.
 */
public class RealVo {

    private Integer senId;
    private Date Time;
    private Double FACTV;
    private Double IFCH;
    private Double CYCLE;
    private Double STATE;
    private Double TS;

    /**
     * 为mq传入是做判断丢包添加的字段
     * maxBatch;currentBatch;status;sumSize;currentSize;
     * */
    private int maxBatch;
    private int currentBatch;
    private int status;
    private int sumSize;
    private int currentSize;

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

    public Double getFACTV() {
        return FACTV;
    }

    public void setFACTV(Double FACTV) {
        this.FACTV = FACTV;
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

    public int getMaxBatch() {
        return maxBatch;
    }

    public void setMaxBatch(int maxBatch) {
        this.maxBatch = maxBatch;
    }

    public int getCurrentBatch() {
        return currentBatch;
    }

    public void setCurrentBatch(int currentBatch) {
        this.currentBatch = currentBatch;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSumSize() {
        return sumSize;
    }

    public void setSumSize(int sumSize) {
        this.sumSize = sumSize;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    @Override
    public String toString() {
        return "Real{" + "senId=" + senId + ", Time=" + DateOrTimeTrans.Date2TimeString2(Time) + ", FACTV=" + FACTV + ", IFCH=" + IFCH + ", CYCLE=" + CYCLE + ", STATE=" + STATE + ", TS=" + TS + '}';
    }
}
