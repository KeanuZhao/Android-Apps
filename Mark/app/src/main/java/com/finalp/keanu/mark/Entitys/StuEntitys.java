package com.finalp.keanu.mark.Entitys;

/**
 * Created by miaos on 2017/3/27.
 */
public class StuEntitys {

//     GroupNumber、BuptNumber、QMNumber、SurName、ForeName、SheetName
    private int id;
    private int GroupNumber;
    private int BuptNumber;
    private int QMNumber;
    private String SurName;
    private String ForeName;
    private String SheetName;

    public StuEntitys(int GroupNumber, int BuptNumber,  int QMNumber,String SurName, String ForeName, String SheetName) {
        this.GroupNumber = GroupNumber;
        this.BuptNumber = BuptNumber;
        this.QMNumber = QMNumber;
        this.SurName = SurName;
        this.ForeName = ForeName;
        this.SheetName = SheetName;
    }

    public StuEntitys(int id,int GroupNumber, int BuptNumber,  int QMNumber,String SurName, String ForeName, String SheetName) {
        this.id = id;
        this.GroupNumber = GroupNumber;
        this.BuptNumber = BuptNumber;
        this.QMNumber = QMNumber;
        this.SurName = SurName;
        this.ForeName = ForeName;
        this.SheetName = SheetName;
    }

    @Override
    public String toString() {
        return "StuEntitys{" +
                "id=" + id +
                ", GroupNumber=" + GroupNumber +
                ", BuptNumber=" + BuptNumber +
                ", QMNumber=" + QMNumber +
                ", SurName='" + SurName + '\'' +
                ", ForeName='" + ForeName + '\'' +
                ", SheetName='" + SheetName + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupNumber() {
        return GroupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        GroupNumber = groupNumber;
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
}
