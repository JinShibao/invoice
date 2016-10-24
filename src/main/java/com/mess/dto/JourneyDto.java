package com.mess.dto;

/**
 * Created by jinshibao on 2016/10/14.
 */

public class JourneyDto {
    private String date;
    private String startTime;
    private String endTime;
    private String reason;
    private String startPlace;
    private String endPlace;
    private String money;
    private String billNum;
    private String billOwner;
    private String remark;

    public JourneyDto() {
    }

    public JourneyDto(String date, String startTime, String endTime, String reason, String startPlace, String endPlace,
                      String money, String billNum, String billOwner, String remark) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reason = reason;
        this.startPlace = startPlace;
        this.endPlace = endPlace;
        this.money = money;
        this.billNum = billNum;
        this.billOwner = billOwner;
        this.remark = remark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public String getEndPlace() {
        return endPlace;
    }

    public void setEndPlace(String endPlace) {
        this.endPlace = endPlace;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getBillNum() {
        return billNum;
    }

    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

    public String getBillOwner() {
        return billOwner;
    }

    public void setBillOwner(String billOwner) {
        this.billOwner = billOwner;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
