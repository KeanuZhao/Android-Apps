package com.finalp.keanu.mark.Entitys;

/**
 * Created by miaos on 2017/4/11.
 */
public class MarkItem {

    private String itemName;
    private String itemContent;

    public MarkItem(String itemName, String itemContent) {
        this.itemName = itemName;
        this.itemContent = itemContent;
    }

    public MarkItem(String itemName) {
        this.itemName = itemName;
        this.itemContent = "";
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }
}
