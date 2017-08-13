package com.finalp.keanu.mark.Utils;

import android.content.Context;
import android.os.Environment;

import com.finalp.keanu.mark.Entitys.ExportItem;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by miaos on 2017/5/9.
 */
public class ExportMark {
    private Context context;
    private List<ExportItem> list = new ArrayList<ExportItem>();

    public ExportMark(Context context) {
        this.context = context;
    }

    public boolean exportmark() {

        getStuList();

        int length = list.size();
        for(int i = 0; i < length; i++) {
            DBserviceMark dBserviceMark = new DBserviceMark(context);
            list.get(i).setTotalMark(dBserviceMark.getTotalMark(list.get(i).getGroupNumber()));
        }

        return exportTofile();
    }

    //获取学生信息
    private void getStuList() {
        DBserviceStu dBserviceStu = new DBserviceStu(context);
        list = dBserviceStu.exportAll();
    }

    //导出到文件
    private boolean exportTofile() {
        HSSFWorkbook wb = new HSSFWorkbook();
        int length = list.size();
        for(int i = 0; i < length; i++) {
            //(int groupNumber, int buptNumber, int QMNumber, String surName, String foreName
            HSSFSheet sheet = wb.createSheet(list.get(i).getSheetName());
            // 生成一个样式
            HSSFCellStyle style = wb.createCellStyle();
            //创建第一行（也可以称为表头）
            HSSFRow row = sheet.createRow(0);
            //样式字体居中
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //给表头第一行一次创建单元格
            HSSFCell cell = row.createCell((short) 0);
            cell.setCellValue("Group Number");
            cell.setCellStyle(style);
            cell = row.createCell( (short) 1);
            cell.setCellValue("Bupt Number");
            cell.setCellStyle(style);
            cell = row.createCell((short) 2);
            cell.setCellValue("QM Number");
            cell.setCellStyle(style);
            cell = row.createCell((short) 3);
            cell.setCellValue("Sur Name");
            cell.setCellStyle(style);
            cell = row.createCell((short) 4);
            cell.setCellValue("Fore Name");
            cell.setCellStyle(style);
            cell = row.createCell((short) 5);
            cell.setCellValue("Total Mark");
            cell.setCellStyle(style);
            String sheetName = list.get(i).getSheetName();
            for(short j = 0; i < length; j++) {
                if(!sheetName.equals(list.get(i).getSheetName())) {
                    i--;
                    break;
                }
                row = sheet.createRow(j + 1);
                row.createCell(0).setCellValue(list.get(i).getGroupNumber());
                row.createCell(1).setCellValue(list.get(i).getBuptNumber());
                row.createCell(2).setCellValue(list.get(i).getQMNumber());
                row.createCell(3).setCellValue(list.get(i).getSurName());
                row.createCell(4).setCellValue(list.get(i).getForeName());
                row.createCell(5).setCellValue(list.get(i).getTotalMark());
                i++;
            }
        }
        try {
            //写文件
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                //Environment.getExternalStorageDirectory()表示找到sdcarf目录
                File file =new File(Environment.getExternalStorageDirectory(),"成绩单" + System.currentTimeMillis() + ".xls");
                FileOutputStream out =new FileOutputStream(file);
                wb.write(out);
                out.close();
            }else{
                FileOutputStream out = context.openFileOutput("成绩单" + System.currentTimeMillis() + ".xls",Context.MODE_PRIVATE);
                wb.write(out);
                out.close();
            }
            return true;
        } catch(FileNotFoundException e) {
            System.out.println("file not foundException!");
            return false;
        } catch (IOException e) {
            System.out.println("file IOexception!");
            return false;
        }
    }
}
