package com.finalp.keanu.mark.Entitys;

/**
 * Created by miaos on 2017/4/8.
 */
public class MatEntitys {

    private String criteriaContent;
    private String markFrom;
    private String markTo;

    public MatEntitys() {
    }

    public MatEntitys(String criteriaContent, String markFrom, String markTo) {
        this.criteriaContent = criteriaContent;
        this.markFrom = markFrom;
        this.markTo = markTo;
    }

    public String getCriteriaContent() {
        return criteriaContent;
    }

    public void setCriteriaContent(String criteriaContent) {
        this.criteriaContent = criteriaContent;
    }

    public String getMarkFrom() {
        return markFrom;
    }

    public void setMarkFrom(String markFrom) {
        this.markFrom = markFrom;
    }

    public String getMarkTo() {
        return markTo;
    }

    public void setMarkTo(String markTo) {
        this.markTo = markTo;
    }

    @Override
    public String toString() {
        StringBuffer resultStr = new StringBuffer();
        resultStr.append(criteriaContent + "%");
        resultStr.append(markFrom + "%");
        resultStr.append(markTo + "%");
        return resultStr.toString();
    }
}
