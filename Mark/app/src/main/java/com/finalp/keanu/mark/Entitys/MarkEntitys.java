package com.finalp.keanu.mark.Entitys;

import java.util.ArrayList;

/**
 * Created by miaos on 2017/4/10.
 */
public class MarkEntitys {

    private int id;
    private int groupNumber;
    private String sheetName;
    private ArrayList<Integer> markMateria;//细分
    private String reMark;
    private int totalMark;

    public MarkEntitys() {};

    public MarkEntitys(int groupNumber, String sheetName, ArrayList<Integer> markMateria, String reMark, int totalMark) {
        this.groupNumber = groupNumber;
        this.sheetName = sheetName;
        this.markMateria = markMateria;
        this.reMark = reMark;
        this.totalMark = totalMark;
    }

    @Override
    public String toString() {
        return "MarkEntitys{" +
                "id=" + id +
                ", groupNumber=" + groupNumber +
                ", sheetName='" + sheetName + '\'' +
                ", markMateria=" + markMateria +
                ", reMark='" + reMark + '\'' +
                ", totalMark=" + totalMark +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public ArrayList<Integer> getMarkMateria() {
        return markMateria;
    }

    public void setMarkMateria(ArrayList<Integer> markMateria) {
        this.markMateria = markMateria;
    }

    public String getReMark() {
        return reMark;
    }

    public void setReMark(String reMark) {
        this.reMark = reMark;
    }

    public int getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(int totalMark) {
        this.totalMark = totalMark;
    }

    //    private int id;
//    private int GroupNumber;
//    private int BuptNumber;
//    private int QMNumber;
//    private String SurName;
//    private String ForeName;
//    private String SheetName;

}
