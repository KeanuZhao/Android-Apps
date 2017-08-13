package com.finalp.keanu.mark.Entitys;

/**
 * Created by miaos on 2017/5/9.
 */
public class ExportItem {

    private int totalMark;
    //    GroupNumber、BuptNumber、QMNumber、SurName、ForeName、
    private int GroupNumber;
    private int BuptNumber;
    private int QMNumber;
    private String SurName;
    private String ForeName;
    private String SheetName;

    public ExportItem(int groupNumber, int buptNumber, int QMNumber, String surName, String foreName, String sheetName) {
        GroupNumber = groupNumber;
        BuptNumber = buptNumber;
        this.QMNumber = QMNumber;
        SurName = surName;
        ForeName = foreName;
        SheetName = sheetName;
    }

    public int getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(int totalMark) {
        this.totalMark = totalMark;
    }

    public int getGroupNumber() {
        return GroupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        GroupNumber = groupNumber;
    }

    public int getBuptNumber() {
        return BuptNumber;
    }

    public void setBuptNumber(int buptNumber) {
        BuptNumber = buptNumber;
    }

    public int getQMNumber() {
        return QMNumber;
    }

    public void setQMNumber(int QMNumber) {
        this.QMNumber = QMNumber;
    }

    public String getSurName() {
        return SurName;
    }

    public void setSurName(String surName) {
        SurName = surName;
    }

    public String getForeName() {
        return ForeName;
    }

    public void setForeName(String foreName) {
        ForeName = foreName;
    }

    public String getSheetName() {
        return SheetName;
    }

    public void setSheetName(String sheetName) {
        SheetName = sheetName;
    }
}
