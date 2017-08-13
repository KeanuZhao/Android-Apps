package com.finalp.keanu.mark.Entitys;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miaos on 2017/4/2.
 */
public class SearchingResult {

    private int groupNumber;
    private List<StuEntitys> list;

    public SearchingResult() {
        list = new ArrayList<StuEntitys>();
    }

    public SearchingResult(int groupNumber,List<StuEntitys> list) {
        this.groupNumber = groupNumber;
        this.list = list;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public List<StuEntitys> getList() {
        return list;
    }

    public void setList(List<StuEntitys> list) {
        this.list = list;
    }
}
