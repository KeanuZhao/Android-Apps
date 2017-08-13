package com.finalp.keanu.mark.Utils;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by miaos on 2017/3/19.
 */
public class POIexcel {

    private String fileName;

    private POIFSFileSystem fs;//file文件

    private HSSFWorkbook wb;//工作簿对象

    public POIexcel(String fileName) {
        this.fileName = fileName;
    }

    //创建操作流
    private void createPOISF() {
        if(!isCreate()) {
            try {
                fs = new POIFSFileSystem(new FileInputStream(fileName));
                wb = new HSSFWorkbook(fs);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //判断操作流是否创建，创建返回true
    private boolean isCreate() {
        return fs == null ? false : true;
    }

    //获得sheet的数目
    public int getSheetNum() {
        createPOISF();
        return wb.getNumberOfSheets();
    }

    //获得第n个sheet页
    public HSSFSheet getSheetX(int x) {
        createPOISF();
        if(x > getSheetNum()) {
            return null;
        }
        return wb.getSheetAt(x);
    }


}
