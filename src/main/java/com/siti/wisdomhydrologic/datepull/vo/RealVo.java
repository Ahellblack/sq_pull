package com.siti.wisdomhydrologic.datepull.vo;

import com.siti.wisdomhydrologic.util.DateOrTimeTrans;

import java.util.Date;

/**
 * Created by dell on 2019/7/11.
 */
public class RealVo {

    private Integer senId;
    private Date Time;
    private Integer FACTV;
    private Integer IFCH;
    private Integer CYCLE;
    private Integer STATE;
    private Integer TS;

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

    public Integer getFACTV() {
        return FACTV;
    }

    public void setFACTV(Integer FACTV) {
        this.FACTV = FACTV;
    }

    public Integer getIFCH() {
        return IFCH;
    }

    public void setIFCH(Integer IFCH) {
        this.IFCH = IFCH;
    }

    public Integer getCYCLE() {
        return CYCLE;
    }

    public void setCYCLE(Integer CYCLE) {
        this.CYCLE = CYCLE;
    }

    public Integer getSTATE() {
        return STATE;
    }

    public void setSTATE(Integer STATE) {
        this.STATE = STATE;
    }

    public Integer getTS() {
        return TS;
    }

    public void setTS(Integer TS) {
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
